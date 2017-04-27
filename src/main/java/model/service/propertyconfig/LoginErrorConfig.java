package model.service.propertyconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Taras on 25.04.2017.
 */
public class LoginErrorConfig {
    private static final String PROPERTIES_FILE = "src/main/resources/properties/loginErrors.properties";


    public static String WRONG_PASSWORD;
    public static String WRONG_LOGIN_AND_PASSWORD;
    public static String LOGIN_ALREADY_EXIST;


    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try {
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);

            WRONG_PASSWORD = properties.getProperty("WRONG_PASSWORD");
            WRONG_LOGIN_AND_PASSWORD = properties.getProperty("WRONG_LOGIN_AND_PASSWORD");
            LOGIN_ALREADY_EXIST = properties.getProperty("LOGIN_ALREADY_EXIST");

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
