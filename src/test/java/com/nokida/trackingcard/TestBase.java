package com.nokida.trackingcard;

import com.nokida.trackingcard.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSessionFactory;

public class TestBase {

    public SqlSessionFactory factory = SqlSessionFactoryUtil.sqlSessionFactory;


}
