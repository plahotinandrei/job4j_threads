package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread loader = new Thread(
                () -> {
                    try {
                        for (int i = 0; i <= 100; i++) {
                            System.out.print("\rLoading : " + i  + "%");
                            Thread.sleep(Double.valueOf(100 + Math.random() * 600).longValue());
                        }
                        System.out.print("\rLoaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        loader.start();
    }
}
