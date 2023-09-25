package ru.job4j.concurrent;

import java.util.Iterator;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        Iterator<Character> symbol = symbolIt();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + symbol.next());
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.print("\r loaded.");
        }
    }

    public static void main(String[] args) {
        try {
            Thread progress = new Thread(new ConsoleProgress());
            progress.start();
            Thread.sleep(5000);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Iterator<Character> symbolIt() {

        return new Iterator<>() {
            private final char[] process = new char[] {'-', '\\', '|', '/'};
            private int start = 0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Character next() {
                if (start > 3) {
                    start = 0;
                }
                return process[start++];
            }
        };
    }
}
