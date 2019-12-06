package main.java.com.mkudriavtsev.filesparser.view;

import main.java.com.mkudriavtsev.filesparser.controller.MainController;
import main.java.com.mkudriavtsev.filesparser.exceptions.DirectoryEmptyException;

import java.io.File;
import java.util.Scanner;

public class MainView {
    Scanner scanner = new Scanner(System.in);
    MainController mainController = new MainController();

    public void execute() {
        mainView();
    }
    private void mainView() {
        boolean isInterrupted = false;
        String command;
        System.out.println("Для просмотра файлов введите директорию");
        System.out.println("Для выхода из программы введите exit");
        while (!isInterrupted) {
            command = scanner.nextLine();
            if (command.equals("exit")) {
                isInterrupted = true;
            }
            else {
                File directory = new File(command);
                if (directory.isDirectory() && directory.exists()) {
                    try {
                        mainController.parseFiles(directory);
                    }
                    catch (DirectoryEmptyException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    System.out.println("Введенная директория не существует");
                }
            }
        }
    }
}
