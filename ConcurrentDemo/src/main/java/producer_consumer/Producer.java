package producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private BlockingQueue<Task> taskQueue;
    private Object readLock;
    private Object writeLock;
    private String producerName;
    private AtomicInteger taskId;

    public Producer(String producerName, BlockingQueue<Task> taskQueue, Object readLock, Object writeLock, AtomicInteger taskId) {
        this.producerName = producerName;
        this.taskQueue = taskQueue;
        this.readLock = readLock;
        this.writeLock = writeLock;
        this.taskId = taskId;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while(true) {
            if (this.taskQueue.remainingCapacity() == 0) {
                synchronized (this.writeLock) {
                    try {
                        this.writeLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                this.taskQueue.offer(new Task(taskId.getAndAdd(1), "task produced by " + producerName));
                synchronized (this.readLock) {
                    this.readLock.notifyAll();
                }
            }
        }
    }
}
