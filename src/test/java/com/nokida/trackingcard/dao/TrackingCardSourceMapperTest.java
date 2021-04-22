package com.nokida.trackingcard.dao;

import com.nokida.trackingcard.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

@Slf4j
public class TrackingCardSourceMapperTest extends TestBase {


    @Test
    public void testSelectByJobNumber(){
        SqlSession sqlSession = factory.openSession();
        TrackingCardSourceMapper trackingCardSourceMapper = sqlSession.getMapper(TrackingCardSourceMapper.class);
        System.out.println(trackingCardSourceMapper.selectByJobNumber("00100000135400190-1").toString());

    }
}
