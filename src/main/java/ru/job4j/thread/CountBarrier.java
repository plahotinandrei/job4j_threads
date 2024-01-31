package ru.job4j.thread;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (total > count) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(100);
        Thread thread1 = new Thread(
            () -> {
                System.out.println(Thread.currentThread().getName() + " started");
                for (int i = 0; i < 15000; i++) {
                    barrier.count();
                }
            },
        "Thread 1"
        );
        Thread thread2 = new Thread(
                () -> {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " started. Count: " + barrier.getCount());
                },
                "Thread 2"
        );
        thread1.start();
        thread2.start();
    }
}
