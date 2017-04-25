package model.Server.connection.src.main.java.com.fenix.buffer;

import com.aad.myutil.logger.MYLoggerFactory;
import com.aad.myutil.server.MYServer;
import jdk.nashorn.internal.parser.JSONParser;
import model.Server.GameManager;
import model.Server.connection.src.main.java.com.fenix.command.controller.COMMAND_FAMILY;
import model.Server.connection.src.main.java.com.fenix.command.controller.LOGIN_COMMAND;
import model.Server.connection.src.main.java.com.fenix.util.JSONHelper;
import model.Server.connection.src.main.java.com.fenix.util.KEYS;
import model.Server.connection.src.main.java.com.fenix.util.TransferCellInfo;
import model.entity.*;
import model.service.GameService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrew.
 */
public class LoginBuffer extends AbstractBuffer<LOGIN_COMMAND> {
    public LoginBuffer() {
        super(COMMAND_FAMILY.LOGIN);
        loginEvent();
        reconnectEvent();
        registrationEvent();

    }

    @Override
    public void executeCommand(long userId, LOGIN_COMMAND command, Object o) {
        try {
            JSONObject json = (JSONObject) o;
            switch (command) {
                case DEVICE_INFO: {
                    getDeviceInfo(userId, json);
                    break;
                }
//                case OTVETKA: {
//                    //otvetkaMeth(userId, json);
//                  //  getFarm(userId);
//                    break;
//                }
//                case FARM_STATUS: {
//                    farmStatus(userId);
//                    break;
//                }
//                case LOGIN: {
//                    String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
//                    break;
//                }
//                case GETAVPLANTS: {
//                    getAvaliablePlants(userId);
//                    break;
//                }
//                case PICK_UP_PLANT: {
//                    pickUpPlant(userId,json);
//                    break;
//                }

            }
        } catch (Exception e) {
            MYLoggerFactory.get().error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

//       private void otvetkaMeth(long userId, JSONObject json) {
//        //    sendData(userId, LOGIN_COMMAND.LOGIN_SUCCESS, json);
////        String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
///       System.out.println("OTVETKA USER IDDDDDDDDDDDDDDD = " + userId);
//        msg += "11111 ";
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), msg);
//        sendData(userId, LOGIN_COMMAND.OTVETKA, response);
//*/
//        //  farmStatus(userId);
//        getFarm(userId);
//    }

    private void getDeviceInfo(long userId, JSONObject json) {

    }

    private void loginEvent() {
        MYServer.getServerEvents().addListener(MYServer.MYSERVER_EVENTS.LISTENER_LOGIN, params ->
        {
            long userId = (Long) params[0];
            loginSuccess(userId);
            System.err.println("Login = " + userId);
        });
    }

    private void reconnectEvent() {
        MYServer.getServerEvents().addListener(MYServer.MYSERVER_EVENTS.LISTENER_RECONNECT, params ->
        {
            long userId = (Long) params[0];
            loginSuccess(userId);
            System.err.println("Reconnect = " + userId);
        });
    }

    private void registrationEvent() {
        MYServer.getServerEvents().addListener(MYServer.MYSERVER_EVENTS.LISTENER_REG, params ->
        {
            long userId = (Long) params[0];
            loginSuccess(userId);
            System.err.println("Registration = " + userId);
        });
    }

    private void loginSuccess(long userId) {
        JSONObject json = new JSONObject();
        sendData(userId, LOGIN_COMMAND.LOGIN_SUCCESS, json);
    }

    @Override
    public LOGIN_COMMAND getCommand(Object o) {
        int commandId = (Integer) o;
        return LOGIN_COMMAND.valueOf(commandId);
    }

    //////////////Самописные команды для фермы. их стоит перенести в отдельный клас\\\\\\\\\\\\\\\\\\\\\

//    private void farmStatus(long userId) {
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        gameService.setPlayer(new Player("ca", "123")); //забиваем игрока из базы руками
//        List<TransferCellInfo> cells = farmToTransferList(gameService.getField(gameService.getPlayer()));//тут тащим из базы
//        // List<TransferCellInfo> cells = makeRandomField();
//        String responseMsg = celltoJSONArray(cells);
//
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);  // makeRandomJSONField());    //responseMsg);
//        sendData(userId, LOGIN_COMMAND.FARM_STATUS, response);
//    }
//
//    private void getFarm(long userId) {
//        // String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        gameService.setPlayer(new Player("Q", "11")); //забиваем игрока из базы руками
//        List<Cell> cells = gameService.getField().getCells();
//        JSONObject response = new JSONObject();
//
//        ///////////////
///*
//        System.out.println("OTVETKA USER IDDDDDDDDDDDDDDD = " + userId);
//        msg += "11111 ";
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), msg);
//        sendData(userId, LOGIN_COMMAND.OTVETKA, response);
//    ////////////////////////////////
//        */
//        String responseMsg = celltoJSONArray(null);
//        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
//        //sendData(userId, LOGIN_COMMAND.OTVETKA, response);
//        sendData(userId, LOGIN_COMMAND.FARM_STATUS, response);
//    }
////    private void pickUpPlant(long userId, JSONObject json) {
////        GameService gameService = GameManager.INSTANCE.getGameService(userId);
////       String answer = gameService.pickupPlant(json.get("x").toString(), json.get("y").toString()); //вернет "" если все ок
////        JSONObject response = new JSONObject();
////        if(answer.equals(""))
////        {
////            List<TransferCellInfo> cells = farmToTransferList(gameService.getField(gameService.getPlayer()));//тут тащим из базы
////            String responseMsg = celltoJSONArray(cells);
////            response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
////            sendData(userId, LOGIN_COMMAND.FARM_STATUS, response);
////        }else
////        {
////            response.put(KEYS.MODEL_DATA.getKey(), answer);
////            sendData(userId, LOGIN_COMMAND.PICK_UP_PLANT, response); //здесь я возвращая сообщение об ошибке.
////                                                                    //возможно мне лучше сделать доп маркер/команду для ошибок
////        }
////
////    }
//
//    private void getAvaliablePlants(long userId) {
//        JSONObject response = new JSONObject();
//        String responseMsg = "ALLPlantsAvaliable";
//        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
//        sendData(userId, LOGIN_COMMAND.GETAVPLANTS, response);
//    }
//    private List<TransferCellInfo> farmToTransferList(Field field) {
//        List<TransferCellInfo> resultList = new ArrayList<>();
//        TransferCellInfo tempobj = null;
//        for (Cell cell :
//                field.getCells()) {
//            tempobj = new TransferCellInfo();
//            tempobj.x = cell.getxPosition();
//            tempobj.y = cell.getyPosition();
//            tempobj.typeName = cell.getType().name();
//            if (tempobj.typeName.equals("Empty")) {
//            }
//            if (tempobj.typeName.equals("Plant")) {
//                Plant plantTemp = (Plant) cell;
//                tempobj.planted = plantTemp.getPlantedTime().toString();
//                tempobj.name = plantTemp.getName();
//                tempobj.proseed = plantTemp.getProseed();
//                tempobj.growTime = plantTemp.getGrowTime();
//            }
//            if (tempobj.typeName.equals("Building")) {
//                Building buildingTemp = (Building) cell;
//                tempobj.name = buildingTemp.getName();
//                tempobj.proseedBonus = buildingTemp.getBonus().getProseed();
//                tempobj.timeBonus = buildingTemp.getBonus().getTime();
//            }
//            resultList.add(tempobj);
//        }
//        return resultList;
//    }
//
//    private String celltoJSONArray(List<TransferCellInfo> cells) {
//        JSONArray jsonList = new JSONArray();
//
//        for (TransferCellInfo cell :
//                cells) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("x", cell.x);
//            jsonObject.put("y", cell.y);
//            jsonObject.put("typeName", cell.typeName);
//            jsonObject.put("name", cell.name);
//            jsonObject.put("planted", cell.planted);
//            jsonObject.put("proseedBonus", cell.proseedBonus);
//            jsonObject.put("timeBonus", cell.timeBonus);
//            jsonObject.put("proseed", cell.proseed);
//            jsonObject.put("growTime", cell.growTime);
//            jsonList.add(jsonObject);
//        }
//        return jsonList.toString();
//    }
//
//    private String makeRandomJSONField() {
//        Random random = new Random();
//        String[] types = {"Empty", "Plant", "Building"};
//        String[] plants = {"cabbage", "carrot", "wheet"};
//        String[] buildings = {"reactor", "greenhouse", "fertilizer"};
//        int type, plant, building;
//
//        JSONArray jsonList = new JSONArray();
//        for (int i = 1; i < 9; i++) {
//            for (int j = 1; j < 9; j++) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("x", i);
//                jsonObject.put("y", j);
//
//                type = random.nextInt(3);
//                if (type == 0) {
//                    jsonObject.put("typeName", types[0]);
//                } else if (type == 1) {
//                    jsonObject.put("typeName", types[1]);
//                    jsonObject.put("planted", "10.10.2017 01:00:00");
//                    plant = random.nextInt(3);
//                    switch (plant) {
//                        case 0: {
//                            jsonObject.put("name", plants[0]);
//                            break;
//                        }
//                        case 1: {
//                            jsonObject.put("name", plants[1]);
//                            break;
//                        }
//                        case 2: {
//                            jsonObject.put("name", plants[2]);
//                            break;
//                        }
//                    }
//                } else if (type == 2) {
//                    jsonObject.put("typeName", types[2]);
//                    jsonObject.put("proseedBonus", 350);
//                    building = random.nextInt(3);
//                    switch (building) {
//                        case 0: {
//                            jsonObject.put("name", buildings[0]);
//                            break;
//                        }
//                        case 1: {
//                            jsonObject.put("name", buildings[1]);
//                            break;
//                        }
//                        case 2: {
//                            jsonObject.put("name", buildings[2]);
//                            break;
//                        }
//                    }
//                }
//                jsonList.add(jsonObject);
//            }
//        }
//        return jsonList.toString();
//    }
}
