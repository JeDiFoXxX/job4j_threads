package ru.job4j.thread;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var dataBuffer = new byte[1024];
        var countBytes = 0;
        try (InputStream input = new URL(url).openStream()) {
            var startTime = System.currentTimeMillis();
            for (int bytesRead; (bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1;) {
                countBytes += bytesRead;
                if (countBytes >= speed) {
                    var endTime = System.currentTimeMillis() - startTime;
                    if (endTime < speed) {
                        Thread.sleep(speed - endTime);
                    }
                    countBytes = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Incorrect number of arguments");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int rslConnection = connection.getResponseCode();
            connection.disconnect();
            if (rslConnection > 399) {
                throw new RuntimeException("Error connect: " + rslConnection);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL");
        } catch (IOException e) {
            throw new IllegalStateException("Unable to establish connection to the server: " + e.getMessage());
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Download speed cannot be negative or zero");
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
