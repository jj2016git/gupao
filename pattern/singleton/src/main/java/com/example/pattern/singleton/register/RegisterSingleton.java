package com.example.pattern.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegisterSingleton {
    private static Map<String, Object> container = new ConcurrentHashMap<>();

    private RegisterSingleton() {

    }

    /**
     * 多线程并发场景下，可能会产生多例
     *
     * @return
     */
    public static RegisterSingleton getInstance() {
        String className = RegisterSingleton.class.getName();
        if (!container.containsKey(className)) {
            synchronized (container) {
                if (!container.containsKey(className)) {
                    container.put(RegisterSingleton.class.getName(), new RegisterSingleton());
                }
            }
        }
        return (RegisterSingleton) container.get(RegisterSingleton.class.getName());
    }
}
