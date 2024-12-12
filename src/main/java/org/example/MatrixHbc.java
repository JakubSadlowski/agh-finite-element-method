package org.example;

public class MatrixHbc {
    private final double[][] Hbc;
    private final int numPoints;
    private final GlobalData globalData;
    private final Element element;
    private final Grid grid;
    private final ElementUni elementUni;

    public MatrixHbc(GlobalData globalData, Element element, int numPoints) {
        this.numPoints = numPoints;
        this.globalData = globalData;
        this.element = element;
        this.grid = globalData.getGrid();
        this.Hbc = new double[4][4];
        this.elementUni = new ElementUni(numPoints);

        calculateMatrixHbc();
    }

    private void calculateMatrixHbc() {
        double[] weights = new GaussQuadratureData(numPoints).getWeights();
        double[] ksiEtaValues = elementUni.getKsiEtaValues();

        // Iterate through each surface of the element (4 surfaces for a 2D element)
        for (int surfaceIndex = 0; surfaceIndex < 4; surfaceIndex++) {
            Node[] surfaceNodes = getNodesForSurface(surfaceIndex);

            // Only calculate if both nodes on the surface have boundary condition
            if (surfaceNodes[0].isBC() && surfaceNodes[1].isBC()) {
                double detJ = calculateDetJ(surfaceNodes[0], surfaceNodes[1]);

                // Iterate over integration points on the surface
                for (int i = 0; i < numPoints; i++) {
                    double point = ksiEtaValues[i];
                    double[] transformedPoint = elementUni.getSurfaceTransformations().get(surfaceIndex).apply(point);

                    // xi and eta for this integration point
                    double xi = transformedPoint[0];
                    double eta = transformedPoint[1];

                    // Calculate shape functions for the current surface
                    double[] N = elementUni.getSurface().getN()[surfaceIndex];

                    // Update the Hbc matrix
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            Hbc[j][k] += globalData.getAlpha() * N[j] * N[k] * weights[i] * detJ;
                        }
                    }
                }
            }
        }
    }

    private Node[] getNodesForSurface(int surfaceIndex) {
        int[] nodeIndices = element.getElements();
        Node[] nodes = grid.getNodes();
        Node[] surfaceNodes = new Node[2];

        switch (surfaceIndex) {
            case 0: // bottom edge
                surfaceNodes[0] = nodes[nodeIndices[0] - 1];
                surfaceNodes[1] = nodes[nodeIndices[1] - 1];
                break;
            case 1: // right edge
                surfaceNodes[0] = nodes[nodeIndices[1] - 1];
                surfaceNodes[1] = nodes[nodeIndices[2] - 1];
                break;
            case 2: // top edge
                surfaceNodes[0] = nodes[nodeIndices[2] - 1];
                surfaceNodes[1] = nodes[nodeIndices[3] - 1];
                break;
            case 3: // left edge
                surfaceNodes[0] = nodes[nodeIndices[3] - 1];
                surfaceNodes[1] = nodes[nodeIndices[0] - 1];
                break;
        }
        return surfaceNodes;
    }

    private double calculateDetJ(Node node1, Node node2) {
        double dx = node2.getX() - node1.getX();
        double dy = node2.getY() - node1.getY();
        return Math.sqrt(dx * dx + dy * dy) / 2.0;
    }

    public double[][] getHbc() {
        return Hbc;
    }

    public void printResults() {
        System.out.println("Matrix Hbc:");
        for (double[] row : Hbc) {
            System.out.print("|");
            for (double value : row) {
                System.out.printf(" %5.3f ", value);
            }
            System.out.println("|");
        }
    }
}
