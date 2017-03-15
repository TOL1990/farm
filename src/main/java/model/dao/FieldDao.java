package model.dao;

import model.entity.Cell;
import model.entity.Field;
import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public interface FieldDao {

    Field getFieldId(Player player);
    Field getField();

    List<Cell> getCellsByFieldId(long id);

    void addField(Player player);

    Field getField(Player player);
}
