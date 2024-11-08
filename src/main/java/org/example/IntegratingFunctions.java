package org.example;

import java.util.function.Function;

public class IntegratingFunctions {

    private static double gauss1d(Function<Double, Double> f, int points) {
        GaussQuadratureData gaussData = new GaussQuadratureData(points);
        double[] nodes = gaussData.getNodes();
        double[] weights = gaussData.getWeights();
        double result = 0.0;

        for (int i = 0; i < points; i++) {
            result += weights[i] * f.apply(nodes[i]);
        }
        return result;
    }

    private static double gauss2d(Function<double[], Double> f, int points) {
        GaussQuadratureData gaussData = new GaussQuadratureData(points);
        double[] nodes = gaussData.getNodes();
        double[] weights = gaussData.getWeights();
        double result = 0.0;

        for (int i = 0; i < points; i++) {
            for (int j = 0; j < points; j++) {
                double[] point = {nodes[i], nodes[j]};
                result += weights[i] * weights[j] * f.apply(point);
            }
        }
        return result;
    }

    public static double integrate1D(Function<Double, Double> f, int points) {
        return gauss1d(f, points);
    }

    public static double integrate2D(Function<double[], Double> f, int points) {
        return gauss2d(f, points);
    }
}
