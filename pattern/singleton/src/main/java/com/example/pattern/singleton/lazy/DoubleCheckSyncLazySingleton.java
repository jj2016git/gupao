package com.example.pattern.singleton.lazy;

/**
 * 多线程保持单例
 * 同步降低效率
 */
public class DoubleCheckSyncLazySingleton {
    private static DoubleCheckSyncLazySingleton instance;

    private DoubleCheckSyncLazySingleton() {
    }

    public static DoubleCheckSyncLazySingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSyncLazySingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSyncLazySingleton();
                }
            }
        }
        return instance;
    }
}
