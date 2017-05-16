package com.test.core.command;

import com.test.core.ConstCollections;
import com.test.field.controller.FieldManager;
import com.test.field.entity.Building;
import com.test.field.entity.CELL_TYPE;
import com.test.field.entity.Field;
import com.test.player.conlroller.PlayerManager;
import com.test.player.entity.Player;
import com.test.util.propertyconfig.ErrorConfig;

/**
 * Created by Taras on 10.03.2017.
 */
public class BuildingConstruct extends Command
{
    private Player player;
    private Building building;
    private Field field;
    private int x;
    private int y;

    public BuildingConstruct(String buildingName, long playerId, int x, int y)
    {
        this.building = getBuildingWithName(buildingName);
        this.field = FieldManager.INSTANCE.getFieldByUserId(playerId);
        this.player = PlayerManager.INSTANCE.getPlayer(playerId);
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

            player.setBalance(player.getBalance() - building.getPrice());
            PlayerManager.INSTANCE.updatePlayerBallance(player);
            
            field.setCell(building, x, y); // ложим в ячейку здание
            FieldManager.INSTANCE.updateCell(field.getId(), field.getCell(x, y));
            run = true;
        }
        return run;
    }

    private void validation()
    {
        if (this.building == null)
        {
            System.out.println("Wrong name for building in BuildingConstruct");
            error = "Wrong name for building in BuildingConstruct";
            setValid(false);
        }
        if (field.getCell(x, y).getType() != CELL_TYPE.EMPTY)
        {
            System.out.println(ErrorConfig.CELL_NOT_EMPTY_CANT_BUILD);
            setValid(false);
            error = ErrorConfig.CELL_NOT_EMPTY_CANT_BUILD;
        }
        //если такого здания нету в базе
        //хватает ли денег
        if (player.getBalance() < building.getPrice())
        {
            System.out.println("Нехватает денег для постройки здания");
            setValid(false);
            error = ErrorConfig.NOT_ENOUGH_MONEY;
        }
    }

    private Building getBuildingWithName(String buildingName)
    {
        for (Building building : ConstCollections.avaliableBuildings)
        {
            if (building.getName().equals(buildingName))
            {
                return new Building(building.getId(), building.getName(), building.getBonus(), building.getPrice());
            }
        }
        return null;
    }
}
