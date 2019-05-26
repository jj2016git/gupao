package producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {
        Object readLock = new Object();
        Object writeLock = new Object();
        AtomicInteger taskId = new AtomicInteger(0);
        BlockingQueue<Task> taskQueue = new LinkedBlockingDeque<>(5);

        new Thread(new Producer("p1", taskQueue, readLock, writeLock, taskId)).start();
        new Thread(new Producer("p2", taskQueue, readLock, writeLock, taskId)).start();
        new Thread(new Producer("p3", taskQueue, readLock, writeLock, taskId)).start();

        new Thread(new Consumer(taskQueue, readLock, writeLock)).start();
        new Thread(new Consumer(taskQueue, readLock, writeLock)).start();
    }
}
