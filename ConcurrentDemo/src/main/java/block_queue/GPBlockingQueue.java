package block_queue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GPBlockingQueue<E> {
    private LinkedList<E> list = new LinkedList<>();
    private AtomicInteger count = new AtomicInteger();
    private volatile int capacity;

    private Lock takeLock = new ReentrantLock();
    private Lock putLock = new ReentrantLock();
    private Condition notEmpty = takeLock.newCondition();
    private Condition notFull = putLock.newCondition();

    public GPBlockingQueue() {
    }

    public GPBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public E take() throws InterruptedException {
        E e = null;
        int c = -1;
        try {
            takeLock.lock();
            while (count.get() == 0) {
                // 阻塞等待元素
                notEmpty.await();
            }

            e = list.poll();
            c = count.getAndDecrement();
            System.out.println("take " + e);
            if (c -1 > 0) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }

        if (c == capacity) {
            signalNotFull();
        }
        return e;
    }

    private void signalNotFull() {
        try {
            putLock.lock();
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }


    public boolean put(E e) throws InterruptedException {
        boolean result = false;
        int c = -1;
        try {
            putLock.lock();
            while (capacity == count.get()) {
                // full, 阻塞
                notFull.await();
            }
            // 必須先添加元素，後修改count，否則take可能拿到null
            result = list.offer(e);
            c = count.getAndIncrement();

            System.out.println("put " + e);
            if (c + 1 < capacity) {
                notFull.signal();
            }
        } finally {
            putLock.unlock();
        }

        if (c == 0) {
            signalNotEmpty();
        }
        return result;
    }

    private void signalNotEmpty() {
        try {
            takeLock.lock();
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

}
