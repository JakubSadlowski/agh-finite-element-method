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
}
