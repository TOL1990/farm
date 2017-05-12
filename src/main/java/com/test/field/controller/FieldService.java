package com.test.field.controller;

import com.test.field.dao.FieldDao;
import com.test.field.entity.Building;
import com.test.field.entity.Field;
import com.test.field.entity.Plant;
import com.test.player.entity.Player;
import com.test.util.FactoryDao;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class FieldService {
    private FieldDao fieldDao;

    public  FieldService() {
        this.fieldDao = FactoryDao.getInstance().getFieldDao();
    }

    public Field getFieldId(Player player) {
        return fieldDao.getFieldId(player);
    }

    public FieldDao getFieldDao() {return fieldDao;}

    public void setFieldDao(FieldDao fieldDao) {
        this.fieldDao = fieldDao;
    }

    public void addField(Player player) {
        if (player != null) fieldDao.addField(player);
        else System.out.println("Cant add field, player  = null. FieldService.addField");
    }

    public Field getField() {
        return fieldDao.getField();
    }

    public List<Plant> getAllPlants() {return fieldDao.getAllPlants();}

    public Plant getPlantByName(String name) { return fieldDao.getPlantByName(name);}


    public void updateFieldCell(long fieldId, int x, int y) {
        fieldDao.updateCell(fieldId, x, y);
    }

    public List<Building> getAllBuildings() {return fieldDao.getAllBuildings();}

    public Building getBuildingByName(String name) {
        return fieldDao.getBuildingByName(name);
    }

    public void setEmptyCell(Field field, int x, int y) {
        fieldDao.setEmptyPlant(field, x, y);
    }
}
