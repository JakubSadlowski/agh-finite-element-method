package org.example;

public class ElementUni {
    private final double[][] ksiEtaValues;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final double[] shapeFunctions;
    private final int npc;

    public ElementUni(int integrationPoints) {
        this.npc = integrationPoints * integrationPoints;
        GaussQuadrature2D gaussQuadratureData = new GaussQuadrature2D(integrationPoints);
        this.ksiEtaValues = gaussQuadratureData.getNodes();
        shapeFunctions = new double[4];
        dNdKsi = new double[npc][4];
        dNdEta = new double[npc][4];

        calculateShapeFunctionsAndDerivatives();
    }

    private void calculateShapeFunctionsAndDerivatives() {
        for (int p = 0; p < npc; p++) {
            double ksi = ksiEtaValues[p][0];
            double eta = ksiEtaValues[p][1];

            shapeFunctions[0] = 0.25 * (1 - ksi) * (1 - eta);
            shapeFunctions[1] = 0.25 * (1 + ksi) * (1 - eta);
            shapeFunctions[2] = 0.25 * (1 + ksi) * (1 + eta);
            shapeFunctions[3] = 0.25 * (1 - ksi) * (1 + eta);

            dNdKsi[p][0] = -0.25 * (1 - eta);
            dNdKsi[p][1] = 0.25 * (1 - eta);
            dNdKsi[p][2] = 0.25 * (1 + eta);
            dNdKsi[p][3] = -0.25 * (1 + eta);

            dNdEta[p][0] = -0.25 * (1 - ksi);
            dNdEta[p][1] = -0.25 * (1 + ksi);
            dNdEta[p][2] = 0.25 * (1 + ksi);
            dNdEta[p][3] = 0.25 * (1 - ksi);
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
