package com.example.pattern.factory.method;

import com.example.pattern.factory.IFactory;
import com.example.pattern.factory.IProduct;
import com.example.pattern.factory.Product1;

public class Product1Factory implements IFactory {
    @Override
    public IProduct create() {
        return new Product1();
    }
}
