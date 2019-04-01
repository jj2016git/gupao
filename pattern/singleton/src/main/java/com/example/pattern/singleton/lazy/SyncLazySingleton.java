package com.example.pattern.singleton.lazy;

/**
 * 多线程保持单例
 * 同步降低效率
 */
public class SyncLazySingleton {
    private SyncLazySingleton() {
    }

    private static SyncLazySingleton instance;

    public static synchronized SyncLazySingleton getInstance() {
        if (instance == null) {
            instance =  new SyncLazySingleton();
        }
        return instance;
    }
}
