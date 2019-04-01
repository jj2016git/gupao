package com.example.pattern.factory;

import com.example.pattern.factory.abstractf.ConcreteFactory1;
import com.example.pattern.factory.abstractf.ConcreteFactory2;
import com.example.pattern.factory.abstractf.ProductA;
import com.example.pattern.factory.abstractf.ProductB;
import com.example.pattern.factory.method.Product2Factory;
import com.example.pattern.factory.simple.SimpleFactory;

public class client {
    public static void main(String[] args) {
        // get product from simpleFactory
        IProduct productFromSimpleFactory = getProductBySimpleFactory(Product1.class);
        System.out.println(productFromSimpleFactory.getPrice());

        // get product from factoryMethod
        IProduct productFromFactoryMethod = getProductByFactoryMethod(new Product2Factory());
        System.out.println(productFromFactoryMethod.getPrice());

        // get product from abstract factory
        ProductA productA = getProductAFromAbsFactory(new ConcreteFactory1());
        System.out.println(productA.getPrice());
        ProductB productB = getProductBFromAbsFactory(new ConcreteFactory2());
        System.out.println(productB.getPrice());
    }

    private static IProduct getProductBySimpleFactory(Class productClazz) {
        SimpleFactory factory = new SimpleFactory();
        return factory.create(productClazz);
    }

    private static IProduct getProductByFactoryMethod(IFactory factory) {
        return factory.create();
    }

    private static ProductA getProductAFromAbsFactory(com.example.pattern.factory.abstractf.IFactory factory) {
        return factory.createProductA();
    }

    private static ProductB getProductBFromAbsFactory(com.example.pattern.factory.abstractf.IFactory factory) {
        return factory.createProductB();
    }
}
