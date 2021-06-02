package com.zzh.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @Author 赵增辉
 * @Date 2021/6/2 18:32
 * @Version 1.0
 * 创建全局唯一的 SqlSessionFactory
 */
public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getInstance() {
        if (sqlSessionFactory == null) { //Single Checked
            synchronized (MyBatisUtils.class) {
                if (sqlSessionFactory == null) { //Double Checked
                    Reader reader = null;
                    try {
                        reader = Resources.getResourceAsReader("mybatis-config.xml");
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    public static void closeSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }


}
