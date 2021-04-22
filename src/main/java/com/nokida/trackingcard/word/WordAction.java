package com.nokida.trackingcard.word;

import com.nokida.trackingcard.util.WordUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("serial")
public class WordAction {

    private String filePath; //文件路径
    private String fileName; //文件名称
    private String fileOnlyName; //文件唯一名称


    public String createWord(List<Map<String, Object>> listInfo,Map<String, Object> dataMap,String filePath) {
        /** 用于组装word页面需要的数据 */

        /** 组装数据 */
        String templName = dataMap.get("WORD_NO").toString();
        dataMap.put("listInfo", listInfo);

        /** 文件名称，唯一字符串 */
        Random r = new Random();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        StringBuffer sb = new StringBuffer();
        sb.append(sdf1.format(new Date()));
        sb.append("_");
        sb.append(r.nextInt(100));
        sb.append(r.nextInt(100));
        String stepId = ObjectUtils.allNotNull(dataMap.get("PROCESS_NO")) ? dataMap.get("PROCESS_NO").toString() : "";
        String productNo = ObjectUtils.allNotNull(dataMap.get("PRODUCT_NO")) ? dataMap.get("PRODUCT_NO").toString() : "";
        //文件唯一名称
        fileOnlyName =/* stepId + "_" + productNo + "_" + */sb + ".doc";

        /** 生成word */
        WordUtil.createWord(dataMap, "templ/"+templName+".ftl", filePath, fileOnlyName);
        return filePath+fileOnlyName;
    }


    /**
     *
     * 下载生成的word文档
     */
    public String dowloadWord() {
        /** 先判断文件是否已生成  */
        try {
            //解决中文乱码
            filePath = URLDecoder.decode(filePath, "UTF-8");
            fileOnlyName = URLDecoder.decode(fileOnlyName, "UTF-8");
            fileName = URLDecoder.decode(fileName, "UTF-8");

            //如果文件不存在，则会跳入异常，然后可以进行异常处理
            new FileInputStream(filePath + File.separator + fileOnlyName);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "dowloadWord";
    }

    /**
     * 返回最终生成的word文档 文件流
     * 下载生成的word文档
     */
    public InputStream getWordFile() {
        try {
            //解决中文乱码
            fileName = URLDecoder.decode(fileName, "UTF-8");

            /** 返回最终生成的word文件流  */
            return new FileInputStream(filePath + File.separator + fileOnlyName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFileOnlyName() {
        return fileOnlyName;
    }


    public void setFileOnlyName(String fileOnlyName) {
        this.fileOnlyName = fileOnlyName;
    }
}