
package com.example.pattern.factory.simple;

import com.example.pattern.factory.IProduct;


public class SimpleFactory {
    public IProduct create(Class productClazz) {
        if (productClazz != null) {
            try {
                return (IProduct) productClazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
