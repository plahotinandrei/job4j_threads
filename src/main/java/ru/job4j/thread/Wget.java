package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var filename = getFilenameFromURL(url).orElse("tmp.txt");
        var file = new File(filename);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            var downloadAt = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                long realSpeed = bytesRead * 1000000L / (System.nanoTime() - downloadAt);
                if (realSpeed > speed) {
                    Thread.sleep(realSpeed / speed);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Not found all required arguments");
        }
        try {
            new URL(args[0]).toURI();
        } catch (URISyntaxException | MalformedURLException exception) {
            throw new IllegalArgumentException("Invalid url");
        }
        if (getFilenameFromURL(args[0]).isEmpty()) {
            throw new IllegalArgumentException("This url does not contain fail");
        }
        if (Integer.parseInt(args[1]) <= 0)  {
            throw new IllegalArgumentException("Invalid speed");
        }
    }

    private static Optional<String> getFilenameFromURL(String url) {
        Optional<String> rsl = Optional.empty();
        try {
            String name = new File(new URL(url).getPath()).getName();
            if (name.matches("^.*\\.[^.]{3,}$")) {
                rsl = Optional.of(name);
            }
        } catch (MalformedURLException ignored) { }
        return rsl;
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
