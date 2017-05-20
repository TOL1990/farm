package com.test.buffer;

import com.aad.myutil.logger.MYLoggerFactory;
import com.test.controller.COMMAND_FAMILY;
import com.test.controller.FARM_COMMAND;
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
                    cellToEmpty(playerId, json);
                    break;
                }
                case SET_BUILDING:
                {
                    setUpBuilding(playerId, json);
                    break;
                }
                case DELETE_BUILDING:
                {
                    cellToEmpty(playerId, json);
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

    private void cellToEmpty(long playerId, JSONObject json)
    {
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        Field field = FieldManager.INSTANCE.getFieldByUserId(playerId);
        field.setCell(new EmptyCell(x, y), x, y);
        farmStatus(playerId);
    }

    private void setUpPlant(long playerId, JSONObject json)
    {
        String plantName = json.get("name").toString();
        int x = Integer.parseInt(json.get("x").toString());
        int y = Integer.parseInt(json.get("y").toString());
        PlantConstuct construct = new PlantConstuct(plantName, playerId, x, y);
        if (!construct.run())
            sendError(construct.getError(), playerId);
        else
            farmStatus(playerId);
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
//Вроде н едолжжен вызываться
    private void getAvaliablePlants(long playerId)
    {
        
//        List<Plant> plants = ConstCollections.availablePlants;
//        String responseMsg = plantsListToJSON(plants);
//        JSONObject response = new JSONObject();
//        response.put(KEYS.MODEL_DATA.getKey(), responseMsg);
//        sendData(playerId, FARM_COMMAND.GET_AVAILABLE_PLANTS, response);
//    }
//
//    private String plantsListToJSON(List<Plant> plants)
//    {
//        JSONArray jsonList = new JSONArray();
//        for (Plant plant : plants)
//        {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("typeName", plant.getType().toString());
//            jsonObject.put("name", plant.getName());
//            jsonObject.put("growTime", plant.getGrowTime());
//            jsonObject.put("proseed", plant.getProceed());
//            jsonObject.put("price", plant.getPrice());
//            jsonList.add(jsonObject);
//        }
//        return jsonList.toString();
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
        System.out.println( responseMsg);
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
                        tempobj.proseed = plantTemp.getProceed();
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
    
}