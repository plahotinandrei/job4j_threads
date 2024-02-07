package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    private boolean isStopped = false;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        IntStream.range(0, size).forEach((i) -> {
            Thread thread = new PoolWorker(tasks);
            threads.add(thread);
            thread.start();
        });
    }

    public void work(Runnable job) throws InterruptedException {
        if (!isStopped) {
            tasks.offer(job);
        }
    }

    public void shutdown() {
        isStopped = true;
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 30; i++) {
            int threadNum = i;
            pool.work(() -> {
                System.out.println("Start " + threadNum);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("End " + threadNum);
            });
        }
        pool.shutdown();
    }
}
