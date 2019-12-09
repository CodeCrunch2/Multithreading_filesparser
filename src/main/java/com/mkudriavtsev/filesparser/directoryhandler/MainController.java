package main.java.com.mkudriavtsev.filesparser.directoryhandler;

import main.java.com.mkudriavtsev.filesparser.exceptions.DirectoryEmptyException;
import java.io.File;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainController {
    public void parseFiles(File directory) throws DirectoryEmptyException {
        if (Objects.requireNonNull(directory.listFiles()).length != 0) {
            int processorCount = Runtime.getRuntime().availableProcessors();
            Queue<File> queue = new ConcurrentLinkedQueue<>();
            Thread filesProducer =  new Thread(new FilesProducer(directory, queue));
            Thread filesConsumer = new Thread(new FilesConsumer(queue,processorCount));
            filesProducer.start();
            filesConsumer.start();
            try {
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
