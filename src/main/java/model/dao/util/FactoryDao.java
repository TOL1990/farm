package model.dao.util;

import model.dao.FieldDao;
import model.dao.PlayerDao;
import model.dao.impl.FieldDaoImpl;
import model.dao.impl.PlayerDaoImpl;

/**
 * Created by Taras on 09.03.2017.
 */
public class FactoryDao {

    private static FactoryDao instance = null;
    //    private static PlayerDao playerDao = null;
//    private static FieldDao fieldDao = null;
    private PlayerDao playerDao = null;
    private FieldDao fieldDao = null;

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

    public static synchronized FactoryDao getInstance() {
        if (instance == null) {
            instance = new FactoryDao();
        }
        return instance;
    }

    public FieldDao getFieldDao() {
        fieldDao = new FieldDaoImpl();
        return fieldDao;
    }

    public PlayerDao getPlayerDao() {
        playerDao = new PlayerDaoImpl();
        return playerDao;
    }

}
