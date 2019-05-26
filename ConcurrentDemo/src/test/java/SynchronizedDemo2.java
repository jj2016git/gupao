import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedDemo2 {
    static AtomicInteger count = new AtomicInteger(50);
    static AtomicInteger soldCount = new AtomicInteger(0);

    public static void sell() {
        if (count.get() > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int tmp;
            if ((tmp = count.get()) > 0 && count.compareAndSet(tmp, tmp - 1)) {
                soldCount.getAndAdd(1);
                System.out.println("剩余票" + count.get());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (count.get() > 0) {
                    sell();
                }
            }).start();
        }
        Thread.sleep(5000);
        System.out.println("result:" + count);
        System.out.println("sold count" + soldCount);
    }
}
