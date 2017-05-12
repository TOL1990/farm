package com.test.util;

import com.test.Area.dao.AreaDao;
import com.test.Area.dao.AreaDaoImpl;
import com.test.field.dao.FieldDao;
import com.test.field.dao.FieldDaoImpl;
import com.test.player.dao.PlayerDao;
import com.test.player.dao.PlayerDaoImpl;

/**
 * Created by Taras on 09.03.2017.
 */
public class FactoryDao
{

    private static FactoryDao instance = null;
    //    private static PlayerDao playerDao = null;
//    private static FieldDao fieldDao = null;
    private PlayerDao playerDao = null;
    private FieldDao fieldDao = null;
    private AreaDao areaDao = null;
    //    public FieldDao getFieldDao() {
//        if (fieldDao == null) {
//            fieldDao = new FieldDaoImpl();
//        }
//        return fieldDao;
//    }

    //    public PlayerDao getPlayerDao() {
//        if (playerDao == null) {
//            playerDao = new PlayerDaoImpl();
//        }
//        return playerDao;
//    }

    public static synchronized FactoryDao getInstance()
    {
        if (instance == null)
        {
            instance = new FactoryDao();
        }
        return instance;
    }

    public FieldDao getFieldDao()
    {
        fieldDao = new FieldDaoImpl();
        return fieldDao;
    }

    public PlayerDao getPlayerDao()
    {
        playerDao = new PlayerDaoImpl();
        return playerDao;
    }

    public AreaDao getAreaDao()
    {
        areaDao = new AreaDaoImpl();
        return areaDao;
    }
}
