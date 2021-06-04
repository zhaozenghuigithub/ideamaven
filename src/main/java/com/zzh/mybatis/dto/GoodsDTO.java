package com.zzh.mybatis.dto;

import com.zzh.mybatis.entity.Category;
import com.zzh.mybatis.entity.Goods;

/**
 * @Author 赵增辉
 * @Date 2021/6/4 14:18
 * @Version 1.0
 */
public class GoodsDTO {
    private Goods goods;
    private Category category;
    private String test;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
