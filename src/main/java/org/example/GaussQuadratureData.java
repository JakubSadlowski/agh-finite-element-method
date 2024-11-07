package org.example;

public class GaussQuadratureData {
    private final double[] nodes;
    private final double[] weights;

    public GaussQuadratureData(int points) {
        if (points == 1) {
            nodes = new double[]{0.0};
            weights = new double[]{2.0};
        } else if (points == 2) {
            nodes = new double[]{-1.0 / Math.sqrt(3), 1.0 / Math.sqrt(3)};
            weights = new double[]{1.0, 1.0};
        } else if (points == 3) {
            nodes = new double[]{-Math.sqrt(3.0 / 5), 0.0, Math.sqrt(3.0 / 5)};
            weights = new double[]{5.0 / 9, 8.0 / 9, 5.0 / 9};
        } else {
            throw new IllegalArgumentException("Unsupported number of integration points");
        }
    }

    public double[] getNodes() {
        return nodes;
    }

    public double[] getWeights() {
        return weights;
    }

    public static double[][] get2DNodes(int points) {
        GaussQuadratureData gaussData = new GaussQuadratureData(points);
        double[] nodes1D = gaussData.getNodes();
        double[][] nodes2D = new double[points * points][2]; // 2D array for nodes
        int index = 0;

        for (double ksi : nodes1D) {
            for (double eta : nodes1D) {
                nodes2D[index][0] = ksi; // Ksi direction
                nodes2D[index][1] = eta; // Eta direction
                index++;
            }
        }
        return nodes2D;
    }

    public static double[][] get2DWeights(int points) {
        GaussQuadratureData gaussData = new GaussQuadratureData(points);
        double[] weights1D = gaussData.getWeights();
        double[][] weights2D = new double[points][points];

        for (int i = 0; i < points; i++) {
            for (int j = 0; j < points; j++) {
                weights2D[i][j] = weights1D[i] * weights1D[j];
            }
        }
        return weights2D;
    }
}
