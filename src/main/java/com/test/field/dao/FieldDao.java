package com.test.field.dao;

import com.test.field.entity.Building;
import com.test.field.entity.Cell;
import com.test.field.entity.Field;
import com.test.field.entity.Plant;
import com.test.user.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by Taras on 09.03.2017.
 */
public interface FieldDao {

    Field getFieldId(Player player);
    Field getField();

    Map<Integer, Map<Integer, Cell>> getCellsByFieldId(long id);

    void addField(Player player);

    Field getField(Player player);

    List<Plant> getAllPlants();

    Plant getPlantByName(String name);

    void updateCell(long fieldId, int x, int y);

    List<Building> getAllBuildings();

    Building getBuildingByName(String name);

    void setEmptyPlant(Field field, int x, int y);
}
