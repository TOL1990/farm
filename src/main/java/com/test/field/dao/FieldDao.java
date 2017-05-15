package com.test.field.dao;

import com.test.field.entity.Building;
import com.test.field.entity.Cell;
import com.test.field.entity.Field;
import com.test.field.entity.Plant;
import com.test.player.entity.Player;

import java.util.Map;

/**
 * Created by Taras on 09.03.2017.
 */
public interface FieldDao {

    Field getFieldId(Player player);
    Field getField();
    Field getFieldById(long fieldId);
    Map<Integer, Map<Integer, Cell>> getCellsByFieldId(long id);

    void addField(Player player);

    Field getField(Player player);

    Plant getPlantByName(String name);

    void updateCell(long fieldId, int x, int y);
    void updateCell(long fieldId, Cell cell);
    Building getBuildingByName(String name);

    void setEmptyPlant(Field field, int x, int y);
    
}
