package org.example;

public class MatrixC {
    private final double[][][] J1;
    private final double[] detJ;
    private final int npc;
    private final double[] gaussWeights;
    private final int integrationPoints;
    private double[][] C;
    private final double specificHeat;
    private final double density;
    private final ElementUni elementUni;

    public MatrixC(int integrationPoints, GlobalData globalData, ElementUni elementUni, Jacobian jacobian) {
        this.specificHeat = globalData.getSpecificHeat();
        this.density = globalData.getDensity();
        this.elementUni = elementUni;
        this.J1 = jacobian.getJ1();
        this.detJ = jacobian.getDetJ();
        this.npc = integrationPoints * integrationPoints;
        this.integrationPoints = integrationPoints;

        GaussQuadratureData gaussQuadratureData = new GaussQuadratureData(integrationPoints);
        this.gaussWeights = gaussQuadratureData.getWeights();

        C = new double[4][4];
        calculateMatrixC();
    }

    private void calculateMatrixC() {
        double[] ksiEtaValues = elementUni.getKsiEtaValues();

        int index = 0;
        for (int i = 0; i < integrationPoints; i++) {
            for (int j = 0; j < integrationPoints; j++) {
                // Get ksi and eta values for current integration point
                double ksi = ksiEtaValues[i];
                double eta = ksiEtaValues[j];

                // Calculate shape functions for this point
                double[] N = elementUni.calculateShapeFunctions(ksi, eta);

                // Calculate weight for this point
                double weight = gaussWeights[i] * gaussWeights[j] * detJ[index];

                // Calculate contribution to C matrix
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        C[k][l] += N[k] * N[l] * specificHeat * density * weight;
                    }
                }
                index++;
            }
        }
    }

    public double[][] getC() {
        return C;
    }

    public void printMatrixC() {
        System.out.println("Local Matrix C:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%8.3f ", C[i][j]);
            }
            System.out.println();
        }
    }
}