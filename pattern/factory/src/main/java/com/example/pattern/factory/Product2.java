package com.example.pattern.factory;

public class Product2 implements IProduct {
    /**
     * 获取价格
     *
     * @return
     */
    @Override
    public String getPrice() {
        return "product2 price = Y10";
    }
}
