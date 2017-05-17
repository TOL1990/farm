package com.test.util.propertyconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Taras on 09.03.2017.
 */
public class QueryConfig {

    private static final String PROPERTIES_FILE = "src/main/resources/properties/QuerySQLs.properties";


    public static String GET_ALL_PLAYERS;
    public static String ADD_PLAYER;
    public static String GET_PLAYER_BY_NICK;
    public static String GET_FIELD_BY_USER_ID;
    public static String ADD_FIELD_BY_USER_ID;
    public static String GET_CELLS_BY_FIELD_ID;
    public static String ADD_NEW_EMPTY_CELL;
    public static String UPDATE_CELL;
    public static String GET_ALL_PLANTS;
    public static String GET_ALL_BUILDINGS;
    public static String UPDATE_PLAYER_BALLANCE;
    public static String UPDATE_ALL_CELL_IN_FIELD;
    public static String GET_AREA_CELL_BY_FIELD_ID;
    public static String GET_CELLS_BY_AREA_ID;
    public static String GET_AREA_BY_ID;
    public static String GET_ALL_AREAS;
    public static String UPDATE_AREA_CELL;

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try {
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);

            GET_ALL_PLAYERS = properties.getProperty("GET_ALL_PLAYERS");
            ADD_PLAYER = properties.getProperty("ADD_PLAYER");
            GET_PLAYER_BY_NICK = properties.getProperty("GET_PLAYER_BY_NICK");
            GET_FIELD_BY_USER_ID = properties.getProperty("GET_FIELD_BY_USER_ID");
            ADD_FIELD_BY_USER_ID = properties.getProperty("ADD_FIELD_BY_USER_ID");
            GET_CELLS_BY_FIELD_ID = properties.getProperty("GET_CELLS_BY_FIELD_ID");
            ADD_NEW_EMPTY_CELL = properties.getProperty("ADD_NEW_EMPTY_CELL");
            UPDATE_CELL = properties.getProperty("UPDATE_CELL");
            GET_ALL_PLANTS = properties.getProperty("GET_ALL_PLANTS");
            GET_ALL_BUILDINGS = properties.getProperty("GET_ALL_BUILDINGS");
            UPDATE_PLAYER_BALLANCE = properties.getProperty("UPDATE_PLAYER_BALLANCE");
            UPDATE_ALL_CELL_IN_FIELD = properties.getProperty("UPDATE_ALL_CELL_IN_FIELD");
            GET_AREA_CELL_BY_FIELD_ID = properties.getProperty("GET_AREA_CELL_BY_FIELD_ID");
            GET_CELLS_BY_AREA_ID = properties.getProperty("GET_CELLS_BY_AREA_ID");
            GET_AREA_BY_ID = properties.getProperty("GET_AREA_BY_ID");
            GET_ALL_AREAS = properties.getProperty("GET_ALL_AREAS");
            UPDATE_AREA_CELL = properties.getProperty("UPDATE_AREA_CELL");

        } catch (FileNotFoundException ex) {
            System.err.println("Properties config file not found");
        } catch (IOException ex) {
            System.err.println("Error while reading file");
        } finally {
            try {
                propertiesFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
