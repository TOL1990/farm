package model.service;

import model.core.BuildingConstruct;
import model.core.PickingPlant;
import model.core.PlantConstuct;
import model.dao.util.FactoryDao;
import model.entity.*;
import model.service.propertyconfig.AreaService;

import java.util.List;

/**
 * Created by Taras on 13.03.2017.
 * сервис в которые можно кидать команды для работы с игрой инициализируется
 * корректным юзером после авторизации
 */
public class GameService
{

    //мы будем обращаться к этим полям, а они будут перезаписываться сервисами
    private Player player;
    private Field field;
    private Area homeArea;


    //Слои для работы с дао
    private PlayerService playerService;
    private FieldService fieldService;
    private AreaService areaService;


    public GameService()
    {
        playerService = new PlayerService();
        fieldService = new FieldService();
        areaService = new AreaService();
    }

    public GameService(Player player)
    {
        //todo неюзать нигде пока
        playerService = new PlayerService();
        fieldService = new FieldService();

        this.player = player;

        if (this.field == null)
        {
            field = FactoryDao.getInstance().getFieldDao().getFieldId(player); // проверить логику что и нахера возвращает
        }
    }

    public void addNewPlayer(String nickName, String password)
    {
        playerService.addPlayer(nickName, password);

        Player player = playerService.getPlayerByNick(nickName);
        fieldService.addField(player);//добавить поле клиенту с ником nickName

    }

    /**
     * вывести ферму на екран
     */
    public String soutFarm()
    {
//        cleanConsole();
        Field field = fieldService.getFieldDao().getField();
        return field.getConsoleSoutField();
    }

    public List<Plant> getAllPlants()
    {
        return fieldService.getAllPlants();
    }

    public List<Building> getAllBuildings()
    {
        return fieldService.getAllBuildings();
    }

    public String setPlant(String plantName, String x, String y)
    {
        Field field = fieldService.getField(); //получаем поле в кеше

        Plant plant = fieldService.getPlantByName(plantName);//тянем растение по нику из дао(кеш/БД)

        PlantConstuct construct = new PlantConstuct(plant, field, Integer.parseInt(x), Integer.parseInt(y));
        if (construct.run())//если успешно прошла операция в кеше, обновляем базу
        {
            fieldService.updateFieldCell(field.getId(), Integer.parseInt(x), Integer.parseInt(y));
            return "";
        }
        else
        {
            return construct.getError();
        }
    }

    public String pickupPlant(String x, String y)
    {

        Field field = fieldService.getField();

        PickingPlant pick = new PickingPlant(field, Integer.parseInt(x), Integer.parseInt(y));

        if (pick.run())
        {
            fieldService.updateFieldCell(field.getId(), Integer.parseInt(x), Integer.parseInt(y));
            updatePlayerBallance(field.getPlayer());
            return "";
        }
        else
        {
            System.out.println("не удалось собрать урожай. pickupPlant");
            return "не удалось собрать урожай. pickupPlant";
        }
    }

    public void delPlant(String x, String y)
    {
        Field field = fieldService.getField();
        fieldService.setEmptyCell(field, Integer.parseInt(x), Integer.parseInt(y));
    }

    public void delBuilding(String x, String y)
    {
        Field field = fieldService.getField();
        fieldService.setEmptyCell(field, Integer.parseInt(x), Integer.parseInt(y));
    }

    public String setBuilding(String buildingName, String x, String y)
    {
        Field field = fieldService.getField();
        Building building = fieldService.getBuildingByName(buildingName);

        BuildingConstruct construct = new BuildingConstruct(building, field, Integer.parseInt(x), Integer.parseInt(y));
        if (construct.run())
        {
            fieldService.updateFieldCell(field.getId(), Integer.parseInt(x), Integer.parseInt(y));
            updatePlayerBallance(field.getPlayer());
            return "";
        }
        else
        {
            return construct.getError();
        }

    }

    public List<Player> getAllPlayers()
    {
        return playerService.getAllPlayers();
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        if (player.getId() == 0 && player.getNick() != null && player.getNick() != "")
        {
            setPlayerByNick(player.getNick());
        }
    }

    public void setPlayerByNick(String nick)
    {
        this.player = playerService.getPlayerByNick(nick);
    }

    public Field getField()
    {
        if (field == null && player.getNick() != null)
        {
            this.field = getField(player);
        }
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }

    public Field getField(Player player)
    {
        field = fieldService.getFieldDao().getField(player);
        return field;
    }

    public void initial()
    {
        if (player != null)
        {
            field = fieldService.getFieldDao().getField(player);
        }
    }

    public void updatePlayerBallance(Player player)
    {
        playerService.updatePlayerBallance(player);
    }

    public Area getArea()
    {
        if (homeArea == null && field != null)
        {
            homeArea = getArea(field);
        }
        return homeArea;
    }

    private Area getArea(Field field)
    {
        if (field == null)
        {
            getField();
        }
        homeArea = areaService.getArea(field);
        return homeArea;
    }

    //можно получить область либо по ид либо по координатам на карте мира
    public Area getArea(Area area)
    {
        return areaService.getArea(area);
    }

    public Player getPlayerByNick(String nick)
    {
        return playerService.getPlayerByNick(nick);
    }

}
