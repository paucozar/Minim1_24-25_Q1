package edu.upc.dsa.models;

import edu.upc.dsa.models.ElementType;

public class PuntoInteres {
    private int horizontal;
    private int vertical;
    private ElementType type;

    public PuntoInteres(int horizontal, int vertical, ElementType type) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.type = type;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horitzontal) {
        this.horizontal = horitzontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PuntoInteres [horitzontal="+horizontal+", vertical=" + vertical + ", type=" + type +"]";
    }
}
