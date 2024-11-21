package org.example;

import java.util.function.Function;

import static org.example.FileDataReader.readFile;
import static org.example.FilePrinter.printFromFile;
import static org.example.IntegratingFunctions.integrate1D;
import static org.example.IntegratingFunctions.integrate2D;

public class Main {
    public static void main(String[] args) {
        /*String filePath = "G:\\Programowanie_projekty\\IdeaProjects\\MES\\src\\main\\resources\\Test1_4_4.txt";
        GlobalData globalData = readFile(filePath);
        printFromFile(globalData);*/

        /*Function<Double, Double> f1D = x -> 5 * x * x + 3 * x + 6;
        System.out.println("\n\n1D Integral (1-point): " + integrate1D(f1D, 1));
        System.out.println("1D Integral (2-points): " + integrate1D(f1D, 2));
        System.out.println("1D Integral (3-point): " + integrate1D(f1D, 3));

        Function<double[], Double> f2D = xy -> 5 * Math.pow(xy[0], 2) * Math.pow(xy[1], 2) + 3 * xy[0] * xy[1] + 6;
        System.out.println("2D Integral (1-point): " + integrate2D(f2D, 1));
        System.out.println("2D Integral (2-point): " + integrate2D(f2D, 2));
        System.out.println("2D Integral (3-point): " + integrate2D(f2D, 3));*/

        /*Node[] nodes = new Node[4];
        nodes[0] = new Node(0.0, 0.0, 1);
        nodes[1] = new Node(0.025, 0.0, 2);
        nodes[2] = new Node(0.025, 0.025, 3);
        nodes[3] = new Node(0.0, 0.025, 4);*/

        /*Node[] nodes = new Node[4];
        nodes[0] = new Node(0.01, -0.01, 1);
        nodes[1] = new Node(0.025, 0.0, 2);
        nodes[2] = new Node(0.025, 0.025, 3);
        nodes[3] = new Node(0.0, 0.025, 4);

        ElementUni elementUni = new ElementUni(3);
        double[][] dNdKsi = elementUni.getdNdKsi();
        double[][] dNdEta = elementUni.getdNdEta();
        elementUni.printResults();

        Jacobian jacobian = new Jacobian(3);
        jacobian.calculateJacobians(nodes, dNdKsi, dNdEta);
        jacobian.printJacobians();

        MatrixH matrixH = new MatrixH(jacobian.getJ1(), jacobian.getDetJ(), dNdKsi, dNdEta, 3);
        matrixH.printResults();*/
    }
}