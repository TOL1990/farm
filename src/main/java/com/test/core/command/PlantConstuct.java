package com.test.core.command;

import com.test.field.entity.CELL_TYPE;
import com.test.field.entity.Field;
import com.test.field.entity.Plant;
import com.test.user.entity.Player;
import com.test.util.propertyconfig.ErrorConfig;


/**
 * Created by Taras on 10.03.2017.
 */
public class PlantConstuct extends Command
{
    private Plant plant;
    private Field field;
    private int x;
    private int y;


    public PlantConstuct(Plant plant, Field field, int x, int y)
    {
        this.plant = plant;
        this.field = field;
        this.x = x;
        this.y = y;
        error = "";
    }

    public boolean run()
    {
        //нельзя строить в непустой клетке
        validation();
        boolean run = false;


        if (isValid())
        {
            plant.setXPosition(x);
            plant.setYPosition(y);
            plant.setPlantedTime(System.currentTimeMillis());

            Player player = field.getPlayer();
            player.setBalance(player.getBalance() - plant.getPrice());
            field.setCell(plant, x, y); // ложим в ячейку растение
            run = true;
        }

        return run;
    }

    public void validation()
    {
        
        if (field.getCell(x, y).getType() != CELL_TYPE.EMPTY)
        {
            error = ErrorConfig.CELL_NOT_EMPTY_CANT_PLANT;
            System.out.println(error);
            setValid(false);
        }
        //если такого здания нету в базе

        //хватает ли денег
        if (field.getAvaliableMoney() < plant.getPrice())
        {
            error = ErrorConfig.NOT_ENOUGH_MONEY;
            System.out.println(error);
            setValid(false);
        }
    }
}
