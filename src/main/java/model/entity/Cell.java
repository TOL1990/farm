package model.entity;

/**
 * Created by Taras on 09.03.2017.
 */
public abstract class Cell {
    protected int xPosition;
    protected int yPosition;
    protected  CellType type;

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
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
