package model.entity;

/**
 * Created by Taras on 10.03.2017.
 * Пустая клетка готовая под застройку или засадку
 */
public class EmptyCell extends Cell {

    public EmptyCell(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.setType(CellType.Empty);
    }


}
