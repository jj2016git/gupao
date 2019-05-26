package block_queue;

public class Consumer implements Runnable {

    private GPBlockingQueue<Task> taskQueue;

    public Consumer(GPBlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
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
            while (true) {
                Task task = this.taskQueue.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
