package org.bookstore.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {

    private static final String RESOURCE_URL = "mybatis-config.xml";

    private static final SqlSessionFactory sqlSessionFactory;

    static {
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(RESOURCE_URL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    public static SqlSession openSession() {

        return sqlSessionFactory.openSession();
    }

}
