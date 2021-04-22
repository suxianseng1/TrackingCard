package com.nokida.trackingcard.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtil {

    //首先创建静态成员变量sqlSessionFactory，静态变量被所有的对象所共享。
    public static SqlSessionFactory sqlSessionFactory = null;

    private SqlSessionFactoryUtil() {}

    //使用静态代码块保证线程安全问题
    static{

        String resource = "mybatis.xml";

        try {

            InputStream inputStream = Resources.getResourceAsStream(resource);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}