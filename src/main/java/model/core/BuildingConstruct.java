package model.core;

import model.entity.Building;
import model.entity.CellType;
import model.entity.Field;
import model.entity.Player;
import model.service.propertyconfig.ErrorConfig;

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
        this.isValid = true;
        this.error = "";
    }

    public boolean run() {
        //нельзя строить в непустой клетке
        validation();
        if (!isValid) return false;

        building.setXPosition(x);
        building.setYPosition(y);

        Player player = field.getPlayer();
        player.setBalance(player.getBalance() - building.getPrice());
        field.setCell(building, x, y); // ложим в ячейку здание
        return true;
    }

    private void validation() {
        if (field.getCell(x, y).getType() != CellType.Empty) {
            System.out.println(ErrorConfig.CELL_NOT_EMPTY_CANT_BUILD);
            setValid(false);
            error = ErrorConfig.CELL_NOT_EMPTY_CANT_BUILD;
        }
        //если такого здания нету в базе
        //хватает ли денег
        if (field.getAvaliableMoney() < building.getPrice()) {
            System.out.println("Нехватает денег для постройки здания");
            setValid(false);
            error = ErrorConfig.NOT_ENOUGH_MONEY;
        }
    }
}
