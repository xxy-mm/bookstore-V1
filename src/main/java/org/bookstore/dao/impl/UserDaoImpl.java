package org.bookstore.dao.impl;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.bookstore.dao.UserDao;
import org.bookstore.entity.User;
import org.bookstore.exception.DuplicateKeyException;
import org.bookstore.mapper.UserMapper;
import org.bookstore.util.MybatisUtil;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public long insert(User user) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            long userId = mapper.insertUser(user);
            sqlSession.commit();
            return userId;
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();

            if (cause instanceof SQLException) {
                int errorCode = ((SQLException) cause).getErrorCode();
                if (errorCode == 1062) {
                    throw new DuplicateKeyException("Failed to insert user", e);
                }
            }

            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByUsername(String username) {

        try (SqlSession sqlSession = MybatisUtil.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.findByUsername(username);
        }
    }
}
