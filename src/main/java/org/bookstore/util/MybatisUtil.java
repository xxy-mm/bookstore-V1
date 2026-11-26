package org.bookstore.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;

public class MybatisUtil {

    private static final String RESOURCE_URL = "mybatis-config.xml";

    private static final SqlSessionFactory sqlSessionFactory;

    static {

        try (InputStream inputStream = Resources.getResourceAsStream(RESOURCE_URL)) {
            if (inputStream == null) {
                throw new IllegalStateException("Mybatis config not found: " + RESOURCE_URL);
            }
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }


    }

    private MybatisUtil() {
    }

    public static SqlSession openSession() {

        return sqlSessionFactory.openSession();
    }

    public static <M, R> R execute(Class<M> mapperClass, Function<M, R> action) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            return action.apply(sqlSession.getMapper(mapperClass));
        }
    }

    public static <R> R inTransaction(Function<SqlSession, R> work) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) {
            try {
                R result = work.apply(sqlSession);
                sqlSession.commit();
                return result;
            } catch (Exception e) {
                sqlSession.rollback();
                throw e;
            }
        }
    }


    public static void inTransaction(Consumer<SqlSession> consumer) {
        inTransaction(sqlSession -> {
            consumer.accept(sqlSession);
            return null;
        });
    }

}
