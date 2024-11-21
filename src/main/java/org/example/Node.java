package org.example;

public class Node {
    private double x;
    private double y;
    private final int nodeID;

    public Node(double x, double y, int nodeID) {
        this.x = x;
        this.y = y;
        this.nodeID = nodeID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getNodeID() {
        return nodeID;
    }

    @Override
    public String toString() {
        return "Node " +  nodeID + " {" + "x=" + x + ", y=" + y + "}";
    }
}
