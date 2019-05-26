package block_queue;

import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private GPBlockingQueue<Task> taskQueue;
    private String producerName;
    private AtomicInteger taskId;

    public Producer(String producerName, GPBlockingQueue<Task> taskQueue, AtomicInteger taskId) {
        this.producerName = producerName;
        this.taskQueue = taskQueue;
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

        try {
            boolean result = this.taskQueue.put(new Task(taskId.getAndAdd(1), "task produced by " + producerName));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
