package org.example;

public class GaussQuadratureData {
    public static double[] get1DNodes(int points) {
        switch (points) {
            case 1:
                return new double[]{0.0};
            case 2:
                return new double[]{-1.0 / Math.sqrt(3), 1.0 / Math.sqrt(3)};
            case 3:
                return new double[]{-Math.sqrt(3.0 / 5), 0, Math.sqrt(3.0 / 5)};
            default:
                throw new IllegalArgumentException("Unsupported number of points for 1D integration");
        }
    }

    public static double[] get1DWeights(int points) {
        switch (points) {
            case 1:
                return new double[]{2.0};
            case 2:
                return new double[]{1.0, 1.0};
            case 3:
                return new double[]{5.0 / 9, 8.0 / 9, 5.0 / 9};
            default:
                throw new IllegalArgumentException("Unsupported number of points for 1D integration");
        }
    }

    public static double[][] get2DNodes(int points) {
        switch (points) {
            case 1:
                return new double[][]{{0.0, 0.0}};
            case 2:
                double value = 1.0 / Math.sqrt(3);
                return new double[][]{
                        {-value, -value}, {value, -value},
                        {-value, value}, {value, value}
                };
            case 3:
                double sqrt_3_5 = Math.sqrt(3.0 / 5);
                return new double[][]{
                        {-sqrt_3_5, -sqrt_3_5}, {0, -sqrt_3_5}, {sqrt_3_5, -sqrt_3_5},
                        {-sqrt_3_5, 0}, {0, 0}, {sqrt_3_5, 0},
                        {-sqrt_3_5, sqrt_3_5}, {0, sqrt_3_5}, {sqrt_3_5, sqrt_3_5}
                };
            default:
                throw new IllegalArgumentException("Unsupported number of points for 2D integration");
        }
    }

    public static double[] get2DWeights(int points) {
        switch (points) {
            case 1:
                return new double[]{4.0};
            case 2:
                return new double[]{1.0, 1.0, 1.0, 1.0};
            case 3:
                return new double[]{5.0 / 9, 8.0 / 9, 5.0 / 9};
            default:
                throw new IllegalArgumentException("Unsupported number of points for 2D integration");
        }
    }
}
