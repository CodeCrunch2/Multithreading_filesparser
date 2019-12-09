package main.java.com.mkudriavtsev.filesparser.directoryhandler;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilesConsumer implements Runnable {
    private Queue<File> queue;
    private ExecutorService es;

    FilesConsumer(Queue<File> queue, int processorCount) {
        this.queue = queue;
        es = Executors.newFixedThreadPool(processorCount);
    }
    @Override
    public void run() {
        while (FilesProducer.cycle || queue.size() > 0) {
            File file;
            if ((file = queue.poll()) != null) {
                es.execute(() -> {
                    String name = file.getName();
                    int index = name.lastIndexOf('.');
                    System.out.println("Имя : " + name.substring(0, index) + "\t" + "Расширение : " +
                            name.substring(index + 1) + "\t" + "Размер : " + file.length() + " байт" + "\t" +
                            "Thread: " + Thread.currentThread().getName());
                });
            }
        }
        es.shutdown();
    }
}
