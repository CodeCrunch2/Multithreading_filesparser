package main.java.com.mkudriavtsev.filesparser.controller;

import main.java.com.mkudriavtsev.filesparser.exceptions.DirectoryEmptyException;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MainController {
    public void parseFiles(File directory) throws DirectoryEmptyException {
        if (Objects.requireNonNull(directory.listFiles()).length != 0) {
            int processorCount = Runtime.getRuntime().availableProcessors();
            BlockingQueue<File> queue = new ArrayBlockingQueue<>(1);
            Thread filesProducer =  new Thread(new FilesProducer(directory, queue));
            Thread filesConsumer = new Thread(new FilesConsumer(queue,processorCount));
            filesProducer.start();
            filesConsumer.start();
            try {
                filesProducer.join();
                filesConsumer.join();
            }
            catch (InterruptedException e) {
                System.out.println("Операция прервана");
            }

        }
        else {
            throw new DirectoryEmptyException("Директория пуста");
        }

    }
}
