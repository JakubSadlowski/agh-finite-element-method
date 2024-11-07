package org.example;

public class ElementUni {
    private double[] ksi;
    private double[] eta;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final double[] shapeFunctions;
    private final int npc;

    public ElementUni(int integrationPoints) {
        this.npc = integrationPoints * integrationPoints;
        GaussQuadratureData gaussQuadratureData = new GaussQuadratureData(integrationPoints);
        this.ksi = gaussQuadratureData.getNodes();
        this.eta = gaussQuadratureData.getNodes();
        shapeFunctions = new double[4];
        dNdKsi = new double[npc][4];
        dNdEta = new double[npc][4];

        calculateShapeFunctionsAndDerivatives();
    }

    private void calculateShapeFunctionsAndDerivatives() {
        for (int p = 0; p < npc; p++) {
            double ksiP = ksi[p / ksi.length];
            double etaP = eta[p % eta.length];

            shapeFunctions[0] = 0.25 * (1 - ksiP) * (1 - etaP);
            shapeFunctions[1] = 0.25 * (1 + ksiP) * (1 - etaP);
            shapeFunctions[2] = 0.25 * (1 + ksiP) * (1 + etaP);
            shapeFunctions[3] = 0.25 * (1 - ksiP) * (1 + etaP);

            dNdKsi[p][0] = -0.25 * (1 - etaP);
            dNdKsi[p][1] = 0.25 * (1 - etaP);
            dNdKsi[p][2] = 0.25 * (1 + etaP);
            dNdKsi[p][3] = -0.25 * (1 + etaP);

            dNdEta[p][0] = -0.25 * (1 - ksiP);
            dNdEta[p][1] = -0.25 * (1 + ksiP);
            dNdEta[p][2] = 0.25 * (1 + ksiP);
            dNdEta[p][3] = 0.25 * (1 - ksiP);
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
