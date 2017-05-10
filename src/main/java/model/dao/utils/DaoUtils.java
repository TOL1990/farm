package model.dao.utils;

import com.aad.myutil.managers.MYServerSQLManager;
import model.service.propertyconfig.ConnectingConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Taras on 03.03.2017.
 */
public class DaoUtils
{

    public static final String POOL_NAME = "TestConnection";

    static
    {
        MYServerSQLManager.initConnections(POOL_NAME, ConnectingConfig.URL, ConnectingConfig.USERNAME, ConnectingConfig.PASSWORD, 20);
    }

    public static void close(Connection con, Statement st, ResultSet rs) throws SQLException
    {
        if (rs != null)
        {
            rs.close();
        }
        if (st != null)
        {
            st.close();
        }
        if (con != null)
        {
            con.close();
        }
    }

    public static void close(Statement st, ResultSet rs) throws SQLException
    {
        close(null, st, rs);
    }

    public static void close(Statement st) throws SQLException
    {
        close(null, st, null);
    }

    public static void close(Connection con, Statement st) throws SQLException
    {
        close(con, st, null);
    }

    public static Connection getConnection()
    {
        //Здесь обязательно использовать poolconnection
//        return new DbProcessor().getCon(ConnectingConfig.USERNAME, ConnectingConfig.PASSWORD, ConnectingConfig.URL);
        try
        {
            return MYServerSQLManager.getConnection(POOL_NAME);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
