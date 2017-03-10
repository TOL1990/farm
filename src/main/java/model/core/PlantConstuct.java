package model.core;

import model.entity.CellType;
import model.entity.Field;
import model.entity.Plant;
import model.entity.Player;

/**
 * Created by Taras on 10.03.2017.
 */
public class PlantConstuct extends Command {
    private Plant plant;
    private Field field;
    private int x;
    private int y;


    public PlantConstuct(Plant plant, Field field, int x, int y) {
        this.plant = plant;
        this.field = field;
        this.x = x;
        this.y = y;
    }

    public boolean run() {
        //нельзя строить в непустой клетке
        if (field.getCell(x,y).getType() != CellType.Empty) {
            System.out.println("Нельзя посадить растение в занятую клетку");
            return false;
        }

        plant.setxPosition(x);
        plant.setyPosition(y);

        field.setCell(plant, x,y); // ложим в ячейку растение
        return true;
    }
}
