package com.example.pattern.singleton;

import com.example.pattern.singleton.lazy.StaticInnerClassSingleton;
import com.example.pattern.singleton.register.ThreadLocalSingleton;

public class SingletonTest {
    public static void main(String[] args) {
//        HungrySingleton hungrySingleton1 = HungrySingleton.getInstance();
//        HungrySingleton hungrySingleton2 = HungrySingleton.getInstance();
//        System.out.println(hungrySingleton1 == hungrySingleton2);
//
//        HungryStaticSingleton hungryStaticSingleton1 = HungryStaticSingleton.getInstance();
//        HungryStaticSingleton hungryStaticSingleton2 = HungryStaticSingleton.getInstance();
//        System.out.println(hungryStaticSingleton1 == hungryStaticSingleton2);

//        ConcurrentExecutor.execute(() -> System.out.println(SimpleLazySingleton.getInstance()), 2);
//        ConcurrentExecutor.execute(() -> System.out.println(SyncLazySingleton.getInstance()), 2);
//        ConcurrentExecutor.execute(() -> System.out.println(DoubleCheckSyncLazySingleton.getInstance()), 2);
        ConcurrentExecutor.execute(() -> System.out.println(StaticInnerClassSingleton.getInstance()), 2);
//        ConcurrentExecutor.execute(() -> System.out.println(EnumSingleton.getInstance()), 2);
//        ConcurrentExecutor.execute(() -> System.out.println(RegisterSingleton.getInstance()), 10);
        ConcurrentExecutor.execute(() -> {
            System.out.println(ThreadLocalSingleton.getInstance());
            System.out.println(ThreadLocalSingleton.getInstance());
        }, 2);
    }
}
