package com.nokida.trackingcard.dao;

import com.nokida.trackingcard.TestBase;
import com.nokida.trackingcard.word.WordAction;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardInfoDetailMapperTest extends TestBase {

    /**
     * F002,F004,F005,F006,F007,F009
     */
    @Test
    public void testGetCardInfoList1(){
        SqlSession sqlSession = factory.openSession();
        CardInfoDetailMapper cardInfoDetailMapper = sqlSession.getMapper(CardInfoDetailMapper.class);
        List<java.util.Map<String, Object>> listInfo = cardInfoDetailMapper.getCardInfoList("XT_F002","00100000135400190-1","","");
        WordAction wordAction = new WordAction();
        TrackingCardSourceMapper trackingCardSourceMapper = sqlSession.getMapper(TrackingCardSourceMapper.class);
        List<Map<String,Object>> headerList = trackingCardSourceMapper.selectByJobNumber("00100000135400190-1");
        Map<String,Object> map = headerList.get(0);
        map.put("cardName","F015");
        wordAction.createWord(listInfo,map,"test123");
    }

    /**
     * F015
     */
    @Test
    public void testGetCardInfoList2(){
        SqlSession sqlSession = factory.openSession();
        CardInfoDetailMapper cardInfoDetailMapper = sqlSession.getMapper(CardInfoDetailMapper.class);
        List<java.util.Map<String, Object>> listInfo = cardInfoDetailMapper.getCardInfoList("XT_F002","00100000135400190-1","","");
        WordAction wordAction = new WordAction();
        TrackingCardSourceMapper trackingCardSourceMapper = sqlSession.getMapper(TrackingCardSourceMapper.class);
        List<Map<String,Object>> headerList = trackingCardSourceMapper.selectByJobNumber("00100000135400190-1");
        Map<String,Object> map = headerList.get(0);
        map.put("cardName","F015");
        wordAction.createWord(listInfo,map,"test123");
    }
}
