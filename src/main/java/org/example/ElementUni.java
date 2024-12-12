package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ElementUni {
    private final double[] ksiEtaValues;
    private final double[][] dNdKsi;
    private final double[][] dNdEta;
    private final int npc;
    private final int numPoints;
    private final List<Function<Double, double[]>> surfaceTransformations;
    private final Surface surface;

    public ElementUni(int integrationPoints) {
        this.npc = integrationPoints * integrationPoints;
        this.numPoints = integrationPoints;
        GaussQuadratureData gaussQuadratureData = new GaussQuadratureData(integrationPoints);
        this.ksiEtaValues = gaussQuadratureData.getNodes();
        dNdKsi = new double[npc][4];
        dNdEta = new double[npc][4];
        surface = new Surface(integrationPoints);
        surfaceTransformations = initializeSurfaceTransformations();
        calculateDerivatives();
        calculateSurfaceValues();
    }

    private void calculateDerivatives() {
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

    private List<Function<Double, double[]>> initializeSurfaceTransformations() {
        List<Function<Double, double[]>> transformations = new ArrayList<>();
        // Transformation for the "down" surface
        transformations.add(point -> new double[]{point, -1.0});
        // Transformation for the "right" surface
        transformations.add(point -> new double[]{1.0, point});
        // Transformation for the "left" surface
        transformations.add(point -> new double[]{-1.0, point});
        // Transformation for the "up" surface
        transformations.add(point -> new double[]{point, 1.0});
        return transformations;
    }

    public void calculateSurfaceValues() {
        for (int i = 0; i < surface.N[0].length; i++) {
            Function<Double, double[]> transformation = surfaceTransformations.get(i);
            for (int j = 0; j < numPoints; j++) {
                double point = ksiEtaValues[j];
                double[] transformedPoint = transformation.apply(point);

                double xi = transformedPoint[0];
                double eta = transformedPoint[1];

                //double[] surfaceValues = new double[4];
                surface.N[i][0] = 0.25 * (1 - xi) * (1 - eta);
                surface.N[i][1] = 0.25 * (1 + xi) * (1 - eta);
                surface.N[i][2] = 0.25 * (1 + xi) * (1 + eta);
                surface.N[i][3] = 0.25 * (1 - xi) * (1 + eta);

                /*// Output results for debugging
                System.out.printf("Surface %d, Point %d -> xi: %.2f, eta: %.2f, Values: %s%n",
                        i + 1, j + 1, xi, eta, java.util.Arrays.toString(surfaceValues));*/
            }
        }
    }

    public double[][] getdNdEta() {
        return dNdEta;
    }

    public double[][] getdNdKsi() {
        return dNdKsi;
    }

    public Surface getSurface() {
        return surface;
    }

    public List<Function<Double, double[]>> getSurfaceTransformations() {
        return surfaceTransformations;
    }

    public double[] getKsiEtaValues() {
        return ksiEtaValues;
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
