package org.example;

public class SolveEquation {
    GlobalData globalData;

    public SolveEquation(GlobalData globalData) {
        this.globalData = globalData;
    }

    public void calculateResults(int integrationPoints) {
        Grid grid = globalData.getGrid();
        //GlobalMatrixH globalMatrixH = new GlobalMatrixH(globalData);

        ElementUni elementUni = new ElementUni(integrationPoints);
        //elementUni.printResults();

        for (Element element : grid.getElements()) {
            System.out.println("\nCalculations for element: " + element.getElementID());
            Jacobian jacobian = new Jacobian(integrationPoints, globalData, elementUni, element.getElementID());
            MatrixHbc matrixHbc = new MatrixHbc(globalData, element, integrationPoints);
            matrixHbc.printResults();
            //jacobian.printJacobians();
            //MatrixH matrixH = new MatrixH(integrationPoints, globalData, elementUni, jacobian);
            //matrixH.printResults();
            //globalMatrixH.calculateGlobalMatrixH(element, matrixH.getH());
        }

        //globalMatrixH.printGlobalMatrixH();
    }
}