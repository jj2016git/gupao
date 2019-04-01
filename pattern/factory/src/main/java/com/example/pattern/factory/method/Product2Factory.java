package com.example.pattern.factory.method;

import com.example.pattern.factory.IFactory;
import com.example.pattern.factory.IProduct;
import com.example.pattern.factory.Product2;

public class Product2Factory implements IFactory {
    @Override
    public IProduct create() {
        return new Product2();
    }
}
