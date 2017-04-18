package model.service;

import model.core.BuildingConstruct;
import model.core.PickingPlant;
import model.core.PlantConstuct;
import model.dao.util.FactoryDao;
import model.entity.Building;
import model.entity.Field;
import model.entity.Plant;
import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 13.03.2017.
 * сервис в которые можно кидать команды для работы с игрой инициализируется
 * корректным юзером после авторизации
 */
public class GameService {

    //мы будем обращаться к этим полям, а они будут перезаписываться сервисами
    private Player player;
    private Field field;


    //Слои для работы с дао
    private PlayerService playerService;
    private FieldService fieldService;


    public GameService() {
        playerService = new PlayerService();
        fieldService = new FieldService();
    }

    public GameService(Player player) {
        //todo неюзать нигде пока
        playerService = new PlayerService();
        fieldService = new FieldService();

        this.player = player;

        if (this.field == null) {
            field = FactoryDao.getInstance().getFieldDao().getFieldId(player); // проверить логику что и нахера возвращает
        }
    }

    public void addNewPlayer(String nickName, String password) {
        playerService.addPlayer(nickName, password);

        Player player = playerService.getPlayerByNick(nickName);
        fieldService.addField(player);//добавить поле клиенту с ником nickName

    }

    /**
     * вывести ферму на екран
     */
    public String soutFarm() {
//        cleanConsole();
        Field field = fieldService.getFieldDao().getField();
        return field.getConsoleSoutField();
    }

    /**
     *
     */
    public List<Plant> getAllPlants() {return fieldService.getAllPlants();}

    public List<Building> getAllBuildings() {return fieldService.getAllBuildings();}

    private void cleanConsole() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }

    public void setPlant(String plantName, String x, String y) {
        Field field = fieldService.getField(); //получаем поле в кеше

        Plant plant = fieldService.getPlantByName(plantName);//тянем растение по нику из дао(кеш/БД)

        PlantConstuct constuct = new PlantConstuct(plant, field, Integer.parseInt(x), Integer.parseInt(y));
        if (constuct.run())//если успешно прошла операция в кеше, обновляем базу
        {
            fieldService.updateFieldCell(field.getId(), Integer.parseInt(x), Integer.parseInt(y));
            updatePlayerBallance(field.getPlayer());
        } else {
            System.out.println("не удалось посадить растение. setPlant");
        }
    }

    public void pickupPlant(String x, String y) {

        Field field = fieldService.getField();

        PickingPlant pick = new PickingPlant(field,Integer.parseInt(x), Integer.parseInt(y));

        if(pick.run())
        {
            fieldService.updateFieldCell(field.getId(), Integer.parseInt(x), Integer.parseInt(y));
            updatePlayerBallance(field.getPlayer());
        }else {
            System.out.println("не удалось собрать урожай. pickupPlant");
        }
    }

    public void delPlant(String x, String y) {
        Field field = fieldService.getField();
        fieldService.setEmptyCell(field, Integer.parseInt(x), Integer.parseInt(y) );
    }

    public void delBuilding(String x, String y) {
        Field field = fieldService.getField();
        fieldService.setEmptyCell(field, Integer.parseInt(x), Integer.parseInt(y) );
    }

    public void setBuilding(String buildingName, String x, String y) {
        Field field = fieldService.getField();
        Building building = fieldService.getBuildingByName(buildingName);

        BuildingConstruct construct = new BuildingConstruct(building, field, Integer.parseInt(x), Integer.parseInt(y));
        if (construct.run()) {
            fieldService.updateFieldCell(field.getId(), Integer.parseInt(x), Integer.parseInt(y));
            updatePlayerBallance(field.getPlayer());
        } else {
            System.out.println("не удалось построить здание. setBuilding");
        }

    }

    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {

        if (player.getId() == 0 && player.getNick() != null && player.getNick() != "")
            setPlayerByNick(player.getNick());

    }

    public void setPlayerByNick(String nick) {
        this.player = playerService.getPlayerByNick(nick);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void getField(Player player) {
        fieldService.getFieldDao().getField(player);
    }

    public void initial() {
        if (player != null) {
            field = fieldService.getFieldDao().getField(player);
        }
    }

    public void updatePlayerBallance(Player player) {
        playerService.updatePlayerBallance(player);
    }
}
