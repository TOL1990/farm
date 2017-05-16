package com.test.field.controller;

import com.test.field.dao.FieldDao;
import com.test.field.entity.Cell;
import com.test.field.entity.Field;
import com.test.player.entity.Player;
import com.test.util.FactoryDao;

/**
 * Created by Taras.
 */
public enum FieldManager
{
    INSTANCE;

    //private Map<Long, Long> fieldByUserId;//<UserId, FieldId>
    private FieldDao fieldDao;

    FieldManager()
    {
        //  fieldByUserId = new ConcurrentHashMap<>();
        fieldDao = FactoryDao.getInstance().getFieldDao();
    }

    public Field getFieldById(long fieldId)
    {
        return fieldDao.getFieldById(fieldId);
    }

    public Field getFieldByUserId(long userId)
    {
        return fieldDao.getField(new Player(userId));
    }

    public void updateCell(Long playerId, Cell cell)
    {
        Field field = getFieldByUserId(playerId);
        fieldDao.updateCell(field.getId(), cell);
    }
    public void addField(long playerId)
    {
        fieldDao.addField(playerId);
    }

    // Реализация когда кеш лежит в менеджере
//    INSTANCE;
//    private Map<Long, Field> fields;//<field Id, Field>
//    private Map<Long, Long> fieldByUserId;//<UserId, FieldId>
//
//    private FieldDao fieldDao;
//
//
//    FieldManager()
//    {
//        fields = new ConcurrentHashMap<>();
//        fieldByUserId = new ConcurrentHashMap<>();
//        fieldDao = FactoryDao.getInstance().getFieldDao();
//    }
//
//    public Field getFieldByUserId(long userId)
//    {
//        Field field = null;
//        Long fieldId = fieldByUserId.get(userId);
//        if (fieldId != null)
//        {
//            field = fields.get(fieldId);
//        }
//        return field;
//    }
//
//
//    
//    
//    public Field getFieldById(long fieldId)
//    {
//        Field field = fields.get(fieldId);
//        if(field != null)
//        {
//            addField(fieldId);
//        }
//        return  field;
//    }
////затащит з дао
//    public Field addField(long fieldId)
//    {
//        Field field = fieldDao.getFieldById(fieldId);
//        if(field != null)
//        {
//            fields.put(fieldId, field);
//            fieldByUserId.put(field.getPlayer().getId(), fieldId);
//        }
//        return field;
//    }
}
