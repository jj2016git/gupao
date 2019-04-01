package com.example.pattern.singleton.register;

/**
 * ThreadLocal内部类ThreadLocalMap保存单例
 * 由于ThreadLocal变量本身为线程独有，所以不会出现线程不安全导致的多例
 * 但是每个线程都持有一个独特的instance
 */
public class ThreadLocalSingleton {
    private static ThreadLocal<ThreadLocalSingleton> container = new ThreadLocal<>();

    private ThreadLocalSingleton() {

    }

    public static ThreadLocalSingleton getInstance() {
        if (container.get() == null) {
            container.set(new ThreadLocalSingleton());
        }
        return container.get();
    }
}
