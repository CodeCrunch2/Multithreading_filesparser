package main.java.com.mkudriavtsev.filesparser.controller;

import java.io.File;
import java.util.Queue;

public class FilesProducer implements Runnable {
    private File [] files;
    private Queue<File> queue;
    public static boolean cycle = true;
    FilesProducer(File directory, Queue<File> queue) {
        files = directory.listFiles();
        this.queue = queue;
    }

    @Override
    public void run() {
        for (File file: files) {
            if (file.isFile()) {
                queue.add(file);
            }
        }
        cycle = false;
    }
}
