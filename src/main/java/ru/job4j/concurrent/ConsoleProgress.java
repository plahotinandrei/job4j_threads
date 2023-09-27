package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        int i = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + process[i++]);
                i = i == process.length  ? 0 : i;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.print("\r loaded.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
