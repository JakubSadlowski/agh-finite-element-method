package org.example;

public class MatrixHbc {
    private final double[][] Hbc;
    private final GlobalData globalData;
    private final Element element;
    private final Grid grid;
    private final ElementUni elementUni;
    private final int numPoints;

    public MatrixHbc(GlobalData globalData, Element element, int numPoints) {
        this.globalData = globalData;
        this.element = element;
        this.grid = globalData.getGrid();
        this.numPoints = numPoints;
        this.Hbc = new double[4][4];
        this.elementUni = new ElementUni(numPoints);

        calculateMatrixHbc();
    }

    private void calculateMatrixHbc() {
        double[] weights = new GaussQuadratureData(numPoints).getWeights();
        double alpha = globalData.getAlpha();

        // Process each edge of the element
        for (int edge = 0; edge < 4; edge++) {
            Node[] nodes = getEdgeNodes(edge);

            // Skip if not a boundary edge
            if (!nodes[0].isBC() || !nodes[1].isBC()) {
                continue;
            }

            double detJ = calculateEdgeLength(nodes[0], nodes[1]) / 2.0;

            // Integration along the edge
            for (int i = 0; i < numPoints; i++) {
                double[] N = calculateShapeFunctions(edge, elementUni.getKsiEtaValues()[i]);

                // Calculate contribution for this integration point
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        Hbc[j][k] += alpha * N[j] * N[k] * weights[i] * detJ;
                    }
                }
            }
        }
    }

    private Node[] getEdgeNodes(int edge) {
        int[] nodeIds = element.getElements();
        Node[] nodes = new Node[2];

        switch (edge) {
            case 0: // bottom
                nodes[0] = grid.getNodes()[nodeIds[0] - 1];
                nodes[1] = grid.getNodes()[nodeIds[1] - 1];
                break;
            case 1: // right
                nodes[0] = grid.getNodes()[nodeIds[1] - 1];
                nodes[1] = grid.getNodes()[nodeIds[2] - 1];
                break;
            case 2: // top
                nodes[0] = grid.getNodes()[nodeIds[2] - 1];
                nodes[1] = grid.getNodes()[nodeIds[3] - 1];
                break;
            case 3: // left
                nodes[0] = grid.getNodes()[nodeIds[3] - 1];
                nodes[1] = grid.getNodes()[nodeIds[0] - 1];
                break;
        }
        return nodes;
    }

    private double calculateEdgeLength(Node n1, Node n2) {
        double dx = n2.getX() - n1.getX();
        double dy = n2.getY() - n1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double[] calculateShapeFunctions(int edge, double ksi) {
        double[] N = new double[4];

        switch (edge) {
            case 0: // bottom edge (eta = -1)
                N[0] = 0.25 * (1 - ksi) * (1 - (-1));
                N[1] = 0.25 * (1 + ksi) * (1 - (-1));
                N[2] = 0.25 * (1 + ksi) * (1 + (-1));
                N[3] = 0.25 * (1 - ksi) * (1 + (-1));
                break;
            case 1: // right edge (ksi = 1)
                N[0] = 0.25 * (1 - 1) * (1 - ksi);
                N[1] = 0.25 * (1 + 1) * (1 - ksi);
                N[2] = 0.25 * (1 + 1) * (1 + ksi);
                N[3] = 0.25 * (1 - 1) * (1 + ksi);
                break;
            case 2: // top edge (eta = 1)
                N[0] = 0.25 * (1 - ksi) * (1 - 1);
                N[1] = 0.25 * (1 + ksi) * (1 - 1);
                N[2] = 0.25 * (1 + ksi) * (1 + 1);
                N[3] = 0.25 * (1 - ksi) * (1 + 1);
                break;
            case 3: // left edge (ksi = -1)
                N[0] = 0.25 * (1 - (-1)) * (1 - ksi);
                N[1] = 0.25 * (1 + (-1)) * (1 - ksi);
                N[2] = 0.25 * (1 + (-1)) * (1 + ksi);
                N[3] = 0.25 * (1 - (-1)) * (1 + ksi);
                break;
        }
        return N;
    }

    public double[][] getHbc() {
        return Hbc;
    }
}