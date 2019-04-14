package com.example.gpspring;

import com.example.gpspring.mvcframework.context.GPApplicationContext;

public class Test {
    public static void main(String[] args) {
        GPApplicationContext context = new GPApplicationContext("classpath:application.properties");
        Object object = null;
        try {
            object = context.getBean("demoController");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(object);
        System.out.println(context);
    }
}
