package org.example;

import static org.example.FileDataReader.readFile;
import static org.example.FilePrinter.printFromFile;

public class Main {
    public static void main(String[] args) {
        String filePath = "G:\\Programowanie_projekty\\IdeaProjects\\MES\\src\\main\\resources\\Test1_4_4.txt";
        GlobalData globalData = readFile(filePath);
        printFromFile(globalData);
    }
}