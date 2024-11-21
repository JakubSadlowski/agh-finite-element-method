package org.example;

public class Jacobian {
    private final double[][][] J;
    private final double[][][] J1;
    private final double[] detJ;
    private final int npc;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final GlobalData globalData;
    private final int currentElementID;

    public Jacobian(int integrationPoints, GlobalData globalData, ElementUni elementUni, int elementID) {
        this.npc = integrationPoints * integrationPoints;
        currentElementID = elementID;
        J = new double[npc][2][2];
        J1 = new double[npc][2][2];
        detJ = new double[npc];
        dNdKsi = elementUni.getdNdKsi();
        dNdEta = elementUni.getdNdEta();
        this.globalData = globalData;
        calculateJacobians();
    }

    public double[][] getJ(int pointIndex) {
        return J[pointIndex];
    }

    public double[][][] getJ1() {
        return J1;
    }

    public double[] getDetJ() {
        return detJ;
    }

    private void calculateJacobians() {
        Node[] nodes = globalData.getGrid().getNodes();
        Element[] elements = globalData.getGrid().getElements();
        int[] currentElement = elements[currentElementID - 1].getElements();

        for (int p = 0; p < npc; p++) {
            for (int i = 0; i < elements[currentElementID - 1].getElements().length; i++) {
                int currentNodeID = currentElement[i];
                double x = nodes[currentNodeID - 1].getX();
                double y = nodes[currentNodeID - 1].getY();

                J[p][0][0] += dNdKsi[p][i] * x;  // dXdKsi
                J[p][0][1] += dNdKsi[p][i] * y;  // dYdKsi
                J[p][1][0] += dNdEta[p][i] * x;  // dXdEta
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
                System.out.printf("%.5f", row[i]);
                if (i < row.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println("]");
        }
    }

}
