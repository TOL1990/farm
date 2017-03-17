package model.dao;

import model.entity.*;

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

    List<Plant> getAllPlants();

    Plant getPlantByName(String name);

    void updateCell(long fieldId, int x, int y);

    List<Building> getAllBuildings();

    Building getBuildingByName(String name);
}
