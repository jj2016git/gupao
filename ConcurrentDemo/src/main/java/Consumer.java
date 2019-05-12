import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue<Task> taskQueue;
    private Object readLock;
    private Object writeLock;

    public Consumer(BlockingQueue<Task> taskQueue, Object readLock, Object writeLock) {
        this.taskQueue = taskQueue;
        this.readLock = readLock;
        this.writeLock = writeLock;
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
            if (this.taskQueue.isEmpty()) {
                synchronized (this.readLock) {
                    try {
                        this.readLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Task task = this.taskQueue.poll();
                System.out.println(task);
                synchronized (this.writeLock) {
                    this.writeLock.notifyAll();
                }
            }
        }
    }
}
