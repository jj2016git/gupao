package tool.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockDemo {
    static Map<String, Object> cacheMap = new ConcurrentHashMap<>();
    static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    static ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    static ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

    public static Object get(String key) {
        try {
            readLock.lock();
            return cacheMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static Object put(String key, Object value) {
        try {
            writeLock.lock();
            return cacheMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i<5; i++) {
            new Thread(()-> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Object value = get("foo");
                    System.out.println(Thread.currentThread().getName() + " get " + value);
                }
            }, "read thread " + i).start();
        }

        for (int i = 0; i<3; i++) {
            new Thread(()-> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Object value = put("foo_" + UUID.randomUUID().toString(), "bar");
                    System.out.println(Thread.currentThread().getName() + " put " + value);
                }
            }, "write thread " + i).start();
        }
    }
}
