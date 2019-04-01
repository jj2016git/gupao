package com.example.pattern.factory.abstractf;

public class ConcreteProductA2 implements ProductA {
    /**
     * 获取价格
     *
     * @return
     */
    @Override
    public String getPrice() {
        return "ConcreteProductA2 price = 10++";
    }
}
