package model.dao.utils;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Taras on 03.03.2017.
 */
public class DbProcessor {
    public Connection con;

    public DbProcessor() {
        try {
            DriverManager.registerDriver(new FabricMySQLDriver());
        } catch (SQLException e) {
            System.err.println("DriverManager fail");
            e.printStackTrace();
        }
    }

    public Connection getCon(String userName, String password, String url) {
        if (con != null) return con;

        try {
            con = (Connection) DriverManager.getConnection(url, userName, password);
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
