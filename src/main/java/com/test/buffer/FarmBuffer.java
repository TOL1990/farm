package com.test.buffer;

import com.aad.myutil.logger.MYLoggerFactory;
import com.test.controller.COMMAND_FAMILY;
import com.test.controller.FARM_COMMAND;
import com.test.core.ConstCollections;
import com.test.core.command.BuildingConstruct;
import com.test.core.command.PickingPlant;
import com.test.core.command.PlantConstuct;
import com.test.field.controller.FieldManager;
import com.test.field.entity.*;
import com.test.player.conlroller.PlayerManager;
import com.test.util.KEYS;
import com.test.util.TransferCellInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Taras on 18.04.2017.
 */
public class FarmBuffer extends AbstractBuffer<FARM_COMMAND>
{
    public FarmBuffer()
    {
        super(COMMAND_FAMILY.FARM);
//        loginEvent();
//        reconnectEvent();
//        registrationEvent();

    }

    @Override
    public void executeCommand(long playerId, FARM_COMMAND command, Object o)
    {
        try
        {
            JSONObject json = (JSONObject) o;
            switch (command)
            {
                case FARM_STATUS:
                {
                    farmStatus(playerId);
                    break;
                }
                case GET_AVAILABLE_PLANTS:
                {
                    getAvaliablePlants(playerId);
                    break;
                }
                case PICK_UP_PLANT:
                {
                    pickUpPlant(playerId, json);
                    break;
                }
                case SET_PLANT:
                {
                    setUpPlant(playerId, json);
                    break;
                }
                case DELETE_PLANT:
                {
                    deletePlant(playerId, json);
                    break;
                }
                case SET_BUILDING:
                {
                    setUpBuilding(playerId, json);
                    break;
                }
                case DELETE_BUILDING:
                {
                    deleteBuilding(playerId, json);
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

    private void deleteBuilding(long playerId, JSONObject json)
    {
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        FieldManager.INSTANCE.updateCell(playerId,new EmptyCell(x,y));
        farmStatus(playerId);
    }

    private void setUpBuilding(long playerId, JSONObject json)
    {
        String buildingName = json.get("name").toString();
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        BuildingConstruct construct = new BuildingConstruct(buildingName, playerId, x, y);
        if (!construct.run())
            sendError(construct.getError(), playerId);
        else
            farmStatus(playerId);
    }

    private void deletePlant(long playerId, JSONObject json)
    {
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        FieldManager.INSTANCE.updateCell(playerId,new EmptyCell(x,y));
        farmStatus(playerId);
    }

    private void setUpPlant(long playerId, JSONObject json)
    {
        String plantName = json.get("name").toString();
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        PlantConstuct construct = new PlantConstuct(plantName, playerId, x, y);
        if (!construct.run())
        {
            sendError(construct.getError(), playerId);
        }
        else
        {
            farmStatus(playerId);
        }
    }

    private void pickUpPlant(long playerId, JSONObject json)
    {
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());

        PickingPlant pick = new PickingPlant(playerId, x, y);
        if (!pick.run())
        {
            System.out.println("Не удалось собрать расстение");
            sendError("Can't pickUp Plant", playerId);
        }
        else
        {
            farmStatus(playerId);
        }
    }

    private void getAvaliablePlants(long playerId)
    {
        List<Plant> plants = ConstCollections.avaliablePlants;
        String responseMsg = plantsListToJSON(plants);
        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
        sendData(playerId, FARM_COMMAND.GET_AVAILABLE_PLANTS, response);
    }

    private String plantsListToJSON(List<Plant> plants)
    {
        JSONArray jsonList = new JSONArray();
        for (Plant plant : plants)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeName", plant.getType().toString());
            jsonObject.put("name", plant.getName());
            jsonObject.put("growTime", plant.getGrowTime());
            jsonObject.put("proseed", plant.getProseed());
            jsonObject.put("price", plant.getPrice());
            jsonList.add(jsonObject);
        }
        return jsonList.toString();
    }


    @Override
    public FARM_COMMAND getCommand(Object o)
    {
        int commandId = (Integer) o;
        return FARM_COMMAND.valueOf(commandId);
    }

    private void farmStatus(long playerId)
    {
        Field field = FieldManager.INSTANCE.getFieldByUserId(playerId);
        List<TransferCellInfo> cells = farmToTransferList(field);
        String responseMsg = cellsToJSON(cells);

        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
        sendData(playerId, FARM_COMMAND.FARM_STATUS, response);
        sendBalance(playerId);
    }

    private List<TransferCellInfo> farmToTransferList(Field field)
    {
        List<TransferCellInfo> resultList = new ArrayList<>();
        TransferCellInfo tempobj = null;

        for (Map.Entry<Integer, Map<Integer, Cell>> entryRows : field.getCells().entrySet())
        {
            for (Map.Entry<Integer, Cell> entry : entryRows.getValue().entrySet())
            {
                Cell cell = entry.getValue();
                tempobj = new TransferCellInfo();
                tempobj.x = cell.getXPosition();
                tempobj.y = cell.getYPosition();
                tempobj.type = cell.getType();
                switch (tempobj.type)
                {
                    case PLANT:
                    {
                        Plant plantTemp = (Plant) cell;
                        tempobj.planted = plantTemp.getPlantedTime();
                        tempobj.name = plantTemp.getName();
                        tempobj.proseed = plantTemp.getProseed();
                        tempobj.growTime = plantTemp.getGrowTime();
                        break;
                    }
                    case BUILDING:
                    {
                        Building buildingTemp = (Building) cell;
                        tempobj.name = buildingTemp.getName();
                        tempobj.proseedBonus = buildingTemp.getBonus().getProseed();
                        tempobj.timeBonus = buildingTemp.getBonus().getTime();
                        break;
                    }
                    case ERROR:
                    {
                        System.out.println("иди нах!!!!!");
                        break;
                    }
                }
                resultList.add(tempobj);
            }
        }
        return resultList;
    }

    private String cellsToJSON(List<TransferCellInfo> cells)
    {
        JSONArray jsonList = new JSONArray();

        for (TransferCellInfo cell : cells)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", cell.x);
            jsonObject.put("y", cell.y);
            jsonObject.put("typeName", cell.type.toString());
            jsonObject.put("name", cell.name);
            jsonObject.put("planted", cell.planted);
            jsonObject.put("proseedBonus", cell.proseedBonus);
            jsonObject.put("timeBonus", cell.timeBonus);
            jsonObject.put("proseed", cell.proseed);
            jsonObject.put("growTime", cell.growTime);
            jsonList.add(jsonObject);
            //  jsonList.add(JSONHelper.objectToJSON(cell));
        }
        return jsonList.toString();
    }

    private void sendBalance(long playerId)
    {
        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), PlayerManager.INSTANCE.getPlayer(playerId).getBalance());
        sendData(playerId, FARM_COMMAND.MONEY_BALANCE, response);
    }

    private void sendError(String errorMessage, long playerId)
    {
        JSONObject response = new JSONObject();
        response.put(KEYS.MODEL_DATA.getKey(), errorMessage);
        sendData(playerId, FARM_COMMAND.FARM_ERROR, response);
    }


// Старая Реализация
//    private void deletePlant(long userId, JSONObject json)
//    {
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        String x = json.get("x").toString();
//        String y = json.get("y").toString();
//        gameService.delPlant(x, y);
//        updateFarm(gameService, userId);
//    }
//
//    private void deleteBuilding(long userId, JSONObject json)
//    {
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        String x = json.get("x").toString();
//        String y = json.get("y").toString();
//        gameService.delBuilding(x, y);
//        updateFarm(gameService, userId);
//    }
//
//    private void setUpPlant(long userId, JSONObject json)
//    {
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        String plantName = json.get("name").toString();
//        String x = json.get("x").toString();
//        String y = json.get("y").toString();
//        String answer = gameService.setPlant(plantName, x, y);
//        if (answer.equals(""))
//        {
//            updateFarm(gameService, userId);
//        }
//        else
//        {
//            sendError(answer, userId);
//        }
//    }
//
//    private void setUpBuilding(long userId, JSONObject json)
//    {
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        String buildingName = json.get("name").toString();
//        String x = json.get("x").toString();
//        String y = json.get("y").toString();
//        String answer = gameService.setBuilding(buildingName, x, y);
//        if (answer.equals(""))
//        {
//            updateFarm(gameService, userId);
//        }
//        else
//        {
//            sendError(answer, userId);
//        }
//    }
//
//    private void otvetkaMeth(long userId, JSONObject json)
//    {
//        //    sendData(userId, LOGIN_COMMAND.LOGIN_SUCCESS, json);
//
//        String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
///*
//        System.out.println("OTVETKA USER IDDDDDDDDDDDDDDD = " + userId);
//        msg += "11111 ";
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), msg);
//        sendData(userId, LOGIN_COMMAND.OTVETKA, response);
//*/
//        //  farmStatus(userId);
//        getFarm(userId);
//    }
//
//
////   
//
//    //////////////Самописные команды для фермы. их стоит перенести в отдельный клас\\\\\\\\\\\\\\\\\\\\\
//    @Override
//    public FARM_COMMAND getCommand(Object o)
//    {
//        int commandId = (Integer) o;
//        return FARM_COMMAND.valueOf(commandId);
//    }
//
//    private void farmStatus(long userId)
//    {
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        // gameService.setPlayer(); //забиваем игрока из базы руками
//        List<TransferCellInfo> cells = farmToTransferList(gameService.getField(gameService.getPlayer()));//тут тащим из базы
//        // List<TransferCellInfo> cells = makeRandomField();
//        String responseMsg = celltoJSONArray(cells);
//
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);  // makeRandomJSONField());    //responseMsg);
//        sendData(userId, FARM_COMMAND.FARM_STATUS, response);
//
//    }
//
//    private void getFarm(long userId)
//    {
//        // String msg = JSONHelper.getString(json.get(KEYS.MODEL_DATA.getKey()));
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        gameService.setPlayer(new Player("Q", "11")); //забиваем игрока из базы руками
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
//        sendData(userId, FARM_COMMAND.FARM_STATUS, response);
//    }
//
//    private void pickUpPlant(long userId, JSONObject json)
//    {
//        GameService gameService = GameManager.INSTANCE.getGameService(userId);
//        String answer = gameService.pickupPlant(json.get("x").toString(), json.get("y").toString()); //вернет "" если все ок
//
//        if (answer.equals(""))
//        {
//            updateFarm(gameService, userId);
//        }
//        else
//        {
//            sendError(answer, userId);
//        }
//    }
//
//    private void getAvaliablePlants(long userId)
//    {
//        JSONObject response = new JSONObject();
//        String responseMsg = "ALLPlantsAvaliable";
//        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
//        sendData(userId, FARM_COMMAND.GET_AVAILABLE_PLANTS, response);
//    }
//
//    private List<TransferCellInfo> farmToTransferList(Field field)
//    {
//        List<TransferCellInfo> resultList = new ArrayList<>();
//        TransferCellInfo tempobj = null;
//
//
//        for (Map.Entry<Integer, Map<Integer, Cell>> entryRows : field.getCells().entrySet())
//        {
//            for (Map.Entry<Integer, Cell> entry : entryRows.getValue().entrySet())
//            {
//                Cell cell = entry.getValue();
//                tempobj = new TransferCellInfo();
//                tempobj.x = cell.getXPosition();
//                tempobj.y = cell.getYPosition();
//                tempobj.type = cell.getType();
//                switch (tempobj.type)
//                {
//                    case PLANT:
//                    {
//                        Plant plantTemp = (Plant) cell;
//                        tempobj.planted = plantTemp.getPlantedTime();
//                        tempobj.name = plantTemp.getName();
//                        tempobj.proseed = plantTemp.getProseed();
//                        tempobj.growTime = plantTemp.getGrowTime();
//                        break;
//                    }
//                    case BUILDING:
//                    {
//                        Building buildingTemp = (Building) cell;
//                        tempobj.name = buildingTemp.getName();
//                        tempobj.proseedBonus = buildingTemp.getBonus().getProseed();
//                        tempobj.timeBonus = buildingTemp.getBonus().getTime();
//                        break;
//                    }
//                    case ERROR:
//                    {
//                        System.out.println("иди нах!!!!!");
//                        break;
//                    }
//                }
//                resultList.add(tempobj);
//            }
//        }
////        for (Cell cell : field.getCells())
////        {
////            tempobj = new TransferCellInfo();
////            tempobj.x = cell.getXPosition();
////            tempobj.y = cell.getYPosition();
////            tempobj.type = cell.getType();
////            switch (tempobj.type)
////            {
////                case PLANT:
////                {
////                    Plant plantTemp = (Plant) cell;
////                    tempobj.planted = plantTemp.getPlantedTime();
////                    tempobj.name = plantTemp.getName();
////                    tempobj.proseed = plantTemp.getProseed();
////                    tempobj.growTime = plantTemp.getGrowTime();
////                    break;
////                }
////                case BUILDING:
////                {
////                    Building buildingTemp = (Building) cell;
////                    tempobj.name = buildingTemp.getName();
////                    tempobj.proseedBonus = buildingTemp.getBonus().getProseed();
////                    tempobj.timeBonus = buildingTemp.getBonus().getTime();
////                    break;
////                }
////                case ERROR:
////                {
////                    System.out.println("иди нах!!!!!");
////                    break;
////                }
////            }
////            resultList.add(tempobj);
////        }
//        return resultList;
//    }
//
//    private void updateFarm(GameService gameService, long userId)
//    {
//        JSONObject response = new JSONObject();
//        List<TransferCellInfo> cells = farmToTransferList(gameService.getField(gameService.getPlayer()));//тут тащим из базы
//        String responseMsg = celltoJSONArray(cells);
//        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
//        sendData(userId, FARM_COMMAND.FARM_STATUS, response);
//        sendBalance(gameService, userId);
//    }
//
//    private String celltoJSONArray(List<TransferCellInfo> cells)
//    {
//        JSONArray jsonList = new JSONArray();
//
//        for (TransferCellInfo cell : cells)
//        {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("x", cell.x);
//            jsonObject.put("y", cell.y);
//            jsonObject.put("typeName", cell.type.toString());
//            jsonObject.put("name", cell.name);
//            jsonObject.put("planted", cell.planted);
//            jsonObject.put("proseedBonus", cell.proseedBonus);
//            jsonObject.put("timeBonus", cell.timeBonus);
//            jsonObject.put("proseed", cell.proseed);
//            jsonObject.put("growTime", cell.growTime);
//            jsonList.add(jsonObject);
//            //  jsonList.add(JSONHelper.objectToJSON(cell));
//        }
//        return jsonList.toString();
//    }
//
//    private String makeRandomJSONField()
//    {
//        Random random = new Random();
//        String[] types = {
//                "EMPTY",
//                "Plant",
//                "Building"
//        };
//        String[] plants = {
//                "cabbage",
//                "carrot",
//                "wheet"
//        };
//        String[] buildings = {
//                "reactor",
//                "greenhouse",
//                "fertilizer"
//        };
//        int type, plant, building;
//
//        JSONArray jsonList = new JSONArray();
//        for (int i = 1; i < 9; i++)
//        {
//            for (int j = 1; j < 9; j++)
//            {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("x", i);
//                jsonObject.put("y", j);
//
//                type = random.nextInt(3);
//                if (type == 0)
//                {
//                    jsonObject.put("typeName", types[0]);
//                }
//                else if (type == 1)
//                {
//                    jsonObject.put("typeName", types[1]);
//                    jsonObject.put("planted", "10.10.2017 01:00:00");
//                    plant = random.nextInt(3);
//                    switch (plant)
//                    {
//                        case 0:
//                        {
//                            jsonObject.put("name", plants[0]);
//                            break;
//                        }
//                        case 1:
//                        {
//                            jsonObject.put("name", plants[1]);
//                            break;
//                        }
//                        case 2:
//                        {
//                            jsonObject.put("name", plants[2]);
//                            break;
//                        }
//                    }
//                }
//                else if (type == 2)
//                {
//                    jsonObject.put("typeName", types[2]);
//                    jsonObject.put("proseedBonus", 350);
//                    building = random.nextInt(3);
//                    switch (building)
//                    {
//                        case 0:
//                        {
//                            jsonObject.put("name", buildings[0]);
//                            break;
//                        }
//                        case 1:
//                        {
//                            jsonObject.put("name", buildings[1]);
//                            break;
//                        }
//                        case 2:
//                        {
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
//
//    private void sendBalance(GameService gameService, long userId)
//    {
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), gameService.getPlayer().getBalance());
//        sendData(userId, FARM_COMMAND.MONEY_BALANCE, response);
//    }
//
//    private void sendError(String errorMessage, long userId)
//    {
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), errorMessage);
//        sendData(userId, FARM_COMMAND.FARM_ERROR, response);
//    }
}