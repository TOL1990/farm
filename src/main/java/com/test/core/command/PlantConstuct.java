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
    private Field field;
    private int x;
    private int y;



    public PlantConstuct(String plantName, long  playerId, int x, int y)
    {
        this.plant = getPlantWithName(plantName);
        this.field = FieldManager.INSTANCE.getFieldByUserId(playerId);
        this.player = PlayerManager.INSTANCE.getPlayer(playerId);
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
            
            player.setBalance(player.getBalance() - plant.getPrice());
            PlayerManager.INSTANCE.updatePlayerBallance(player);
            
            field.setCell(plant, x, y); // ложим в ячейку растение
            FieldManager.INSTANCE.updateCell(field.getId(), field.getCell(x, y));
            run = true;
        }
        return run;
    }

    public void validation()
    {
        if (this.plant == null)
        {
            System.out.println("Wrong name for plant in PlantConstruct");
            error = "Wrong name for plant in PlantConstruct";
            setValid(false);
        }
        if (field.getCell(x, y).getType() != CELL_TYPE.EMPTY)
        {
            error = ErrorConfig.CELL_NOT_EMPTY_CANT_PLANT;
            System.out.println(error);
            setValid(false);
        }
        //если такого здания нету в базе

        //хватает ли денег
        if (player.getBalance()< plant.getPrice())
        {
            error = ErrorConfig.NOT_ENOUGH_MONEY;
            System.out.println(error);
            setValid(false);
        }
    }
    private Plant getPlantWithName(String serchPlant)
    {
        for (Plant plant : ConstCollections.avaliablePlants)
        {
            if(plant.getName().equals(serchPlant))
                return new Plant(plant.getId(), plant.getName(), plant.getPrice(), plant.getProseed(), plant.getGrowTime());
        }
        return null;
    }
}
