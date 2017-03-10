package model.core;

import model.entity.Building;
import model.entity.CellType;
import model.entity.Field;

/**
 * Created by Taras on 10.03.2017.
 */
public class BuildingConstruct extends Command {
    private Building building;
    private Field field;
    private int x;
    private int y;


    public BuildingConstruct(Building building, Field field, int x, int y) {
        this.building = building;
        this.field = field;
        this.x = x;
        this.y = y;
    }

    public boolean run() {
        //нельзя строить в непустой клетке
        if (field.getCell(x,y).getType() != CellType.Empty) return false;

        building.setxPosition(x);
        building.setyPosition(y);

        field.setCell(building, x,y); // ложим в ячейку здание

        return true;
    }
}
