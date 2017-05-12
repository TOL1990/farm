package com.test.util.propertyconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class  ConnectingConfig {

    private static final String PROPERTIES_FILE = "src/main/resources/properties/connectionDB.properties";

    public static int PORT;
    public static String URL;
    public static String USERNAME;
    public static String PASSWORD;
    public static String IP;
//
    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try {
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);


            PORT = Integer.parseInt(properties.getProperty("PORT"));
            URL = properties.getProperty("URL");
            USERNAME = properties.getProperty("USERNAME");
            PASSWORD = properties.getProperty("PASSWORD");
            IP = properties.getProperty("IP");


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
