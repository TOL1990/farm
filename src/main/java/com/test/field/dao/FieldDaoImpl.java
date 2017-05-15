package com.test.field.dao;

import com.test.field.entity.*;
import com.test.player.entity.Player;
import com.test.util.DaoUtils;
import com.test.util.propertyconfig.QueryConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Taras on 13.03.2017.
 */
public class FieldDaoImpl //implements FieldDao
{
    private Map<Long, Field> fields;//<field Id, Field>
    private Map<Long, Long> fieldByUserId;//<UserId, FieldId>
    
    private Field fieldCash; //тут будет лежать поле из бд

    public FieldDaoImpl()
    {
        fieldCash = new Field();

        //Обновляем листы доступных растений и строений из бд. Или здесь же добавиьт из вне
        fieldCash.setAllPlants(getAllPlantsDB());
        fieldCash.setAllBuildings(getAllBuildingsDB());

    }

    public Field getFieldId(long id)
    {
        return getFieldId(new Player(id)); //это обновит кеш
    }

    /**
     * @return ферму из кеша. Если кеш пустой из БД по юзеру и обновляет кеш
     */
    public Field getFieldId(Player player)
    {

        if (fieldCash.getId() == 0L)
        {
            getFieldIdDB(player);// обновляем кеш
        }
        return fieldCash;
    }

    public Field getField()
    {
        //нахрена пока не понятно
        if (fieldCash == null)
        {
            System.out.println("Получить поле не получилось, оно null");
        }
        return fieldCash;
    }

    public Field getFieldById(long fieldId)
    {
        
        return null;
    }

    public void setField(Field fieldCash)
    {
        this.fieldCash = fieldCash;
    }

    public Field getField(Player player)
    {
        getFieldIdDB(player); // это обновит кеш ид fieldCash из базы
        fieldCash.setCells(getCellsByFieldId(fieldCash.getId()));
        fieldCash.setPlayer(player);
        getAllPlants();
        return fieldCash;
    }

    private Field getFieldIdDB(Player player)
    {

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long fieldId = 0;
        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_FIELD_BY_USER_ID);
            preparedStatement.setLong(1, player.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                fieldId = resultSet.getLong("id_field"); // получаем ID фермы, заданного юзера
            }
            if (fieldId == 0)
            {
                System.out.println("Не получилось получить поле по ид игрока");
            }

            fieldCash.setId(fieldId);//назначаем в кеш ид фермы

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(connection, preparedStatement, resultSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return fieldCash;
    }

    public void addField(Player player)
    {
        //todo создать новое поле и заполнить его пустыми клеточками, написать запрос
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;

        if (player == null || player.getId() == 0l)
        {
            System.out.println("Не получается добавить поле по пустому юзеру");
        }

        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.ADD_FIELD_BY_USER_ID);
            preparedStatement.setLong(1, player.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                connection.close();
                DaoUtils.close(preparedStatement);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        getFieldId(player.getId());//обновить кеш //todo переделать по человечи

        addEmptyCells(fieldCash);

        //создать новое поле в бд для игрока с ником nickName

    }

    public List<Building> getAllBuildings()
    {
        if (fieldCash.getAllBuildings() == null)
        {
            fieldCash.setAllBuildings(getAllBuildingsDB()); // тащим из базы
        }
        return fieldCash.getAllBuildings();
    }

    public List<Building> getAllBuildingsDB()
    {

        Connection connection = DaoUtils.getConnection();
        List<Building> buildings = new ArrayList<Building>();


        Statement statement = null;
        ResultSet rs = null;
        try
        {
            statement = connection.createStatement();
            rs = statement.executeQuery(QueryConfig.GET_ALL_BUILDINGS);

            while (rs.next())
            {
                int id = rs.getInt("id_building");
                String buildingName = rs.getString("building_name");
                long price = rs.getLong("price");

                int proseed = rs.getInt("proseed");
                int time = rs.getInt("time");
                String bonusName = rs.getString("bonus_name");
                long bonusId = rs.getLong("id_bonus");

                buildings.add(new Building(id, buildingName, new BuildingBonus(bonusId, bonusName, time, proseed), price));
            }
        }
        catch (SQLException e)
        {
            System.err.println("Can't get buildings from DB");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(connection, statement, rs);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return buildings;
    }

    public Building getBuildingByName(String name)
    {
        if (fieldCash.getAllBuildings() == null)
        {
            getAllBuildings(); //обновим кеш
        }

        for (Building b : fieldCash.getAllBuildings())
        {
            if (b.getName().equals(name.toLowerCase()))
            {
                return b;
            }
        }
        return null;
    }

    public List<Plant> getAllPlants()
    {
        if (fieldCash.getAllPlants() == null)
        {
            fieldCash.setAllPlants(getAllPlantsDB()); // тащим из базы
        }
        return fieldCash.getAllPlants();
    }

    public List<Plant> getAllPlantsDB()
    {
//
//        Connection connection = DaoUtils.getConnection();
        List<Plant> plants = new ArrayList<Plant>();
//
//
//        Statement statement = null;
//        ResultSet rs = null;
//        try
//        {
//            statement = connection.createStatement();
//            rs = statement.executeQuery(QueryConfig.GET_ALL_PLANTS);
//
//            while (rs.next())
//            {
//                long id = rs.getLong("id_plant");
//                int growTime = rs.getInt("grow_time");
//                String name = rs.getString("name");
//                long price = rs.getLong("price");
//                long proseed = rs.getLong("proseed");
//                plants.add(new Plant(id, name, price, proseed, growTime));
//            }
//        }
//        catch (SQLException e)
//        {
//            System.err.println("Can't get plants from DB");
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                DaoUtils.close(connection, statement, rs);
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
        return plants;
    }

    public Plant getPlantByName(String name)
    {
        if (fieldCash.getAllPlants() == null)
        {
            getAllPlants(); //обновим кеш
        }

        for (Plant p : fieldCash.getAllPlants())
        {
            if (p.getName().equals(name.toLowerCase()))
            {
                return p;
            }
        }
        return null;
    }

    public void updateCell(long fieldId, int x, int y)
    {
        Connection con = DaoUtils.getConnection();
        Cell cell = fieldCash.getCell(x, y);

        PreparedStatement statement = null;
        try
        {
            statement = con.prepareStatement(QueryConfig.UPDATE_CELL);
            long time = 0;
            int id = 0;
            switch (cell.getType())
            {
                case EMPTY:
                {
                    time = System.currentTimeMillis();
                    break;
                }
                case BUILDING:
                {
                    Building building = (Building) cell;
                    time = System.currentTimeMillis();
                    id = (int) building.getId();
                    break;
                }
                case PLANT:
                {
                    Plant plant = (Plant) cell;
                    time = plant.getPlantedTime();
                    id = (int) plant.getId();
                    break;
                }
            }
//            if (cell.getType() == CELL_TYPE.EMPTY)
//            {
//                EmptyCell empty = (EmptyCell) cell;
//                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//                statement.setLong(2, 1);//1.EMPTY 2.Plant 3.Building
//                statement.setInt(3, 1);// type_id - неважно т.к. клетка пуста
//            }
//            if (cell.getType() == CELL_TYPE.BUILDING)
//            {
//                Building building = (Building) cell;
//                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//                statement.setLong(2, 3);//1.EMPTY 2.Plant 3.Building
//                statement.setInt(3, (int) building.getId());
//            }
//            if (cell.getType() == CELL_TYPE.PLANT)
//            {
//                Plant plant = (Plant) cell;
//                statement.setTimestamp(1, plant.getPlantedTime());
//                statement.setLong(2, 2);//1.EMPTY 2.Plant 3.Building
//                statement.setInt(3, (int) plant.getId());
//            }

            statement.setTimestamp(1, new Timestamp(time));
            statement.setLong(2, cell.getType().getId());//1.EMPTY 2.Plant 3.Building
            statement.setInt(3, id);

            statement.setInt(4, x);
            statement.setInt(5, y);
            statement.setLong(6, fieldId);
            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(con, statement);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Возвращет лист клеток по ид поля из БД
     */
    public Map<Integer, Map<Integer, Cell>> getCellsByFieldId(long id)
    {
        List<Cell> cells = new ArrayList<Cell>();
        Field field = new Field();

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_CELLS_BY_FIELD_ID);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int x = resultSet.getInt("pos_x");
                int y = resultSet.getInt("pos_y");
                int typeId = resultSet.getInt("type_id"); // ид конкретного растения или здания

                String typeName = resultSet.getString("name");
                Timestamp plantedDate = resultSet.getTimestamp("planted");

                if (typeName.equals(CELL_TYPE.EMPTY.toString()))
                {
                    //   cells.add(new EmptyCell(x, y));
                    field.setCell(new EmptyCell(x, y), x, y);
                }
                if (typeName.equals(CELL_TYPE.PLANT.toString()))
                {
                    Plant plantInfo = getPlantFromCash(typeId);
                    Plant plant = new Plant((long) typeId, plantInfo.getName(), plantInfo.getPrice(), plantInfo.getProseed(), plantInfo.getGrowTime(), plantedDate.getTime(), x, y);
                    //   cells.add(plant);
                    field.setCell(plant, x, y);
                }
                if (typeName.equals(CELL_TYPE.BUILDING.toString()))
                {
                    Building buildInfo = getBuildingFromCash(typeId);
                    Building building = new Building((long) typeId, buildInfo.getName(), buildInfo.getBonus(), buildInfo.getPrice(), x, y);
//                    cells.add(building);
                    field.setCell(building, x, y);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(connection, preparedStatement, resultSet);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
     //   initBonuces(field); //todo починить бонусы

        return field.getCells();
    }

    private Plant getPlantFromCash(int typeId)
    {
        List<Plant> allPlants = fieldCash.getAllPlants();
        for (Plant p : allPlants)
        {
            if (p.getId() == (long) typeId)
            {
                return p;
            }
        }
        return null;
    }

    /**
     * По ид строения возвращаем из кеша инфу о нем
     *
     * @param typeId
     * @return
     */
    private Building getBuildingFromCash(int typeId)
    {
        List<Building> allBuildings = fieldCash.getAllBuildings();
        for (Building b : allBuildings)
        {
            if (b.getId() == (long) typeId)
            {
                return b;
            }
        }
        return null;
    }

    private void updatePlayer(Player player)
    {
        Connection con = DaoUtils.getConnection();

        PreparedStatement statement = null;
        try
        {
            statement = con.prepareStatement(QueryConfig.UPDATE_PLAYER_BALLANCE);
            statement.setLong(1, player.getBalance());
            statement.setLong(1, player.getId());
            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(con, statement);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setEmptyPlant(Field field, int x, int y)
    {
        fieldCash.setCell(new EmptyCell(x, y), x, y); //ложим в кеш пустое поле
        updateCell(fieldCash.getId(), x, y);
    }

    public void addEmptyCells(Field field)
    {
        if (field == null)
        {
            System.out.println("Boroda. addEmptyCells" + " - не получается  добавить клеточки в поле. поле пустое");
        }

        long id = field.getId();
        //todo заменить на булк
        // bulckEmptyCellsInsert(id)

        for (int i = 1; i < 8 + 1; i++)
        {
            for (int j = 1; j < 8 + 1; j++)
            {
                addEmptyCell(j, i, id); //засадим пустыми клетками
            }
        }
    }

    private void addEmptyCell(int x, int y, long id)
    {
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.ADD_NEW_EMPTY_CELL);
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                connection.close();
                DaoUtils.close(preparedStatement);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String bulckEmptyCellsInsert(long id)
    {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.toString();
    }

    private void initBonuces(Field field)
    {
        for (Map.Entry<Integer, Map<Integer, Cell>> entryRows : field.getCells().entrySet())
        {
            for (Map.Entry<Integer, Cell> entry : entryRows.getValue().entrySet())
            {
                Cell currentCell = entry.getValue();
                if (currentCell.getType() == CELL_TYPE.PLANT)
                { // если ячейка растение
                    long timeBonus = 0; // бонус к созреваниб
                    long proceedBonus = 0;//бонус к урожаю

                    List<Cell> neighborCells = getNeighborCells(field, currentCell.getXPosition(), currentCell.getYPosition());//делаем лист ее соседей
                    if (neighborCells.size() > 0)
                    {
                        for (int i = 0; i < neighborCells.size(); i++)
                        {
                            if (neighborCells.get(i).getType() == CELL_TYPE.BUILDING)
                            {
                                Building building = (Building) neighborCells.get(i); // кастонули ячейку к зданию чтобы получить бонус

                                long time = building.getBonus().getTime();
                                long proceed = building.getBonus().getProseed();

                                if (time > timeBonus)
                                {
                                    timeBonus = time;
                                }
                                if (proceed > proceedBonus)
                                {
                                    proceedBonus = proceed;
                                }
                            }
                        }
                    }
                    Plant cellPlant = (Plant) currentCell;
                    cellPlant.setProseed(cellPlant.getProseed() + cellPlant.getProseed() * proceedBonus);
                    cellPlant.setGrowTime((int) (cellPlant.getGrowTime() - cellPlant.getGrowTime() * timeBonus / 100));
                }
            }
        }
    }

    private void initBonuces(List<Cell> farmCells)
    {

        for (Cell currentCell ://бежим по всем клеткам
                farmCells)
        {
            if (currentCell.getType() == CELL_TYPE.PLANT)
            { // если ячейка растение
                long timeBonus = 0; // бонус к созреваниб
                long proceedBonus = 0;//бонус к урожаю

                List<Cell> neighborCells = getNeighborCells(farmCells, currentCell.getXPosition(), currentCell.getYPosition());//делаем лист ее соседей
                if (neighborCells.size() > 0)
                {
                    for (int i = 0; i < neighborCells.size(); i++)
                    {
                        if (neighborCells.get(i).getType() == CELL_TYPE.BUILDING)
                        {
                            Building building = (Building) neighborCells.get(i); // кастонули ячейку к зданию чтобы получить бонус

                            long time = building.getBonus().getTime();
                            long proceed = building.getBonus().getProseed();

                            if (time > timeBonus)
                            {
                                timeBonus = time;
                            }
                            if (proceed > proceedBonus)
                            {
                                proceedBonus = proceed;
                            }
                        }
                    }
                }
                Plant cellPlant = (Plant) currentCell;
                cellPlant.setProseed(cellPlant.getProseed() + cellPlant.getProseed() * proceedBonus);
                //todo нужно добаивть подсчет времени коректный. Тут тип заглушка.
                cellPlant.setGrowTime((int) (cellPlant.getGrowTime() - cellPlant.getGrowTime() * timeBonus / 100));
            }
        }
    }

    /**
     * метод проверяет существует ли ячейка по указанному индексу и если да то добавляет ее в лист
     *
     * @param cells
     * @return лист соседних ячеек
     */
    private List<Cell> getNeighborCells(List<Cell> cells, int x, int y)
    {
        List<Cell> cellList = new ArrayList<Cell>();
        //проверяем на есть ли ячейка и
        try
        {
            Cell cell = getCellInList(cells, x - 1, y);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }//игнорим ошибку. Если клеточка не может быть х-1, у. просто не добавим в лист

        try
        {
            Cell cell = getCellInList(cells, x, y - 1);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }

        try
        {
            // int index = getIndexInList(x + 1, y);
            Cell cell = getCellInList(cells, x + 1, y);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }

        try
        {
            Cell cell = getCellInList(cells, x, y + 1);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }

        try
        {
            Cell cell = getCellInList(cells, x - 1, y - 1);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }

        try
        {
            Cell cell = getCellInList(cells, x + 1, y + 1);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }

        try
        {
            Cell cell = getCellInList(cells, x - 1, y + 1);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }

        try
        {
            Cell cell = getCellInList(cells, x + 1, y - 1);
            if (cell != null)
            {
                cellList.add(cell);
            }
        }
        catch (Exception e)
        {
        }

        return cellList;
    }

    private List<Cell> getNeighborCells(Field field, int x, int y)
    {
        List<Cell> cellList = new ArrayList<Cell>();
        //проверяем на есть ли ячейка и
        try
        {
            cellList.add(field.getCell(x - 1, y));
        }
        catch (Exception e)
        {
        }//игнорим ошибку. Если клеточка не может быть х-1, у. просто не добавим в лист

        try
        {
            cellList.add(field.getCell(x, y - 1));
        }
        catch (Exception e)
        {
        }

        try
        {
            cellList.add(field.getCell(x + 1, y));
        }
        catch (Exception e)
        {
        }

        try
        {
            cellList.add(field.getCell(x, y + 1));
        }
        catch (Exception e)
        {
        }

        try
        {
            cellList.add(field.getCell(x - 1, y - 1));
        }
        catch (Exception e)
        {
        }

        try
        {
            cellList.add(field.getCell(x + 1, y + 1));
        }
        catch (Exception e)
        {
        }

        try
        {
            cellList.add(field.getCell(x - 1, y + 1));
        }
        catch (Exception e)
        {
        }

        try
        {
            cellList.add(field.getCell(x + 1, y - 1));
        }
        catch (Exception e)
        {
        }

        return cellList;
    }

    private Cell getCellInList(List<Cell> cells, int x, int y)
    {
        for (Cell c : cells)
        {
            if (c.getXPosition() == x && c.getYPosition() == y)
            {
                return c;
            }
        }
        return null;
    }


}
