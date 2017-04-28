package model.Server.connection.src.main.java.com.fenix.buffer;

import com.aad.myutil.logger.MYLoggerFactory;
import model.Server.GameManager;
import model.Server.connection.src.main.java.com.fenix.command.controller.COMMAND_FAMILY;
import model.Server.connection.src.main.java.com.fenix.command.controller.FARM_COMMAND;
import model.Server.connection.src.main.java.com.fenix.command.controller.WORLD_COMMAND;
import model.Server.connection.src.main.java.com.fenix.util.KEYS;
import model.Server.connection.src.main.java.com.fenix.util.TransferCellInfo;
import model.entity.Area;
import model.service.GameService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Random;

/**
 * Created by Taras on 28.04.2017.
 */
public class WorldBuffer extends AbstractBuffer<WORLD_COMMAND> {
    public WorldBuffer() {
        super(COMMAND_FAMILY.WORLD);
    }

    @Override
    public void executeCommand(long userId, WORLD_COMMAND command, Object o) {
        try {
            JSONObject json = (JSONObject) o;
            switch (command) {
                case GET_AREA: {
                    getArea(userId, json);
                    break;
                }
            }
        } catch (Exception e) {
            MYLoggerFactory.get().error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void getArea(long userId, JSONObject json) {
        GameService gameService = GameManager.INSTANCE.getGameService(userId);
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        gameService.getArea(new Area(x, y));
        JSONObject response = new JSONObject();
        String msg =  getRandomAreaCells();
        response.put(KEYS.MODEL_DATA.getKey(),msg);
        sendData(userId, WORLD_COMMAND.GET_AREA, response);
        System.out.println("SEND to client" + msg);
    }

    @Override
    public WORLD_COMMAND getCommand(Object o) {
        int commandId = (Integer) o;
        return WORLD_COMMAND.valueOf(commandId);
    }

    private String getRandomAreaCells() {
    String json= "";
        Random random = new Random();

        JSONArray jsonList = new JSONArray();
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("x", i);
                jsonObject.put("y", j);
                if(random.nextInt(2) == 0)
                    jsonObject.put("typeName", "Farm");
                else
                    jsonObject.put("typeName", "Empty");

                jsonObject.put("areaId", 1);
                jsonList.add(jsonObject);
            }
        }

        return jsonList.toString();
    }
}
