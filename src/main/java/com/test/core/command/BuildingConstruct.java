package com.test.core.command;

import com.test.core.ConstCollections;
import com.test.field.controller.FieldManager;
import com.test.field.entity.Building;
import com.test.field.entity.BuildingBonus;
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
    private Building buildTemplate;
    private Field field;
    private int x;
    private int y;

    public BuildingConstruct(String buildingName, long playerId, int x, int y)
    {
        this.buildTemplate = ConstCollections.getBuilding(buildingName);
        this.field = FieldManager.INSTANCE.getFieldByUserId(playerId);
        this.player = PlayerManager.INSTANCE.getPlayer(playerId);
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
            Building newBldng = getNewBuilding();

            player.setBalance(player.getBalance() - buildTemplate.getPrice());
            PlayerManager.INSTANCE.updatePlayerBallance(player);

            field.setCell(newBldng, x, y); // ложим в ячейку здание
            FieldManager.INSTANCE.updateCell(field.getId(), x, y);
            run = true;
        }
        return run;
    }

    private Building getNewBuilding()
    {
        int x = this.x;
        int y = this.y;
        long price = buildTemplate.getPrice();
        String name = buildTemplate.getName();
        long id = buildTemplate.getId();
        int timeBonus = buildTemplate.getBonus().getTime();
        int proceedBonus = buildTemplate.getBonus().getProseed();
        return new Building(id, name, new BuildingBonus(timeBonus, proceedBonus), price, x, y);
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
        if (player.getBalance() < buildTemplate.getPrice())
        {
            System.out.println("NOT ENOUGHT MONEY FOR BUILD");
            setValid(false);
            error = ErrorConfig.NOT_ENOUGH_MONEY;
        }
    }
}
