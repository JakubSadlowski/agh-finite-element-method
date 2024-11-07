package org.example;

import java.util.function.Function;

public class IntegratingFunctions {

    // Method to integrate a 1D function using Gaussian quadrature
    private static double gauss1d(Function<Double, Double> f, int points) {
        GaussQuadratureData gaussData = new GaussQuadratureData(points);
        double[] nodes = gaussData.getNodes();
        double[] weights = gaussData.getWeights();
        double result = 0.0;

        // Loop through each node and apply the weights
        for (int i = 0; i < points; i++) {
            result += weights[i] * f.apply(nodes[i]);
        }
        return result;
    }

    // Method to integrate a 2D function using Gaussian quadrature
    private static double gauss2d(Function<double[], Double> f, int points) {
        double[][] nodes = GaussQuadratureData.get2DNodes(points);
        double[][] weights = GaussQuadratureData.get2DWeights(points);
        double result = 0.0;

        // Loop through each pair of 2D nodes and apply the corresponding weights
        for (int i = 0; i < points; i++) {
            for (int j = 0; j < points; j++) {
                double[] point = {nodes[i][0], nodes[j][1]}; // Current integration point
                result += weights[i][j] * f.apply(point);   // Apply the weights and evaluate the function
            }
        }
        return result;
    }

    // Public method for 1D integration
    public static double integrate1D(Function<Double, Double> f, int points) {
        return gauss1d(f, points);
    }

    // Public method for 2D integration
    public static double integrate2D(Function<double[], Double> f, int points) {
        return gauss2d(f, points);
    }
}
