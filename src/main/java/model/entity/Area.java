package model.entity;

import java.util.List;

/**
 * Created by Taras on 26.04.2017.
 */
public class Area {
    private long id;

    private int x;
    private int y;

    private List<AreaCell> cells;

    public Area() {
    }

    public Area(long id) {
        this.id = id;
    }

    public Area(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Area(long id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Area(long id, int x, int y, List<AreaCell> cells) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.cells = cells;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<AreaCell> getCells() {
        return cells;
    }

    public void setCells(List<AreaCell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", cells=" + cells +
                '}';
    }
}
