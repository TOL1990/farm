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
                case LOGIN: {
                    getDeviceInfo(userId, json);
                    break;
                }

//                case OTVETKA: {
//                    //otvetkaMeth(userId, json);
//                  //  getFarm(userId);
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
    private void sendError(String errorMessage, long userId) {
        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), errorMessage);
        sendData(userId, LOGIN_COMMAND.LOGIN_ERROR, response);
    }

}
