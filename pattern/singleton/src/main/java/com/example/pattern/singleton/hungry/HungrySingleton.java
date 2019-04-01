package com.example.pattern.singleton.hungry;

/**
 * HungrySingleton类加载时，初始化单例对象
 * 优点：线程安全，简单
 * 缺点：浪费空间
 */
public class HungrySingleton {
    private static final HungrySingleton hungrySingleton = new HungrySingleton();

    private HungrySingleton() {

    }

    public static HungrySingleton getInstance() {
        return hungrySingleton;
    }
}
