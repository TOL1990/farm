package com.test.field.dao;

import com.test.field.entity.Cell;
import com.test.field.entity.Field;
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
    void addField(Long playerId);

    Field getField(Player player);
    void updateCell(long fieldId, Cell cell);
}
