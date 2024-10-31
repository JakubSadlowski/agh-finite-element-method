package org.example;

public class ElementUni {
    private double ksi = 1.0 / Math.sqrt(3);
    private double eta = 1.0 / Math.sqrt(3);
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final double[] shapeFunctions;
    private final int npc = 4;

    public ElementUni() {
        shapeFunctions = new double[4];
        dNdKsi = new double[npc][4];
        dNdEta = new double[npc][4];

        // Define ksi and eta for each integration point
        double[][] ksiEtaValues = {
                {-ksi, -eta},  // pc1
                { ksi, -eta},  // pc2
                { ksi,  eta},  // pc3
                {-ksi,  eta}   // pc4
        };

        // Calculate shape functions and derivatives for each point
        for (int p = 0; p < npc; p++) {
            double ksiP = ksiEtaValues[p][0];
            double etaP = ksiEtaValues[p][1];

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

    public double[] getShapeFunctions() {
        return shapeFunctions;
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
