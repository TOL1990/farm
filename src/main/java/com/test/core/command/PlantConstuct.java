package com.test.core.command;

import com.test.core.ConstCollections;
import com.test.field.controller.FieldManager;
import com.test.field.entity.CELL_TYPE;
import com.test.field.entity.Field;
import com.test.field.entity.Plant;
import com.test.player.conlroller.PlayerManager;
import com.test.player.entity.Player;
import com.test.util.propertyconfig.ErrorConfig;


/**
 * Created by Taras on 10.03.2017.
 */
public class PlantConstuct extends Command
{
    private Player player;
    private Plant plant;
    private Plant plantTemplate;
    private Field field;
    private int x;
    private int y;


    public PlantConstuct(String plantName, long playerId, int x, int y)
    {
        this.plant = new Plant();
        plantTemplate = ConstCollections.getPlant(plantName);
        this.field = FieldManager.INSTANCE.getFieldByUserId(playerId);
        this.player = PlayerManager.INSTANCE.getPlayer(playerId);
        this.x = x;
        this.y = y;
        error = "";
        setValid(true);
    }


    public boolean run()
    {
        boolean run = false;
        //нельзя строить в непустой клетке
        validation();
        if (isValid())
        {
            Plant newPlantedCell = getNewPlantCell();

            player.setBalance(player.getBalance() - plantTemplate.getPrice());
            PlayerManager.INSTANCE.updatePlayerBallance(player);

            field.setCell(newPlantedCell, x, y); // ложим в ячейку растение
            FieldManager.INSTANCE.updateCell(field.getId(), x, y);
            run = true;
        }
        return run;
    }

    private Plant getNewPlantCell()
    {
        int x = this.x;
        int y = this.y;
        long proceed = plantTemplate.getProceed();
        long growTime = plantTemplate.getGrowTime();
        long planted = System.currentTimeMillis();
        String name = plantTemplate.getName();
        long price = plantTemplate.getPrice();
        long id = plantTemplate.getId();

        Plant newPlantCell = new Plant(id, name, price, proceed, growTime, planted, x, y);
        return newPlantCell;
    }

    public void validation()
    {
        
        if (field.getCell(x, y).getType() != CELL_TYPE.EMPTY)
        {
            error = ErrorConfig.CELL_NOT_EMPTY_CANT_PLANT;
            setValid(false);
        }
        //если такого здания нету в базе

        //хватает ли денег
        if (player.getBalance() < plantTemplate.getPrice())
        {
            error = ErrorConfig.NOT_ENOUGH_MONEY;
            setValid(false);
        }
    }

}
