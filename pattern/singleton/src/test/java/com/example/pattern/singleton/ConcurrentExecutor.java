package com.example.pattern.singleton;

import com.example.pattern.singleton.lazy.SimpleLazySingleton;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentExecutor {
    public static void execute(SingletonHandler handler, int concurrentNum) {
        CountDownLatch countDownLatch = new CountDownLatch(concurrentNum);
        ExecutorService service = Executors.newFixedThreadPool(concurrentNum);

        for (int i = 0; i < concurrentNum; i++) {
            service.execute(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.handle();
            });
            countDownLatch.countDown();
        }
    }

    public interface SingletonHandler{
        void handle();
    }
}
