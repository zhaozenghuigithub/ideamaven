package com.imooc.mybatis;


/**
 * @Author 赵增辉
 * @Date 2021/6/2 14:03
 * @Version 1.0
 */

import com.zzh.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

public class MyBatisTest {

    @Test
    public void testSqlSessionFactory() {
        try {
            //加载配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            System.out.println("sqlSessionFactory加载成功");
            SqlSession sqlSession = null;
            try {
                sqlSession = sqlSessionFactory.openSession();
                Connection connection = sqlSession.getConnection();
                System.out.println(connection);
            }catch (Exception e){

            }finally {
                if (sqlSession != null){
                    sqlSession.close();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMuBatisUtils(){
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisUtils.getInstance().openSession();
            Connection connection = sqlSession.getConnection();
            System.out.println(connection);
        }catch (Exception e){
            throw e;
        }finally{
            MyBatisUtils.closeSession(sqlSession);
        }
    }
}
