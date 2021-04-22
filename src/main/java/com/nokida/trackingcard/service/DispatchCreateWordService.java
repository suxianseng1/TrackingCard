package com.nokida.trackingcard.service;

import com.nokida.trackingcard.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSessionFactory;

public interface DispatchCreateWordService {


    String createWordByJobNumber(String uuid,String jobNumber)  throws Exception ;

    String createWordBySingleCode(String wbs,String singleCode,String isBatchFlag) throws Exception;

}
