package model.Server;

import model.core.BuildingConstruct;
import model.dao.impl.PlayerDaoImpl;
import model.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by Taras on 10.03.2017.
 */
public class ClientServer {
    public static void main(String[] args) {

//        createBuilding();
//    startgame();
        System.out.println(new Date());
    }


    public static void method() {
        Field field = new Field();
        field.createEmptyCells();
//            field.consoleSoutField();

        Building reactor = new Building();
        reactor.setName("reactor");


//        BuildingConstruct buildingConstruct = new BuildingConstruct(reactor, field.getCell(1, 1));
//buildingConstruct.run();
        field.setCell(reactor, 2, 5);

        Building b = (Building) field.getCell(2, 5);
//        field.consoleSoutField();
    }

    public static void createBuilding() {
        Building reactor = new Building();
        Field field = new Field();
        field.createEmptyCells();
        new BuildingConstruct(reactor, field, 2, 1).run();
        field.consoleSoutField();
    }


    public static void startgame() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String nick = "";
        String pass = "";
        while (true)
        {
//            System.out.println("login");
//            try {
//                    readConsole(reader);
//            //    authorisation();// как только успешно авторизировались, получаем инстанс фермы
//
//                if(reader.readLine().equals("FARM STATUS"))
//                {
//
//                }
//                nick = reader.readLine();
//                System.out.println("pass");
//                pass = reader.readLine();
//
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            String[] strArr = new String[0];

            try {
                strArr = reader.readLine().split(" ");

            if(reader.readLine().equals("FARM STATUS"))
            {

            }
            if(reader.readLine().equals("EXIT"))
            {

            }
            if(reader.readLine().equals("EXIT SERVER"))
            {

            }
            if(reader.readLine().equals("GET AV Р"))
            {

            }
            if(reader.readLine().equals("GET AV B"))
            {

            }
            if(strArr[0].equals("SET") && strArr[1].equals("PLANT"))
            {
                String plantName = strArr[2];
                String x = strArr[4];
                String y = strArr[5];


            }
            if(strArr[0].equals("PICK")  && strArr[1].equals("UP"))
            {
                String x = strArr[3];
                String y = strArr[4];
            }
            if(strArr[0].equals("DEL")  && strArr[1].equals("PLANT"))
            {
                String x = strArr[2];
                String y = strArr[3];
            }
            if(strArr[0].equals("SET") && strArr[1].equals("BUILDING"))
            {
                String buildName = strArr[2];
                String x = strArr[4];
                String y = strArr[5];


            }
            if(strArr[0].equals("DEL")  && strArr[1].equals("PLANT"))
            {
                String x = strArr[2];
                String y = strArr[3];
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

        //запуск потока который слушает пользовательский ввод

    private static void readConsole(BufferedReader reader) throws IOException {
        String[] strArr = reader.readLine().split(" ");
        if(reader.readLine().equals("FARM STATUS"))
        {

        }
        if(reader.readLine().equals("EXIT"))
        {

        }
        if(reader.readLine().equals("EXIT SERVER"))
        {

        }
        if(reader.readLine().equals("GET AV Р"))
        {

        }
        if(reader.readLine().equals("GET AV B"))
        {

        }
        if(strArr[0].equals("SET") && strArr[1].equals("PLANT"))
        {
            String plantName = strArr[2];
            String x = strArr[4];
            String y = strArr[5];


        }
        if(strArr[0].equals("PICK")  && strArr[1].equals("UP"))
        {
            String x = strArr[3];
            String y = strArr[4];
        }
        if(strArr[0].equals("DEL")  && strArr[1].equals("PLANT"))
        {
            String x = strArr[2];
            String y = strArr[3];
        }
        if(strArr[0].equals("SET") && strArr[1].equals("BUILDING"))
        {
            String buildName = strArr[2];
            String x = strArr[4];
            String y = strArr[5];

        }
        if(strArr[0].equals("DEL")  && strArr[1].equals("PLANT"))
        {
            String x = strArr[2];
            String y = strArr[3];
        }
    }

}
