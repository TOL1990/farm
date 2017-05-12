package com.test.buffer;

import com.aad.myutil.logger.MYLoggerFactory;
import com.test.GameManager;
import com.test.controller.COMMAND_FAMILY;
import com.test.controller.WORLD_COMMAND;
import com.test.util.KEYS;
import com.test.Area.entity.Area;
import com.test.Area.entity.AreaCell;
import oldStaff.service.GameService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Random;

/**
 * Created by Taras on 28.04.2017.
 */
public class WorldBuffer extends AbstractBuffer<WORLD_COMMAND>
{
    public WorldBuffer()
    {
        super(COMMAND_FAMILY.WORLD);
    }

    @Override
    public void executeCommand(long userId, WORLD_COMMAND command, Object o)
    {
        try
        {
            JSONObject json = (JSONObject) o;
            switch (command)
            {
                case GET_AREA:
                {
                    getArea(userId, json);
                    break;
                }
                case GET_HOME_AREA:
                {
                    getHomeArea(userId);
                    break;
                }
            }
        }
        catch (Exception e)
        {
            MYLoggerFactory.get().error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void getArea(long userId, JSONObject json)
    {
        GameService gameService = GameManager.INSTANCE.getGameService(userId);
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        Area area = gameService.getArea(new Area(x, y));
        String msg = getJSONStringArea(area);

        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), msg);
        sendData(userId, WORLD_COMMAND.GET_AREA, response);
        System.out.println("SEND to client" + msg);
    }

    @Override
    public WORLD_COMMAND getCommand(Object o)
    {
        int commandId = (Integer) o;
        return WORLD_COMMAND.valueOf(commandId);
    }

    private String getRandomAreaCells()
    {
        String json = "";
        Random random = new Random();
        JSONObject jsonTransfer = new JSONObject();
        jsonTransfer.put("areaId", 1);
        jsonTransfer.put("areaX", 1);
        jsonTransfer.put("areaY", 1);
        JSONArray jsonList = new JSONArray();
        for (int i = 1; i < 6; i++)
        {
            for (int j = 1; j < 6; j++)
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("x", i);
                jsonObject.put("y", j);
                if (random.nextInt(2) == 0)
                {
                    jsonObject.put("typeName", "FARM");
                }
                else
                {
                    jsonObject.put("typeName", "EMPTY");
                }

                jsonList.add(jsonObject);
            }
        }
        jsonTransfer.put("cells", jsonList);

        return jsonTransfer.toString();
    }

    private void getHomeArea(long userId)
    {
        GameService gameService = GameManager.INSTANCE.getGameService(userId);
        Area homeArea = gameService.getArea(); // достанет по ид поля
        String msg = getJSONStringArea(homeArea);

        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), msg);
        System.out.println("на сервер посылаем - " + msg);
        sendData(userId, WORLD_COMMAND.GET_HOME_AREA, response);
        sendHomeNeighbors(userId, homeArea.getX(), homeArea.getY());
    }

    private void getArea(long userId, int x, int y)
    {
        GameService gameService = GameManager.INSTANCE.getGameService(userId);
        Area area = gameService.getArea(new Area(x, y));
        if (area != null)
        {
            JSONObject response = new JSONObject();
            // String msg = getRandomAreaCells();
            String msg = getJSONStringArea(area);
            response.put(KEYS.MODEL_DATA.getKey(), msg);
            sendData(userId, WORLD_COMMAND.GET_AREA, response);
            //System.out.println("SEND to client" + msg);
        }
    }

    private void sendHomeNeighbors(long userId, int homeX, int homeY)
    {
        
        //фурычиит некоректно
//        for (int i = homeX - 1; i < homeX + 2; i++)
//        {
//            for (int j = homeY - 1; j < homeY + 2; j++)
//            {
//                if (i != homeX && j != homeY)
//                {
//                    getArea(userId, i, j);
//                }
//            }
//        }
        getArea(userId, homeX, homeY - 1);
        getArea(userId, homeX, homeY + 1);
        getArea(userId, homeX + 1, homeY + 1);
        getArea(userId, homeX - 1, homeY);
        getArea(userId, homeX + 1, homeY);
        getArea(userId, homeX - 1, homeY + 1);
        getArea(userId, homeX + 1, homeY - 1);
        getArea(userId, homeX - 1, homeY - 1);
    }

    private String getJSONStringArea(Area area)
    {
        JSONObject jsonTransfer = new JSONObject();

        jsonTransfer.put("areaId", area.getId());
//        jsonTransfer.put("areaX", 2);
//        jsonTransfer.put("areaY", 2);
        jsonTransfer.put("areaX", area.getX());
        jsonTransfer.put("areaY", area.getY());
        JSONArray jsonList = new JSONArray();

        for (Map.Entry<Integer, Map<Integer, AreaCell>> entryRows : (area.getCells().entrySet()))
        {
            for (Map.Entry<Integer, AreaCell> entry : entryRows.getValue().entrySet())
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("x", entry.getValue().getX());
                jsonObject.put("y", entry.getValue().getY());
                jsonObject.put("typeName", entry.getValue().getType().toString());
                jsonList.add(jsonObject);

                
            }
        }
        jsonTransfer.put("cells", jsonList);

        return jsonTransfer.toString();
    }
}
