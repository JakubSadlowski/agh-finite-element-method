package org.example;

public class ElementUni {
    private double ksi = 0.577;
    private double eta = 0.577;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final double[] shapeFunctions;
    private final int npc = 4;

    public ElementUni() {
        shapeFunctions = new double[4];
        dNdKsi = new double[npc][4];
        dNdEta = new double[npc][4];

        // Calculate shape functions
        shapeFunctions[0] = 0.25 * (1 - ksi) * (1 - eta);
        shapeFunctions[1] = 0.25 * (1 + ksi) * (1 - eta);
        shapeFunctions[2] = 0.25 * (1 + ksi) * (1 + eta);
        shapeFunctions[3] = 0.25 * (1 - ksi) * (1 + eta);

        // Calculate dNdKsi
        for (int i = 0; i < npc; i++) {
            dNdKsi[i][0] = -0.25 * (1 - eta);
            dNdKsi[i][1] = 0.25 * (1 - eta);
            dNdKsi[i][2] = 0.25 * (1 + eta);
            dNdKsi[i][3] = -0.25 * (1 + eta);
        }

        // Calculate dNdEta
        for (int i = 0; i < npc; i++) {
            dNdEta[i][0] = -0.25 * (1 - ksi);
            dNdEta[i][1] = -0.25 * (1 + ksi);
            dNdEta[i][2] = 0.25 * (1 + ksi);
            dNdEta[i][3] = 0.25 * (1 - ksi);
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

}
