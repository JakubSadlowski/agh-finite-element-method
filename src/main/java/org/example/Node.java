package org.example;

public class Node {
    private final double x;
    private final double y;
    private final int nodeID;

    public Node(double x, double y, int nodeID) {
        this.x = x;
        this.y = y;
        this.nodeID = nodeID;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Node " +  nodeID + " {" + "x=" + x + ", y=" + y + "}";
    }
}
