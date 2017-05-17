package com.test.Area.controller;

import com.test.Area.dao.AreaDao;
import com.test.Area.entity.Area;
import com.test.util.FactoryDao;

/**
 * Created by Taras.
 */
public enum AreaManager
{
    INSTANCE;
    
    private AreaDao areaDao;
    AreaManager()
    {
        areaDao = FactoryDao.getInstance().getAreaDao();
    }


    public Area getAreaByCoorinates(int x, int y)
    {
        return areaDao.getAreaByCoordinates(x,y);
    }

    public Area getAreaByFieldId(long fieldId)
    {
        return areaDao.getAreaByFieldId(fieldId);
    }
    public void addNewArea(Long fieldId){
        areaDao.addNewArea(fieldId);
    }
}
