package com.imooc.mybatis;


/**
 * @Author 赵增辉
 * @Date 2021/6/2 14:03
 * @Version 1.0
 */

import com.zzh.mybatis.dto.GoodsDTO;
import com.zzh.mybatis.entity.Goods;
import com.zzh.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisUtils.getInstance().openSession();
            List<Goods> goodsList = sqlSession.selectList("goods.selectAll");
            for (Goods good : goodsList) {
                System.out.println(good.getTitle());

            }
        }catch (Exception e){
            throw e;
        }finally{
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectByID() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getInstance().openSession();
            Goods good = sqlSession.selectOne("goods.selectById", 1602);
            System.out.println(good.getTitle() + "\t" + good.getCurrentPrice());
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectByPriceRange() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getInstance().openSession();
            Map map = new HashMap();
            map.put("min", 100);
            map.put("max", 500);
            map.put("limit", 10);
            List<Goods> goods = sqlSession.selectList("goods.selectByPriceRange", map);
            for (Goods i : goods) {
                System.out.println(i.getTitle() + "\t" + i.getCurrentPrice());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(sqlSession);

        }
    }


    @Test
    public void testSelectGoodsMap() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getInstance().openSession();
            List<Map> list = sqlSession.selectList("goods.selectGoodsMap");
            for (Map i : list) {
                System.out.println(i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }


    @Test
    public void testSelectGoodsDTO() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getInstance().openSession();
            List<GoodsDTO> goodsDTOSList = sqlSession.selectList("goods.selectGoodsDTO");
            for (GoodsDTO i : goodsDTOSList) {
                System.out.println(i.getGoods().getTitle());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testInsert() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getInstance().openSession();
            Goods goods = new Goods();
            goods.setTitle("测试商品1");
            goods.setSubTitle("测试子标题1");
            goods.setOriginalCost(200f);
            goods.setCurrentPrice(100f);
            goods.setDiscount(0.5f);
            goods.setIsFreeDelivery(1);
            goods.setCategoryId(43);
            //insert()方法返回值代表本次成功插入的记录总数
            int num = sqlSession.insert("goods.insertUseGeneratedKeys", goods);
            sqlSession.commit();//提交事务数据
            System.out.println(goods.getGoodsId());//获取<selectKey>标签返回的新增数据的主键
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();//回滚事务
            }
            throw e;
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        SqlSession session = null;
        try{
            session = MyBatisUtils.getInstance().openSession();
            Goods good = session.selectOne("goods.selectById", 2674);
            good.setTitle("测试商品0");
            good.setSubTitle("测试子标题0");
            int num = session.update("goods.update" , good);
            session.commit();//提交事务数据
        }catch (Exception e){
            if(session != null){
                session.rollback();//回滚事务
            }
            throw e;
        }finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testDelete() throws Exception {
        SqlSession session = null;
        try{
            session = MyBatisUtils.getInstance().openSession();
            int num = session.delete("goods.delete" , 2675);
            session.commit();//提交事务数据
        }catch (Exception e){
            if(session != null){
                session.rollback();//回滚事务
            }
            throw e;
        }finally {
            MyBatisUtils.closeSession(session);
        }
    }
}
