package com.example.pattern.singleton.hungry;

public class HungryStaticSingleton {
    private HungryStaticSingleton() {

    }


    private static final HungryStaticSingleton hungryStaticSingleton;
    static {
        hungryStaticSingleton = new HungryStaticSingleton();
    }

    public static HungryStaticSingleton getInstance() {
        return hungryStaticSingleton;
    }
}
