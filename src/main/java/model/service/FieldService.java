package model.service;

import model.dao.FieldDao;
import model.dao.util.FactoryDao;
import model.entity.Field;
import model.entity.Plant;
import model.entity.Player;

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

    public List<Plant> getAllPlants() {
        return fieldDao.getAllPlants();
    }

    public Plant getPlantByName(String name) { return fieldDao.getPlantByName(name);
    }


    public void updateFieldCell(long fieldId, int x, int y) {
        fieldDao.updateCell(fieldId, x, y);
    }
}
