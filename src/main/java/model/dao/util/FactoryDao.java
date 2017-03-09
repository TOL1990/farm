package model.dao.util;

import model.dao.PlayerDao;
import model.dao.impl.PlayerDaoImpl;

/**
 * Created by Taras on 09.03.2017.
 */
public class FactoryDao {

    private static PlayerDao playerDao = null;
    //        private static ProductDAO productDAO= null;
//        private static ProductTypeDAO productTypeDAO= null;
    private static FactoryDao instance = null;

    public static synchronized FactoryDao getInstance(){
        if (instance == null){
            instance = new FactoryDao();
        }
        return instance;
    }

    public PlayerDao getPlayerDao(){
        if (playerDao == null){
            playerDao = new PlayerDaoImpl();
        }
        return playerDao;
    }
//
//        public DriverDAO getDriverDAO(){
//            if (driverDAO == null){
//                driverDAO = new DriverDAOImpl();
//            }
//            return driverDAO;
//        }
//
//        public RouteDAO getRouteDAO(){
//            if (routeDAO == null){
//                routeDAO = new RouteDAOImpl();
//            }
//            return routeDAO;
//        }
}
