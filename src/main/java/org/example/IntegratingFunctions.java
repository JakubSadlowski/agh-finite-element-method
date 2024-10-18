package org.example;

import java.util.function.Function;

public class IntegratingFunctions {

    private static double gauss1d1p(Function<Double, Double> f){
        double node = 0.0;
        double weight = 2.0;
        double result = 0.0;
        for (int i = 0; i < 1; i++) {
            result += weight * f.apply(node);
        }
        return result;
    }

    private static double gauss1d2p(Function<Double, Double> f){
        double[] nodes = {-1.0 / Math.sqrt(3), 1.0 / Math.sqrt(3)};
        double[] weights = {1, 1};
        double result = 0.0;
        for (int i = 0; i < 2; i++) {
            result += weights[i] * f.apply(nodes[i]);
        }
        return result;
    }

    private static double gauss1d3p(Function<Double, Double> f){
        double[] nodes = {-Math.sqrt(3.0 / 5), 0, Math.sqrt(3.0 / 5)};
        double[] weights = {5.0 / 9, 8.0 / 9, 5.0 / 9};
        double result = 0.0;
        for (int i = 0; i < 3; i++) {
            result += weights[i] * f.apply(nodes[i]);
        }
        return result;
    }

    private static double gauss2d1p(Function<double[], Double> f){
        double[][] nodes = {{0.0, 0.0}};
        double[] weights = {4.0};
        double result = 0.0;
        for (int i = 0; i < 1; i++) {
            result += weights[i] * f.apply(nodes[i]);
        }
        return result;
    }

    public static double gauss2d2p(Function<double[], Double> f) {
        double[][] nodes = {
                {-1.0 / Math.sqrt(3), -1.0 / Math.sqrt(3)},
                {1.0 / Math.sqrt(3), -1.0 / Math.sqrt(3)},
                {-1.0 / Math.sqrt(3), 1.0 / Math.sqrt(3)},
                {1.0 / Math.sqrt(3), 1.0 / Math.sqrt(3)}
        };
        double[] weights = {1, 1, 1, 1};
        double result = 0.0;
        for (int i = 0; i < 4; i++) {
            result += weights[i] * f.apply(nodes[i]);
        }
        return result;
    }

    public static double gauss2d3p(Function<double[], Double> f) {
        double[] weights = {5.0 / 9, 8.0 / 9, 5.0 / 9};
        double[] nodes = {-Math.sqrt(3.0 / 5), 0, Math.sqrt(3.0 / 5)};
        double result = 0.0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                double[] point = {nodes[i], nodes[j]};
                result += weights[i] * weights[j] * f.apply(point);
            }
        }
        return result;
    }

    public static double integrate1D(Function<Double, Double> f, int points) {
        switch (points) {
            case 1:
                return gauss1d1p(f);
            case 2:
                return gauss1d2p(f);
            case 3:
                return gauss1d3p(f);
            default:
                throw new IllegalArgumentException("Unsupported number of points");
        }
    }

    public static double integrate2D(Function<double[], Double> f, int points) {
        switch (points) {
            case 1:
                return gauss2d1p(f);
            case 2:
                return gauss2d2p(f);
            case 3:
                return gauss2d3p(f);
            default:
                throw new IllegalArgumentException("Unsupported number of points");
        }
    }
}
