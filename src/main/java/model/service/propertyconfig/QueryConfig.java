package model.service.propertyconfig;

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

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try {
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);


            GET_ALL_PLAYERS = properties.getProperty("GET_ALL_PLAYERS");
            ADD_PLAYER = properties.getProperty("ADD_PLAYER");




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
