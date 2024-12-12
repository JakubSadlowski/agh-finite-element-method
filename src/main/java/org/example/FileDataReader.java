package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader {

    public static GlobalData readFile(String filePath) {
        GlobalData globalData = null;
        Grid grid;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readingNodes = false;
            boolean readingElements = false;
            boolean readingBC = false;
            Node[] nodes = null;
            Element[] elements = null;
            int nodeIndex = 0;
            int elementIndex = 0;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("[,\\s]+");

                if (line.startsWith("SimulationTime")) {
                    globalData = new GlobalData(
                            Double.parseDouble(tokens[1]), 0, 0, 0, 0, 0, 0, 0, 0, 0
                    );
                } else if (line.startsWith("SimulationStepTime")) {
                    if (globalData != null){
                        globalData.setSimulationStepTime(Double.parseDouble(tokens[1]));
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("Conductivity")) {
                    if (globalData != null){
                        globalData.setConductivity(Double.parseDouble(tokens[1]));
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("Alfa")) {
                    if (globalData != null){
                        globalData.setAlpha(Double.parseDouble(tokens[1]));
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("Tot")) {
                    if (globalData != null){
                        globalData.setTot(Double.parseDouble(tokens[1]));
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("InitialTemp")) {
                    if (globalData != null){
                        globalData.setInitialTemp(Double.parseDouble(tokens[1]));
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("Density")) {
                    if (globalData != null){
                        globalData.setDensity(Double.parseDouble(tokens[1]));
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("SpecificHeat")) {
                    if (globalData != null){
                        globalData.setSpecificHeat(Double.parseDouble(tokens[1]));
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("Nodes number")) {
                    int nN = Integer.parseInt(tokens[2]);
                    if (globalData != null){
                        globalData.setnN(nN);
                        nodes = new Node[nN];
                    } else{
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("Elements number")) {
                    int nE = Integer.parseInt(tokens[2]);
                    if (globalData != null){
                        globalData.setnE(nE);
                        elements = new Element[nE];
                    } else {
                        throw new NullPointerException("GlobalData is not initialized.");
                    }
                } else if (line.startsWith("*Node")) {
                    readingNodes = true;
                } else if (line.startsWith("*Element")) {
                    readingNodes = false;
                    readingElements = true;
                } else if (line.startsWith("*BC")) {
                    readingElements = false;
                    readingBC = true;
                }

                if (readingNodes && !line.startsWith("*Node")) {
                    int nodeID = Integer.parseInt(tokens[1]);
                    double x = Double.parseDouble(tokens[2]);
                    double y = Double.parseDouble(tokens[3]);
                    if (nodes != null){
                        nodes[nodeIndex] = new Node(x, y, nodeID);
                        nodeIndex++;
                    } else {
                        throw new NullPointerException("Nodes array is not initialized.");
                    }
                }

                if (readingElements && !line.startsWith("*Element")) {
                    int elementID = Integer.parseInt(tokens[1]);
                    int[] ids = new int[4];
                    ids[0] = Integer.parseInt(tokens[2]);
                    ids[1] = Integer.parseInt(tokens[3]);
                    ids[2] = Integer.parseInt(tokens[4]);
                    ids[3] = Integer.parseInt(tokens[5]);
                    if (elements != null){
                        elements[elementIndex] = new Element(ids, elementID);
                        elementIndex++;
                    } else {
                        throw new NullPointerException("Elements array is not initialized.");
                    }
                }

                if (readingBC && nodes != null) {
                    for (String token : tokens) {
                        try {
                            int nodeId = Integer.parseInt(token.trim());
                            if (nodeId > 0 && nodeId <= nodes.length) {
                                nodes[nodeId - 1].setBC(true);
                            }
                        } catch (NumberFormatException ignored) {
                            // Skip non-numeric tokens
                        }
                    }
                }
            }

            if (globalData != null){
                grid = new Grid(globalData.getnN(), globalData.getnE());
                grid.setNodes(nodes);
                grid.setElements(elements);
                globalData.setGrid(grid);
            } else {
                throw new NullPointerException("GlobalData is not initialized.");
            }

        } catch (IOException e) {
            System.err.println("File could not be read properly.");
            e.printStackTrace();

        } catch (NullPointerException npe) {
            System.err.println("Error: " + npe.getMessage());
        }

        return globalData;
    }
}
