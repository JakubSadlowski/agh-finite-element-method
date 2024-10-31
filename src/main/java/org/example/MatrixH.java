package org.example;

public class MatrixH {
    private final double[][][] J1;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final int npc;
    private double[][] dNdX;
    private double[][] dNdY;

    public MatrixH(double[][][] J1, double[][] dNdKsi, double[][] dNdEta, int npc) {
        this.J1 = J1;
        this.dNdKsi = dNdKsi;
        this.dNdEta = dNdEta;
        this.npc = npc;
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

    public void printResults() {
        calculateDndX();
        calculateDndY();

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
    }
}
