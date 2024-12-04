package org.example;

public class GlobalMatrixH {
    private final double[][] globalMatrixH;

    public GlobalMatrixH(GlobalData globalData) {
        globalMatrixH = new double[globalData.getnN()][globalData.getnN()];
    }

    public void calculateGlobalMatrixH(Element element, double[][] matrixH) {
        int[] nodeIDs = element.getElements();

        for (int i = 0; i < nodeIDs.length; i++) {
            for (int j = 0; j < nodeIDs.length; j++) {
                int globalRow = nodeIDs[i] - 1;
                int globalCol = nodeIDs[j] - 1;
                globalMatrixH[globalRow][globalCol] += matrixH[i][j];
            }
        }
    }

    public void printGlobalMatrixH() {
        System.out.println("Global Matrix H:");
        for (double[] row : globalMatrixH) {
            for (double value : row) {
                System.out.printf("%10.4f ", value);
            }
            System.out.println();
        }
    }

    public double[][] getGlobalMatrixH() {
        return globalMatrixH;
    }
}
