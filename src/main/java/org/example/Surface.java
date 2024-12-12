package org.example;

public class Surface {
    private final double[][] N;
    private final int npc;

    public Surface(int integrationPoints) {
        this.npc = integrationPoints;
        this.N = new double[npc][4];
        calculateShapeFunctions();
    }

    private void calculateShapeFunctions() {
        GaussQuadratureData gaussData = new GaussQuadratureData(npc);
        double[] ksi = gaussData.getNodes();

        for (int i = 0; i < npc; i++) {
            // Shape functions for 1D element (edge of 2D element)
            N[i][0] = 0.5 * (1 - ksi[i]);  // N1
            N[i][1] = 0.5 * (1 + ksi[i]);  // N2
            N[i][2] = 0;                    // N3
            N[i][3] = 0;                    // N4
        }
    }

    public double[][] getN() {
        return N;
    }

    public void printShapeFunctions() {
        System.out.println("Surface Shape Functions:");
        for (int i = 0; i < npc; i++) {
            System.out.printf("Point %d: ", i + 1);
            for (int j = 0; j < 4; j++) {
                System.out.printf("N%d = %.4f ", j + 1, N[i][j]);
            }
            System.out.println();
        }
    }
}
