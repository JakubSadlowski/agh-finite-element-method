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
    private final double[] weights;
    private final double specificHeat = 30;

    public MatrixH(double[][][] J1, double[] detJ, double[] weights, double[][] dNdKsi, double[][] dNdEta, int integrationPoints) {
        this.J1 = J1;
        this.detJ = detJ;
        this.weights = weights;
        this.dNdKsi = dNdKsi;
        this.dNdEta = dNdEta;
        this.npc = integrationPoints * integrationPoints;

        calculateDndX();
        calculateDndY();
        calculateMatrixHpc();
        calculateMatrixH();
    }

    private void calculateDndX() {
        dNdX = new double[npc][dNdKsi[0].length];
        for(int p = 0; p < npc; p++) {
            for(int j = 0; j < dNdKsi[0].length; j++) {
                dNdX[p][j] = J1[p][0][0] * dNdKsi[p][j] + J1[p][0][1] * dNdEta[p][j];
            }
        }
    }

    private void calculateDndY() {
        dNdY = new double[npc][dNdKsi[0].length];
        for(int p = 0; p < npc; p++) {
            for(int j = 0; j < dNdKsi[0].length; j++) {
                dNdY[p][j] = J1[p][1][0] * dNdKsi[p][j] + J1[p][1][1] * dNdEta[p][j];
            }
        }
    }

    private void calculateMatrixHpc() {
        int size = dNdX[0].length;
        Hpc = new double[npc][size][size];

        for(int p = 0; p < npc; p++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Hpc[p][i][j] += dNdX[p][i] * dNdX[p][j] + dNdY[p][i] * dNdY[p][j];
                    Hpc[p][i][j] *= specificHeat;
                    Hpc[p][i][j] *= detJ[p];
                }
            }
        }
    }

    private void calculateMatrixH() {
        int size = dNdX[0].length;
        H = new double[size][size];

        for(int p = 0; p < npc; p++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    H[i][j] += Hpc[p][i][j] * weights[p];
                }
            }
        }
    }

    public void printMatrixHpc() {
        for (int p = 0; p < npc; p++) {
            System.out.println("Matrix Hpc" + (p + 1));
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
