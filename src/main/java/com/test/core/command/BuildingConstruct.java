package com.test.core.command;

import com.test.field.entity.Building;
import com.test.field.entity.CELL_TYPE;
import com.test.field.entity.Field;
import com.test.user.entity.Player;
import com.test.util.propertyconfig.ErrorConfig;

/**
 * Created by Taras on 10.03.2017.
 */
public class BuildingConstruct extends Command
{
    private Building building;
    private Field field;
    private int x;
    private int y;

    public BuildingConstruct(Building building, Field field, int x, int y)
    {
        this.building = building;
        this.field = field;
        this.x = x;
        this.y = y;
        this.error = "";
        setValid(true);
    }

    public boolean run()
    {
        boolean run = false;
        //нельзя строить в непустой клетке
        validation();
        if (isValid())
        {
            building.setXPosition(x);
            building.setYPosition(y);

            Player player = field.getPlayer();
            player.setBalance(player.getBalance() - building.getPrice());
            field.setCell(building, x, y); // ложим в ячейку здание
            run = true;
        }
        return run;
    }

    private void validation()
    {
        if (field.getCell(x, y).getType() != CELL_TYPE.EMPTY)
        {
            System.out.println(ErrorConfig.CELL_NOT_EMPTY_CANT_BUILD);
            setValid(false);
            error = ErrorConfig.CELL_NOT_EMPTY_CANT_BUILD;
        }
        //если такого здания нету в базе
        //хватает ли денег
        if (field.getAvaliableMoney() < building.getPrice())
        {
            System.out.println("Нехватает денег для постройки здания");
            setValid(false);
            error = ErrorConfig.NOT_ENOUGH_MONEY;
        }
    }
}
