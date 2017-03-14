package model.service;

import model.dao.FieldDao;
import model.dao.util.FactoryDao;
import model.entity.Field;
import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class FieldService {
    private FieldDao fieldDao;

    public FieldService() {
        this.fieldDao = FactoryDao.getInstance().getFieldDao();
    }

    public Field getField(Player player) {
        return fieldDao.getField(player);
    }

    public FieldDao getFieldDao() {
        return fieldDao;
    }

    public void setFieldDao(FieldDao fieldDao) {
        this.fieldDao = fieldDao;
    }
}
