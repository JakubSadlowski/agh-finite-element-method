package org.example;

import static org.example.FileDataReader.readFile;

public class Main {
    public static void main(String[] args) {
        String filePath = "G:\\Programowanie_projekty\\IdeaProjects\\MES\\src\\main\\resources\\Test1_4_4.txt";
        String filePath2 = "G:\\Programowanie_projekty\\IdeaProjects\\MES\\src\\main\\resources\\Test2_4_4_MixGrid.txt";
        GlobalData globalData = readFile(filePath2);
        //printFromFile(globalData);
        SolveEquation solveEquation = new SolveEquation(globalData);
        solveEquation.calculateResults(3);
    }
}