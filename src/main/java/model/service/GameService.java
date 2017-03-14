package model.service;

import model.dao.util.FactoryDao;
import model.entity.Field;
import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 13.03.2017.
 * сервис в которые можно кидать команды для работы с игрой инициализируется
 * корректным юзером полсе авторизации
 */
public class GameService {

    //мы будем обращаться к этим полям, а они будут перезаписываться сервисами
    private Player player;
    private Field field;

    private PlayerService playerService;
    private FieldService fieldService;


    public GameService() {
        playerService = new PlayerService();
        fieldService = new FieldService();
    }

    public GameService(Player player) {
        this.player = player;

        if (this.field == null) {
            field = FactoryDao.getInstance().getFieldDao().getField(player);
        }
        playerService = new PlayerService();
        fieldService = new FieldService();
    }

    public void addNewPlayer(String nickName, String password) {
        playerService.addPlayer(nickName, password);

    }

    /**
     * вывести ферму на екран
     */
    public String soutFarm() {
        cleanConsole();
        return null;
    }

    /**
     *
     */
    public String getAvaliablePlants() {
        return null;
    }

    public String getAvaliableBuildings() {
        return null;
    }


    public void addNewPlayer() {
    }

    private void cleanConsole() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }

    public void setPlant(String plantName, String x, String y) {
    }

    public void pickupPlant(String x, String y) {
    }

    public void delPlant(String x, String y) {
    }

    public void delBuilding(String x, String y) {
    }

    public void setBuilding(String buildingName, String x, String y) {
    }

    public List<Player> getAllPlayers() {
        return  playerService.getAllPlayers();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
