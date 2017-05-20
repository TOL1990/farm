package com.test.field.controller;

import com.test.field.dao.FieldDao;
import com.test.field.entity.Field;
import com.test.player.entity.Player;
import com.test.util.FactoryDao;

/**
 * Created by Taras.
 */
public enum FieldManager
{
    INSTANCE;

    private FieldDao fieldDao;

    FieldManager()
    {
        fieldDao = FactoryDao.getInstance().getFieldDao();
    }

    public Field getFieldByUserId(long userId)
    {
        return fieldDao.getField(new Player(userId));
    }
    
    public void addField(long playerId)
    {
        fieldDao.addField(playerId);
    }


    public void updateCell(long fieldId, int x, int y)
    {
        fieldDao.updateCell(fieldId, x, y);
    }
}
