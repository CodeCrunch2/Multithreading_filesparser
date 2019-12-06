package main.java.com.mkudriavtsev.filesparser.controller;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilesConsumer implements Runnable {
    private BlockingQueue<File> queue;
    private ExecutorService es;

    FilesConsumer(BlockingQueue<File> queue, int processorCount) {
        this.queue = queue;
        es = Executors.newFixedThreadPool(processorCount);
    }
    @Override
    public void run() {
        try {
            do {
                File file = queue.take();
                es.execute(() -> {
                    String name = file.getName();
                    int index = name.lastIndexOf('.');
                    System.out.println("Имя : " + name.substring(0, index) + "\t" + "Расширение : " +
                            name.substring(index + 1) + "\t" + "Размер : " + file.length() + " байт" + "\t" +
                            "Thread: " + Thread.currentThread().getName());
                });
            }
            while (!queue.isEmpty() && !FilesProducer.isFinished);
            es.shutdown();
        }
        catch (InterruptedException e) {
            System.out.println("Операция прервана");
        }


    }
}
