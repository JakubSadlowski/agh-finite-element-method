package org.example;

public class ElementUni {
    private final double[] ksiEtaValues;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final int npc;
    private final int numPoints;

    public ElementUni(int integrationPoints) {
        this.npc = integrationPoints * integrationPoints;
        this.numPoints = integrationPoints;
        GaussQuadratureData gaussQuadratureData = new GaussQuadratureData(integrationPoints);
        this.ksiEtaValues = gaussQuadratureData.getNodes();
        dNdKsi = new double[npc][4];
        dNdEta = new double[npc][4];
        calculateShapeFunctionsAndDerivatives();
    }

    private void calculateShapeFunctionsAndDerivatives() {
        for (int i = 0; i < numPoints; i++) {
            double eta = ksiEtaValues[i];
            for (int j = 0; j < numPoints; j++) {
                double ksi = ksiEtaValues[j];

                dNdKsi[i * numPoints + j][0] = -0.25 * (1 - eta);
                dNdKsi[i * numPoints + j][1] = 0.25 * (1 - eta);
                dNdKsi[i * numPoints + j][2] = 0.25 * (1 + eta);
                dNdKsi[i * numPoints + j][3] = -0.25 * (1 + eta);

                dNdEta[i * numPoints + j][0] = -0.25 * (1 - ksi);
                dNdEta[i * numPoints + j][1] = -0.25 * (1 + ksi);
                dNdEta[i * numPoints + j][2] = 0.25 * (1 + ksi);
                dNdEta[i * numPoints + j][3] = 0.25 * (1 - ksi);
            }
        }
    }

    public double[][] getdNdEta() {
        return dNdEta;
    }

    public double[][] getdNdKsi() {
        return dNdKsi;
    }

    public void printResults() {
        System.out.println("------------------------------------------------------------");
        System.out.println("| pc | dN1/dKsi   | dN2/dKsi   | dN3/dKsi   | dN4/dKsi   |");
        System.out.println("------------------------------------------------------------");

        for (int p = 0; p < npc; p++) {
            System.out.print("| " + (p + 1) + "  ");
            for (int j = 0; j < dNdKsi[0].length; j++) {
                System.out.printf("| %-8.5f ", dNdKsi[p][j]);
            }
            System.out.println("|");
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("| pc | dN1/dEta   | dN2/dEta   | dN3/dEta   | dN4/dEta   |");
        System.out.println("------------------------------------------------------------");

        for (int p = 0; p < npc; p++) {
            System.out.print("| " + (p + 1) + "  ");
            for (int j = 0; j < dNdEta[0].length; j++) {
                System.out.printf("| %-8.5f ", dNdEta[p][j]);
            }
            System.out.println("|");
        }
        System.out.println("------------------------------------------------------------");
    }
}
