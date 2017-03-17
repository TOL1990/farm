package model.core;

import model.entity.CellType;
import model.entity.Field;
import model.entity.Plant;
import model.entity.Player;

import java.sql.Timestamp;


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
        validation();
        if (!isValid) return false;

        plant.setxPosition(x);
        plant.setyPosition(y);
        plant.setPlantedTime(new Timestamp(System.currentTimeMillis()));

        Player player = field.getPlayer();
        player.setBalance(player.getBalance() - plant.getPrice());
        field.setCell(plant, x, y); // ложим в ячейку растение

        return true;
    }

    public void validation() {
        if (field.getCell(x, y).getType() != CellType.Empty) {
            System.out.println("Ячейка не пуста. Невозможно посадить растение.");
            setValid(false);
        }
        //если такого здания нету в базе

        //хватает ли денег
        if (field.getAvaliableMoney() < plant.getPrice()) {
            System.out.println("Нехватает денег для посадки растения");
            setValid(false);
        }
    }
}
