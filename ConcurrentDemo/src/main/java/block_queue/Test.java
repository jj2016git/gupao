package block_queue;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {
        AtomicInteger taskId = new AtomicInteger(0);
        GPBlockingQueue<Task> taskQueue = new GPBlockingQueue<>(5);

        new Thread(new Producer("p0", taskQueue, taskId)).start();
        new Thread(new Producer("p1", taskQueue, taskId)).start();
        new Thread(new Producer("p2", taskQueue, taskId)).start();

        new Thread(new Consumer(taskQueue)).start();
//        new Thread(new Consumer(taskQueue)).start();
    }
}
