package org.example;

public class Element {
    private final int[] elements = new int[4];
    private final int elementID;
    private Jacobian[] jacobin;

    public Element(int[] ID, int elementID) {
        if (ID.length == 4) {
            System.arraycopy(ID, 0, this.elements, 0, ID.length);
            this.elementID = elementID;
        } else {
            throw new IllegalArgumentException("Elements array must have exactly 4 elements.");
        }
    }

    public int[] getElements() {
        return elements;
    }

    public void setElements(int[] elements) {
        if (elements.length == 4) {
            System.arraycopy(elements, 0, this.elements, 0, elements.length);
        } else {
            throw new IllegalArgumentException("Elements array must have exactly 4 elements.");
        }
    }

    @Override
    public String toString() {
        return "Element " +  elementID + " {" + "ID=" + java.util.Arrays.toString(elements) + "}";
    }
}
