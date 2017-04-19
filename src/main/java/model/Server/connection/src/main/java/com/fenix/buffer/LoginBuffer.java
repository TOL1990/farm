package model.Server.connection.src.main.java.com.fenix.buffer;

import com.aad.myutil.logger.MYLoggerFactory;
import com.aad.myutil.server.MYServer;
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

import java.util.ArrayList;
import java.util.List;

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
                case OTVETKA: {
                    //otvetkaMeth(userId, json);
                    getFarm(userId);
                    break;
                }
                case FARM_STATUS: {
                    farmStatus(userId);
                    break;
                }
                case LOGIN: {
                    String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
//                    System.out.println("111111111111111111111111111user id = " + msg);
                    break;
                }
                case GETAVPLANTS: {
                        getAvaliablePlants(userId);
                    break;
                }

            }
        } catch (Exception e) {
            MYLoggerFactory.get().error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void getAvaliablePlants(long userId) {
        JSONObject response = new JSONObject();
        String responseMsg = "ALLPlantsAvaliable";
        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
        sendData(userId, LOGIN_COMMAND.GETAVPLANTS, response);
    }

    private void otvetkaMeth(long userId, JSONObject json) {
        //    sendData(userId, LOGIN_COMMAND.LOGIN_SUCCESS, json);

     String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
/*
        System.out.println("OTVETKA USER IDDDDDDDDDDDDDDD = " + userId);
        msg += "11111 ";
        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), msg);
        sendData(userId, LOGIN_COMMAND.OTVETKA, response);
*/
        //  farmStatus(userId);
        getFarm(userId);
    }

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

    private void farmStatus(long userId) {
        GameService gameService = GameManager.INSTANCE.getGameService(userId);
        gameService.setPlayer(new Player("ca", "123")); //забиваем игрока из базы руками
        List<TransferCellInfo> cells = farmToTransferList(gameService.getField(gameService.getPlayer()));

        String responseMsg = celltoJSONArray(cells);
        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
        sendData(userId, LOGIN_COMMAND.FARM_STATUS, response);
    }



    private void getFarm(long userId) {
        // String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
        GameService gameService = GameManager.INSTANCE.getGameService(userId);
        gameService.setPlayer(new Player("Q", "11")); //забиваем игрока из базы руками
        List<Cell> cells = gameService.getField().getCells();
        JSONObject response = new JSONObject();

        ///////////////
/*
        System.out.println("OTVETKA USER IDDDDDDDDDDDDDDD = " + userId);
        msg += "11111 ";
        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), msg);
        sendData(userId, LOGIN_COMMAND.OTVETKA, response);
    ////////////////////////////////
        */

        String responseMsg = celltoJSONArray(null);
        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
        //sendData(userId, LOGIN_COMMAND.OTVETKA, response);
        sendData(userId, LOGIN_COMMAND.FARM_STATUS, response);
    }


    private List<TransferCellInfo> farmToTransferList(Field field) {
        List<TransferCellInfo> resultList = new ArrayList<>();
        TransferCellInfo tempobj = null;
        for (Cell cell :
                field.getCells()) {
            tempobj = new TransferCellInfo();
            tempobj.x = cell.getxPosition();
            tempobj.y = cell.getyPosition();
            tempobj.typeName = cell.getType().name();
            if (tempobj.typeName.equals( "Empty")) {

            }
            if (tempobj.typeName.equals( "Plant")) {
                Plant plantTemp = (Plant) cell;
                tempobj.planted = plantTemp.getPlantedTime().toString();
                tempobj.name = plantTemp.getName();
            }
            if (tempobj.typeName.equals("Building")) {
                Building buildingTemp = (Building) cell;
                tempobj.name = buildingTemp.getName();
            }
            resultList.add(tempobj);
        }
        return resultList;
    }
    private String celltoJSONArray(List<TransferCellInfo> cells) {
        JSONArray jsonList = new JSONArray();
        for (TransferCellInfo cell :
                cells) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", cell.x);
            jsonObject.put("y", cell.y);
            jsonObject.put("typeName",cell.typeName);
            jsonObject.put("name",cell.name);
            jsonObject.put("planted",cell.planted);
            jsonList.add(jsonObject);
        }
        return jsonList.toString();
    }
}
