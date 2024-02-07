package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

public class PoolWorker extends Thread {

    private final SimpleBlockingQueue<Runnable> tasks;

    public PoolWorker(SimpleBlockingQueue<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Runnable task = tasks.poll();
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
