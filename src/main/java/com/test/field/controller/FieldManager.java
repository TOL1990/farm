package com.test.field.controller;

import com.test.field.dao.FieldDao;
import com.test.field.entity.Field;
import com.test.util.FactoryDao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras.
 */
public enum FieldManager
{
    INSTANCE;

    private Map<Long, Field> fields;//<field Id, Field>
    private Map<Long, Long> fieldByUserId;//<UserId, FieldId>

    private FieldDao fieldDao;


    FieldManager()
    {
        fields = new ConcurrentHashMap<>();
        fieldByUserId = new ConcurrentHashMap<>();
        fieldDao = FactoryDao.getInstance().getFieldDao();
    }

    public Field getFieldByUserId(long userId)
    {
        Field field = null;
        Long fieldId = fieldByUserId.get(userId);
        if (fieldId != null)
        {
            field = fields.get(fieldId);
        }
        return field;
    }

    public Field getFieldById(long fieldId)
    {
        Field field = fields.get(fieldId);
        if(field != null)
        {
            addField(fieldId);
        }
        return  field;
    }
//затащит з дао
    public Field addField(long fieldId)
    {
        Field field = fieldDao.getFieldById(fieldId);
        if(field != null)
        {
            fields.put(fieldId, field);
            fieldByUserId.put(field.getPlayer().getId(), fieldId);
        }
        return field;
    }
}
