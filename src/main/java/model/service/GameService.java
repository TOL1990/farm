package model.service;

import model.dao.util.FactoryDao;
import model.entity.Field;
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
        StringBuilder stringBuilder = new StringBuilder();
        Field field = fieldService.getFieldDao().getField();

        return field.getConsoleSoutField();
    }

    /**
     *
     */
    public String getAvaliablePlants() {
        //todo реализовать цепочку

        return null;
    }

    public String getAvaliableBuildings() {
        //todo реализовать цепочку
        return null;
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
    public void getField(Player player) {
        fieldService.getFieldDao().getField(player)
    }

    public void setField(Field field) {
        this.field = field;
    }


    public void initial() {
        if(player != null)
        {
            field = fieldService.getFieldDao().getField(player);
        }
    }


}
