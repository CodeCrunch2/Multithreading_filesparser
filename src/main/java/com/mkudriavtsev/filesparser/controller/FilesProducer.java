package main.java.com.mkudriavtsev.filesparser.controller;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FilesProducer implements Runnable {
    private File [] files;
    private BlockingQueue<File> queue;
    static boolean isFinished = false;
    FilesProducer(File directory, BlockingQueue<File> queue) {
        files = directory.listFiles();
        this.queue = queue;
    }

    @Override
    public void run() {
        for (File file: files) {
            try {
                if (file.isFile()) {
                    queue.put(file);
                }
            }
            catch (InterruptedException e) {
                System.out.println("Операция прервана");
            }
        }
        isFinished = true;
    }
}
