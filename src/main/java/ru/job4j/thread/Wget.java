package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String filename;

    public Wget(String url, int speed, String filename) {
        this.url = url;
        this.speed = speed;
        this.filename = filename;
    }

    @Override
    public void run() {
        var file = new File(filename);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            int bitesAll = 0;
            var downloadAt = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                bitesAll += bytesRead;
                out.write(dataBuffer, 0, bytesRead);
                if (bitesAll >= speed) {
                    long pause = 1000L - (System.currentTimeMillis() - downloadAt);
                    if (pause > 0) {
                        Thread.sleep(pause);
                    }
                    downloadAt = System.currentTimeMillis();
                    bitesAll = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        String filePattern = "^.*\\.[^.]{3,}$";
        if (args.length != 3) {
            throw new IllegalArgumentException("Not found all required arguments");
        }
        try {
            URL url = new URL(args[0]);
            if (!(new File(url.getPath())).getName().matches(filePattern)) {
                throw new IllegalArgumentException("This url does not contain fail");
            }
            url.toURI();
        } catch (URISyntaxException | MalformedURLException exception) {
            throw new IllegalArgumentException("Invalid url");
        }
        if (Integer.parseInt(args[1]) <= 0)  {
            throw new IllegalArgumentException("Invalid speed");
        }
        if (!args[2].matches(filePattern)) {
            throw new IllegalArgumentException("Invalid filename");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String filename = args[2];
        Thread wget = new Thread(new Wget(url, speed, filename));
        wget.start();
        wget.join();
    }
}
