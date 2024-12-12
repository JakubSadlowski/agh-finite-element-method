package org.example;

public class Surface {
    private final double[][] N;
    private final int numPoints;

    public Surface(int numPoints) {
        this.numPoints = numPoints;
        this.N = new double[4][4]; // 4 surfaces x 4 nodes
    }

    public void calculateSurfaceShapeFunctions(double ksi) {
        // Shape functions for 1D element (on surface)
        N[0][0] = 0.5 * (1 - ksi); // N1
        N[0][1] = 0.5 * (1 + ksi); // N2
        N[0][2] = 0;                // N3
        N[0][3] = 0;                // N4

        N[1][0] = 0;                // N1
        N[1][1] = 0.5 * (1 - ksi); // N2
        N[1][2] = 0.5 * (1 + ksi); // N3
        N[1][3] = 0;                // N4

        N[2][0] = 0;                // N1
        N[2][1] = 0;                // N2
        N[2][2] = 0.5 * (1 - ksi); // N3
        N[2][3] = 0.5 * (1 + ksi); // N4

        N[3][0] = 0.5 * (1 + ksi); // N1
        N[3][1] = 0;                // N2
        N[3][2] = 0;                // N3
        N[3][3] = 0.5 * (1 - ksi); // N4
    }

    public double[][] getN() {
        return N;
    }
}
