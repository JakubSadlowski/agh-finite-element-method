package org.example;

public class Element {
    private final int[] ID = new int[4];
    private static int elementCounter = 1;

    public Element(int[] ID) {
        if (ID.length == 4) {
            System.arraycopy(ID, 0, this.ID, 0, ID.length);
        } else {
            throw new IllegalArgumentException("ID array must have exactly 4 elements.");
        }
    }

    public int[] getID() {
        return ID;
    }

    public void setID(int[] ID) {
        if (ID.length == 4) {
            System.arraycopy(ID, 0, this.ID, 0, ID.length);
        } else {
            throw new IllegalArgumentException("ID array must have exactly 4 elements.");
        }
    }

    @Override
    public String toString() {
        return "Element " +  elementCounter++ + " {" + "ID=" + java.util.Arrays.toString(ID) + "}";
    }
}
