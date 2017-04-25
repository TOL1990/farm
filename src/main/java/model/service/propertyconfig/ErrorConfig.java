package model.service.propertyconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Taras on 25.04.2017.
 */
public class ErrorConfig {
    private static final String PROPERTIES_FILE = "src/main/resources/properties/farmErrors.properties";


    public static String NOT_ENOUGH_MONEY;
    public static String CELL_NOT_EMPTY_CANT_BUILD;
    public static String CELL_NOT_EMPTY_CANT_PLANT;

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try {
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);

            NOT_ENOUGH_MONEY = properties.getProperty("NOT_ENOUGH_MONEY");
            CELL_NOT_EMPTY_CANT_BUILD = properties.getProperty("CELL_NOT_EMPTY_CANT_BUILD");
            CELL_NOT_EMPTY_CANT_PLANT = properties.getProperty("CELL_NOT_EMPTY_CANT_PLANT");

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
