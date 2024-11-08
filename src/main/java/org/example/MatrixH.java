package org.example;

public class MatrixH {
    private final double[][][] J1;
    private final double[] detJ;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final int npc;
    private double[][] dNdX;
    private double[][] dNdY;
    private double[][][] Hpc;
    private double[][] H;
    private final double[][] weights;
    private final double specificHeat = 30;

    public MatrixH(double[][][] J1, double[] detJ, double[][] dNdKsi, double[][] dNdEta, int integrationPoints) {
        GaussQuadrature2D gaussQuadratureData = new GaussQuadrature2D(integrationPoints);
        this.J1 = J1;
        this.detJ = detJ;
        this.weights = gaussQuadratureData.getWeights();
        this.dNdKsi = dNdKsi;
        this.dNdEta = dNdEta;
        this.npc = integrationPoints * integrationPoints;

        calculateDndX();
        calculateDndY();
        calculateMatrixHpc();
    }

    private void calculateDndX() {
        dNdX = new double[npc][dNdKsi[0].length];
        for (int p = 0; p < npc; p++) {
            for (int j = 0; j < dNdKsi[0].length; j++) {
                dNdX[p][j] = J1[p][0][0] * dNdKsi[p][j] + J1[p][0][1] * dNdEta[p][j];
            }
        }
    }

    private void calculateDndY() {
        dNdY = new double[npc][dNdKsi[0].length];
        for (int p = 0; p < npc; p++) {
            for (int j = 0; j < dNdKsi[0].length; j++) {
                dNdY[p][j] = J1[p][1][0] * dNdKsi[p][j] + J1[p][1][1] * dNdEta[p][j];
            }
        }
    }

    private void calculateMatrixHpc() {
        int size = dNdX[0].length;
        Hpc = new double[npc][size][size];
        H = new double[size][size];

        for (int p = 0; p < npc; p++) {
            int i = p / (int) Math.sqrt(npc);
            int j = p % (int) Math.sqrt(npc);
            for (int m = 0; m < size; m++) {
                for (int n = 0; n < size; n++) {
                    Hpc[p][m][n] = (dNdX[p][m] * dNdX[p][n] + dNdY[p][m] * dNdY[p][n])
                            * specificHeat * detJ[p] * weights[i][j];
                    H[m][n] += Hpc[p][m][n];
                }
            }
        }
    }

    public void printMatrixHpc() {
        for (int p = 0; p < npc; p++) {
            System.out.println("Matrix Hpc" + (p + 1) + ":");
            for (double[] row : Hpc[p]) {
                System.out.print("|");
                for (double value : row) {
                    System.out.printf(" %5.3f ", value);
                }
                System.out.println("|");
            }
            System.out.println();
        }
    }

    public void printMatrixH() {
        System.out.println("Matrix H:");
        for (double[] row : H) {
            System.out.print("|");
            for (double value : row) {
                System.out.printf(" %5.3f ", value);
            }
            System.out.println("|");
        }
    }

    public void printResults() {
        System.out.println("------------------------------------------------------------");
        System.out.println("| pc | dN1/dX   | dN2/dX   | dN3/dX   | dN4/dX   |");
        System.out.println("------------------------------------------------------------");

        for (int p = 0; p < npc; p++) {
            System.out.print("| " + (p + 1) + "  ");
            for (int j = 0; j < dNdX[0].length; j++) {
                System.out.printf("| %-8.5f ", dNdX[p][j]);
            }
            System.out.println("|");
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("| pc | dN1/dY   | dN2/dY   | dN3/dY   | dN4/dY   |");
        System.out.println("------------------------------------------------------------");

        for (int p = 0; p < npc; p++) {
            System.out.print("| " + (p + 1) + "  ");
            for (int j = 0; j < dNdY[0].length; j++) {
                System.out.printf("| %-8.5f ", dNdY[p][j]);
            }
            System.out.println("|");
        }
        System.out.println("------------------------------------------------------------");

        printMatrixHpc();
        printMatrixH();
    }
}
