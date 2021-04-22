package com.nokida.trackingcard.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CardInfoDetailMapper {

    @ResultType(java.util.Map.class)
    @Select("select rownum as ROWNUMBER,j.MATERIAL_NAME,t.* from ${tableName} t left join e_job j on j.JOB_NUMBER = t.job_no  where t.JOB_NO=#{jobNumber} and process_no=#{processNo} and product_no=#{productNo}")
    List<Map<String,Object>> getCardInfoList(@Param("tableName") String tableName, @Param("jobNumber") String jobNumber, @Param("processNo") String processNo,@Param("productNo") String productNo);

    @ResultType(Map.class)
    @Select({ "select rownum as ROWNUMBER,a.* from (select j.MATERIAL_NAME,t.* from ${tableName} t left join e_job j on j.JOB_NUMBER = t.job_no  where t.JOB_NO=#{jobNumber} and process_no=#{processNo} and product_no=#{productNo} order by to_number(CS)) a" })
    List<Map<String, Object>> getF018CardInfoList(@Param("tableName") final String tableName, @Param("jobNumber") final String jobNumber, @Param("processNo") final String processNo, @Param("productNo") final String productNo);

    @ResultType(java.util.Map.class)
    @Select("select rownum as ROWNUMBER,v.WLMS as MATERIAL_NAME,t.*,v.ZLDJ,v.XXGF from XT_F016 t left join e_job j on j.JOB_NUMBER = t.job_no left join V_YCL v on v.WLBH = t.MATERIAL_NO  where t.JOB_NO=#{jobNumber} and process_no=#{processNo} and product_no=#{productNo}")
    List<Map<String,Object>> getF016CardInfoList( @Param("jobNumber") String jobNumber, @Param("processNo") String processNo,@Param("productNo") String productNo);


    @ResultType(Map.class)
    @Select({ "select t.* from XT_F016 t  where JOB_NO = #{jobNumber} and PRODUCT_NO = #{singleCode}" })
    List<Map<String,Object>> getCardInfoListBySingleCode(@Param("jobNumber") final String jobNumber, @Param("singleCode") final String singleCode);

    @ResultType(java.lang.String.class)
    @Select("select XM from p_personal where guid = #{guid}")
    String getNameByGuid(@Param("guid") Object guid);

    @ResultType(java.util.Map.class)
    @Select("select rownum ROWNUMBER,\n" +
            "       WBS as WBS,\n" +
            "       ZJMC as ZJMC,\n" +
            "       TH as DRAWING_NUM,\n" +
            "       SN as PRODUCT_NO,\n" +
            "       WORK_CENTER_NAME as GZ,\n" +
            "       JL as JYJL,\n" +
            "       CSJG as JCJG,\n" +
            "       CZR,\n" +
            "       HJR,\n" +
            "       MC as JYXM,\n" +
            "       XX as JSYQ,\n" +
            "       HJSJ as HJRQ,\n" +
            "       CZSJ as CZRQ,\n" +
            "       ROUT_REMARK as QTJL,\n" +
            "       (PROCESS_NO || ':' || step_Name) as GXMC\n" +
            "  from v_gzk_hzgx t\n" +
            " where SN = #{singleCode}")
    List<Map<String,Object>> getSecondPageData(String singleCode);



}
