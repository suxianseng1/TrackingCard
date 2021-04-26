package com.nokida.trackingcard.service.impl;

import com.nokida.trackingcard.dao.CardInfoDetailMapper;
import com.nokida.trackingcard.dao.TrackingCardSourceMapper;
import com.nokida.trackingcard.exception.MyNullPointRunTimeException;
import com.nokida.trackingcard.service.DispatchCreateWordService;
import com.nokida.trackingcard.util.CopyUtils;
import com.nokida.trackingcard.util.PathUtil;
import com.nokida.trackingcard.util.SqlSessionFactoryUtil;
import com.nokida.trackingcard.util.ZipUtil;
import com.nokida.trackingcard.word.WordAction;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import freemarker.template.utility.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DispatchCreateWordServiceImpl implements DispatchCreateWordService {
    static SqlSessionFactory factory;
    private SqlSession sqlSession;

    static {
        DispatchCreateWordServiceImpl.factory = SqlSessionFactoryUtil.sqlSessionFactory;
    }

    public DispatchCreateWordServiceImpl() {
        this.sqlSession = DispatchCreateWordServiceImpl.factory.openSession();
    }

    @Override
    public String createWordByJobNumber(String uuid, String jobNumber) throws Exception {
        TrackingCardSourceMapper trackingCardSourceMapper = this.sqlSession.getMapper(TrackingCardSourceMapper.class);
        List<Map<String, Object>> headerList = trackingCardSourceMapper.selectByJobNumber(jobNumber);
        String dirPath = PathUtil.getFileRecordSystemDir();
        if (StringUtils.isNotBlank(uuid)) {
            dirPath = dirPath + uuid + File.separator;
        }
        if (CollectionUtils.isEmpty(headerList)) {
            return null;
        }
        String pageFirstPath = this.generaFirstPage(headerList.get(0), dirPath);
        List<String> filePathList = new ArrayList<String>();
        filePathList.add(pageFirstPath);
        filePathList.addAll(this.generatorFiles(headerList, dirPath));
        String destPath = dirPath + jobNumber + ".docx";
        this.mergeDoc(filePathList, destPath);
        return destPath;
    }

    @Override
    public String createWordBySingleCode(String jobNumber, String singleCode, String isBatchFlag) throws Exception {
        TrackingCardSourceMapper trackingCardSourceMapper = this.sqlSession.getMapper(TrackingCardSourceMapper.class);
        CardInfoDetailMapper cardInfoDetailMapper = this.sqlSession.getMapper(CardInfoDetailMapper.class);
        List<Map<String, Object>> headerList = trackingCardSourceMapper.selectByJobNumberAndWbs(jobNumber, singleCode);
        if (CollectionUtils.isNotEmpty(headerList)) {
            Map<String, Object> topListView = new HashMap<String, Object>();
            topListView.put("topListView", headerList);
            List<Map<String, Object>> temp = CopyUtils.deepCopyList(headerList);
            temp.add(0, topListView);
            headerList = temp;
        }
        headerList = this.checkForF016(headerList, CopyUtils.deepCopyList(headerList), cardInfoDetailMapper, trackingCardSourceMapper);
        if (CollectionUtils.isEmpty(headerList)) {
            return null;
        }
        String dirPath = PathUtil.getFileDir();
        String pageFirstPath = "";
        for (Map<String, Object> item : headerList) {
            if (!item.containsKey("topListView")) {
                pageFirstPath = this.generaFirstPage(item, dirPath);
                break;
            }
        }
        String pageMLPath = this.generaMLPage(headerList, dirPath);
        List<String> filePathList = new ArrayList<String>();
        filePathList.add(pageFirstPath);
        filePathList.add(pageMLPath);
        filePathList.addAll(this.generatorFiles(headerList, dirPath));
        String destPath = PathUtil.getFileDir() + jobNumber + ".docx";
        destPath = PathUtil.getFileDir() + jobNumber + ".zip";
        FileOutputStream fos2 = new FileOutputStream(new File(destPath));
        List<File> fileList = new ArrayList<File>();
        filePathList.forEach(path -> fileList.add(new File(path)));
        ZipUtil.newInstance().toZip(fileList, fos2);
        return destPath;
    }

    private String generaMLPage(List<Map<String, Object>> headerList, String dirPath) {
        WordAction wordAction = new WordAction();
        List<Map<String, Object>> listInfo = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> item : headerList) {
            if (item.containsKey("topListView")) {
                List<Map<String, Object>> temp = ((List<Map<String, Object>>)item.get("topListView")).stream().filter(entry -> {
                    if (ObjectUtils.allNotNull(entry.get("CARD_NAME"))){
                        return true;
                    } else {
                        return false;
                    }

                }).collect(Collectors.toList());
                item.put("topListView",temp);
                listInfo.add(item);
            }
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("WORD_NO", "GZK_ML");
        String filePath = wordAction.createWord(listInfo, dataMap, dirPath);
        return filePath;
    }

    private String generaSecondPage(String singleCode, String dirPath, Map<String, Object> map) {
        CardInfoDetailMapper cardInfoDetailMapper = this.sqlSession.getMapper(CardInfoDetailMapper.class);
        List<Map<String, Object>> listInfo = cardInfoDetailMapper.getSecondPageData(singleCode);
        WordAction wordAction = new WordAction();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String patt = "^[0-9]";
        Pattern r = Pattern.compile(patt);
        Matcher matcher = r.matcher(map.get("WBS").toString());
        String partWbs = matcher.replaceAll("");
        dataMap.put("PART_WBS", partWbs);
        dataMap.put("year", String.valueOf(Calendar.getInstance().get(1)).replace(",", ""));
        dataMap.put("WORD_NO", "PageSecond");
        dataMap.put("MATERIAL_NAME", map.get("MATERIAL_NAME"));
        String filePath = wordAction.createWord(listInfo, dataMap, dirPath);
        return filePath;
    }

    private String generaFirstPage(Map<String, Object> dataMap, String dirPath) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("PRODUCT_NO", dataMap.get("PRODUCT_NO"));
        data.put("WBS", dataMap.get("WBS").toString());
        String patt = "^[0-9]";
        Pattern r = Pattern.compile(patt);
        Matcher matcher = r.matcher(dataMap.get("WBS").toString());
        String partWbs = matcher.replaceAll("");
        data.put("PART_WBS", partWbs);
        data.put("DRAWING_NUM", dataMap.get("DRAWING_NUM"));
        data.put("MATERIAL_NAME", dataMap.get("MATERIAL_NAME").toString().split("^")[0]);
        data.put("year", String.valueOf(Calendar.getInstance().get(1)).replace(",", ""));
        data.put("WORD_NO", "PageFirst");
        data.put("ORDER_CODE", dataMap.get("ORDER_CODE"));
        WordAction wordAction = new WordAction();
        String filePath = wordAction.createWord(null, data, dirPath);
        return filePath;
    }

    private void mergeDoc(List<String> filePathList, String destPath) throws Exception {
        if (CollectionUtils.isNotEmpty(filePathList)) {
            this.mergeDoc(filePathList, null, destPath);
        }
    }

    private void mergeDoc(List<String> filePathList, String dirPath, String destPath) throws Exception {
        if (CollectionUtils.isEmpty(filePathList) && StringUtils.isNotBlank(destPath)) {
            File dirRoot = new File(dirPath);
            FilenameFilter filter = (dir, name) -> name.endsWith(".doc") || name.endsWith(".docx");
            if (dirRoot.isDirectory()) {
                filePathList = Arrays.asList(dirRoot.list(filter));
            }
        }
        List<String> pathList = new ArrayList<String>();
        new File(filePathList.get(0)).delete();
        Document temp = new Document();
        for (int i = 1; i < filePathList.size(); ++i) {
            if (i % 10 == 0) {
                String path = PathUtil.getFileDir() + RandomUtils.nextInt(1, 100) + RandomUtils.nextInt(1, 1000) + ".doc";
                temp.saveToFile(path, FileFormat.Docx_2013);
                pathList.add(path);
                temp = new Document();
            }
            if (new File(filePathList.get(i)).exists()) {
                temp.insertTextFromFile(filePathList.get(i), FileFormat.Docx_2013);
                File file = new File(filePathList.get(i));
                file.delete();
                if (i == filePathList.size() - 1) {
                    String path2 = PathUtil.getFileDir() + RandomUtils.nextInt(1, 100) + RandomUtils.nextInt(1, 1000) + ".doc";
                    temp.saveToFile(path2, FileFormat.Docx_2013);
                    pathList.add(path2);
                }
            }
        }
        Document document = new Document(filePathList.get(0));
        for (int j = 1; j < pathList.size(); ++j) {
            document.insertTextFromFile(pathList.get(j), FileFormat.Docx_2013);
            File file2 = new File(pathList.get(j));
            file2.delete();
        }
        document.saveToFile(destPath, FileFormat.Docx_2013);
    }

    private List<String> generatorFiles(List<Map<String, Object>> headerList, String dirPath) {
        CardInfoDetailMapper cardInfoDetailMapper = this.sqlSession.getMapper(CardInfoDetailMapper.class);
        List<String> filePathList = new ArrayList<String>();
        for (Map<String, Object> map : headerList) {
            String filePath = "";
            if (map.containsKey("topListView")) {
                filePath = this.generatorTopListView((List<Map<String, Object>>) map.get("topListView"), dirPath);
            } else {
                filePath = this.generatorSingleFile(cardInfoDetailMapper, map, map.get("JOB_NUMBER").toString(), dirPath);
            }
            if (StringUtils.isNotBlank(filePath)) {
                filePathList.add(filePath);
            }
        }
        this.sqlSession.close();
        return filePathList;
    }

    private String generatorTopListView(List<Map<String, Object>> listInfo, String dirPath) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("PRODUCT_NO", listInfo.get(1).get("PRODUCT_NO"));
        data.put("WBS", listInfo.get(1).get("WBS").toString());
        data.put("DRAWING_NUM", listInfo.get(1).get("DRAWING_NUM"));
        data.put("MATERIAL_NAME", listInfo.get(1).get("MATERIAL_NAME").toString().split("^")[0]);
        data.put("WORD_NO", "GZK_GXHZ");
        data.put("ORDER_CODE", listInfo.get(1).get("ORDER_CODE"));
        WordAction wordAction = new WordAction();
        String filePath = wordAction.createWord(listInfo, data, dirPath);
        return filePath;
    }

    /**
     * 根据单个头信息生成单个 Word 文档
     *
     * @param map       头信息数据
     * @param jobNumber 工单号
     * @param dirPath   存放文件的文件夹路径
     * @return 返回生成文件的绝对路径
     */
    private String generatorSingleFile(CardInfoDetailMapper cardInfoDetailMapper, Map<String, Object> map, String jobNumber, String dirPath) {
        if (!ObjectUtils.allNotNull(map.get("PRODUCT_NO"))) {
            String message = "";
            /*if(!ObjectUtils.allNotNull(map.get("CARD_NAME")))
                message = "跟踪卡名称为空";*/
            if (!ObjectUtils.allNotNull(map.get("PRODUCT_NO")))
                message = "产品单件编码为空";
            throw new MyNullPointRunTimeException(message);
        }
        List<Map<String, Object>> listInfo;
        if (map.get("TEMPLATE_ID").toString().split("_").length > 1) {
            if (map.get("TEMPLATE_ID").toString().equals("XT_F016")) {
                listInfo = cardInfoDetailMapper.getF016CardInfoList(jobNumber, map.get("PROCESS_ID").toString(), map.get("PRODUCT_NO").toString());
            } else if (map.get("TEMPLATE_ID").toString().equals("XT_F018")) {
                listInfo = cardInfoDetailMapper.getF018CardInfoList(map.get("TEMPLATE_ID").toString(), jobNumber, map.get("PROCESS_ID").toString(), map.get("PRODUCT_NO").toString());
            } else if (map.get("TEMPLATE_ID").toString().equals("XT_F003")) {
                listInfo  = cardInfoDetailMapper.getF003CardInfoList(jobNumber, map.get("PROCESS_ID").toString(), map.get("PRODUCT_NO").toString());
            } else {
                listInfo = cardInfoDetailMapper.getCardInfoList(map.get("TEMPLATE_ID").toString(), jobNumber, map.get("PROCESS_ID").toString(), map.get("PRODUCT_NO").toString());
            }
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listInfo)) {
                this.setHeaderAnotherData(map, listInfo.get(0));
            }

            WordAction wordAction = new WordAction();
            String filePath = wordAction.createWord(listInfo, map, dirPath);
            return filePath;
        } else {
            return null;
        }

    }

    private List<Map<String, Object>> checkForF016(List<Map<String, Object>> listSource, List<Map<String, Object>> result, CardInfoDetailMapper cardInfoDetailMapper, TrackingCardSourceMapper trackingCardSourceMapper) throws Exception {
        for (Map<String, Object> map : listSource) {
            if (!map.containsKey("topListView") && map.get("TEMPLATE_ID").toString().equals("XT_F016")) {
                String wipId = map.get("WIP_ID").toString();
                List<Map<String, Object>> f016Data = cardInfoDetailMapper.getCardInfoListBySingleCode(map.get("JOB_NUMBER").toString(), map.get("PRODUCT_NO").toString());
                for (Map<String, Object> f016Map : f016Data) {
                    if (ObjectUtils.allNotNull(f016Map.get("SINGLE_CODE"))) {
                        String jobNumber = f016Map.get("JOB_NO").toString();
                        String singleCode = f016Map.get("SINGLE_CODE").toString();
                        listSource = trackingCardSourceMapper.selectLikeBySingleCode(singleCode);
                        if (CollectionUtils.isNotEmpty(listSource)) {
                            Map<String, Object> topListView = new HashMap<String, Object>();
                            topListView.put("topListView", listSource);
                            List<Map<String, Object>> temp = CopyUtils.deepCopyList(result);
                            temp.add(topListView);
                            result = temp;
                            result.addAll(listSource);
                        }
                        this.checkForF016(listSource, result, cardInfoDetailMapper, trackingCardSourceMapper);
                    }
                }
            }
        }
        return result;
    }

    public void setHeaderAnotherData(Map<String, Object> map, Map<String, Object> data) {
        CardInfoDetailMapper cardInfoDetailMapper = this.sqlSession.getMapper(CardInfoDetailMapper.class);
        if (map.get("TEMPLATE_ID").toString().equals("XT_F018") || map.get("TEMPLATE_ID").toString().equals("XT_F021")) {
            if (ObjectUtils.allNotNull(data.get("JDB_ID"))) {
                String jdbName = cardInfoDetailMapper.getNameByGuid(data.get("JDB_ID").toString());
                map.put("JDB_NAME", jdbName);
            }
            if (ObjectUtils.allNotNull(data.get("GYY_ID"))) {
                String gyyName = cardInfoDetailMapper.getNameByGuid(data.get("GYY_ID").toString());
                map.put("GYY_NAME", gyyName);
            }
            if (ObjectUtils.allNotNull(data.get("ZGJSY_ID"))) {
                String jsyName = cardInfoDetailMapper.getNameByGuid(data.get("ZGJSY_ID").toString());
                map.put("JSY_NAME", jsyName);
            }
            if (ObjectUtils.allNotNull(data.get("JIANYAN_ID"))) {
                String jianyanName = cardInfoDetailMapper.getNameByGuid(data.get("JIANYAN_ID").toString());
                map.put("JYY_NAME", jianyanName);
            }
            if (ObjectUtils.allNotNull(data.get("SJRY_ID"))) {
                String sjrName = cardInfoDetailMapper.getNameByGuid(data.get("SJRY_ID").toString());
                map.put("SJY_NAME", sjrName);
            }
        }
        if (map.get("TEMPLATE_ID").toString().equals("XT_F002") && ObjectUtils.allNotNull(data.get("CJLD_ID"))) {
            String jianyanName = cardInfoDetailMapper.getNameByGuid(data.get("CJLD_ID").toString());
            map.put("CJLD_NAME", jianyanName);
        }
    }

}
