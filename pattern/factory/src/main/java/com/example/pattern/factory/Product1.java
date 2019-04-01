package com.example.pattern.factory;

public class Product1 implements IProduct {
    /**
     * 获取价格
     *
     * @return
     */
    @Override
    public String getPrice() {
        return "product1 price = $10";
    }
}
