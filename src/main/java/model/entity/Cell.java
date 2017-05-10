package model.entity;

/**
 * Created by Taras on 09.03.2017.
 */
public abstract class Cell {
    protected int xPosition;
    protected int yPosition;
    protected CELL_TYPE type;

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public CELL_TYPE getType() {
        return type;
    }

    public void setType(CELL_TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{[" + xPosition +
                ", " + yPosition +
                "], " + type +
                '}';
    }
}
