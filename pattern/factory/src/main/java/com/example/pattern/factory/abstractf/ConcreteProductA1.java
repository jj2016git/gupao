package com.example.pattern.factory.abstractf;

public class ConcreteProductA1 implements ProductA {
    /**
     * 获取价格
     *
     * @return
     */
    @Override
    public String getPrice() {
        return "ConcreteProductA1 price = 10++";
    }
}
