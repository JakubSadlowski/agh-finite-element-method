package org.example;

public class Jacobian {
    private double[][][] J;
    private double[][][] J1;
    private double[] detJ;
    private final int npc;

    public Jacobian(int npc) {
        this.npc = npc;
        J = new double[npc][2][2];
        J1 = new double[npc][2][2];
        detJ = new double[npc];
    }

    public double[][] getJ(int pointIndex) {
        return J[pointIndex];
    }

    public double[][] getJ1(int pointIndex) {
        return J1[pointIndex];
    }

    public double getDetJ(int pointIndex) {
        return detJ[pointIndex];
    }

    public void calculateJacobians(Node[] nodes, double[][] dNdKsi, double[][] dNdEta) {
        for (int p = 0; p < npc; p++) {
            J[p][0][0] = J[p][0][1] = J[p][1][0] = J[p][1][1] = 0.0;

            for (int i = 0; i < nodes.length; i++) {
                double x = nodes[i].getX();
                double y = nodes[i].getY();

                J[p][0][0] += dNdKsi[p][i] * x;  // dXdKsi
                J[p][0][1] += dNdEta[p][i] * x;  // dXdEta
                J[p][1][0] += dNdKsi[p][i] * y;  // dYdKsi
                J[p][1][1] += dNdEta[p][i] * y;  // dYdEta
            }

            detJ[p] = calculateDetJ(J[p]);
            J1[p] = invertJacobian(J[p], detJ[p]);
        }
    }

    private double calculateDetJ(double[][] jacobianMatrix) {
        return jacobianMatrix[0][0] * jacobianMatrix[1][1] - jacobianMatrix[0][1] * jacobianMatrix[1][0];
    }

    private double[][] invertJacobian(double[][] jacobianMatrix, double detJ) {
        double[][] inverse = new double[2][2];

        inverse[0][0] = jacobianMatrix[1][1] / detJ;
        inverse[0][1] = -jacobianMatrix[0][1] / detJ;
        inverse[1][0] = -jacobianMatrix[1][0] / detJ;
        inverse[1][1] = jacobianMatrix[0][0] / detJ;

        return inverse;
    }

    public void printJacobians() {
        for (int p = 0; p < npc; p++) {
            System.out.println("Integration Point " + (p + 1) + ":");
            System.out.println("Jacobian Matrix J:");
            printMatrix(J[p]);

            System.out.println("Determinant detJ: " + detJ[p]);

            System.out.println("Inverse Jacobian Matrix J1:");
            printMatrix(J1[p]);
            System.out.println();
        }
    }

    private void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            System.out.print("[");
            for (int i = 0; i < row.length; i++) {
                System.out.print(String.format("%.5f", row[i]));
                if (i < row.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println("]");
        }
    }

}
