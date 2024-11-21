package org.example;

public class ResultsCalculator {

    public static void calculateResults(GlobalData globalData, int integrationPoints) {
        Grid grid = globalData.getGrid();

        ElementUni elementUni = new ElementUni(integrationPoints);
        //elementUni.printResults();

        for (Element element : grid.getElements()) {
            System.out.println("\nCalculations for element: " + element.getElementID());
            Jacobian jacobian = new Jacobian(integrationPoints, globalData, elementUni, element.getElementID());
            //jacobian.printJacobians();
            MatrixH matrixH = new MatrixH(integrationPoints, globalData, elementUni, jacobian);
            matrixH.printResults();
        }
    }
}
