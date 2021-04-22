package com.nokida.trackingcard.dao;

import com.nokida.trackingcard.pojo.TrackingCardSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TrackingCardSourceMapper {

    @Select("select * from XT_GZK_LIST where job_number = #{jobNumber}")
    @ResultType(java.util.Map.class)
    List<Map<String,Object>> selectByJobNumber(@Param("jobNumber") String jobNumber);

    @Select({ "select * from XT_GZK_LIST where job_number = #{jobNumber} and product_no = #{singleCode} order by to_number(process_no)" })
    @ResultType(Map.class)
    List<Map<String, Object>> selectByJobNumberAndWbs(@Param("jobNumber") final String jobNumber, @Param("singleCode") final String singleCode);

    @Select({ "select * from XT_GZK_LIST where productno = #{singleCode}  order by to_number(process_no)" })
    @ResultType(Map.class)
    List<Map<String,Object>> selectLikeBySingleCode(@Param("singleCode") final String singleCode);

    @Select("select * from XT_GZK_LIST_CP where WBS = #{wbs} and PRODUCTNO = '批产'")
    @ResultType(java.util.Map.class)
    List<Map<String,Object>> selectByWbsAndBatch(@Param("wbs") String wbs);
}