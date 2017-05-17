package com.test.Area.dao;

import com.test.Area.entity.AREA_TYPE;
import com.test.Area.entity.Area;
import com.test.Area.entity.AreaCell;
import com.test.field.entity.Field;
import com.test.util.DaoUtils;
import com.test.util.propertyconfig.QueryConfig;

import java.sql.*;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras
 */
public class AreaDaoImpl implements AreaDao
{
    //здесь будет храниться кеш для областей. Если кактой инфы нет, она добавится из базы
    private Map<Integer, Map<Integer, Area>> areaCash = new ConcurrentHashMap<>();
    private Map<Long, Area> areaCashById = new ConcurrentHashMap<>();

    public AreaDaoImpl()
    {
        loadAreas();
    }

    private void loadAreas()
    {
        Connection connection = DaoUtils.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        try
        {
            statement = connection.createStatement();
            rs = statement.executeQuery(QueryConfig.GET_ALL_AREAS);

            while (rs.next())
            {
                long id = rs.getLong("id_area");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                // areas.add(new Area(id, x, y));
                Map<Integer, Map<Integer, AreaCell>> cells = getCellsInAreaByIdDB(id);
                Area newArea = new Area(id, x, y, cells);

                setAreaCash(newArea, x, y);
                areaCashById.put(id, newArea);
                //  areaById.put()
            }
        }
        catch (SQLException e)
        {
            System.err.println("Can't get areas from DB");
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
    }


//
//    public List<AreaCell> getCellsInAreaById(long areaId)
//    {
//        return Collections.EMPTY_LIST;
//    }


//    private List<Area> getAllAreaDB()
//    {
//        List<Area> areas = new ArrayList<>();
//
//        Connection connection = DaoUtils.getConnection();
//        Statement statement = null;
//        ResultSet rs = null;
//
//        try
//        {
//            statement = connection.createStatement();
//            rs = statement.executeQuery(QueryConfig.GET_ALL_AREAS);
//
//            while (rs.next())
//            {
//                long id = rs.getLong("id_area");
//                int x = rs.getInt("x");
//                int y = rs.getInt("y");
//                areas.add(new Area(id, x, y));
//            }
//        }
//        catch (SQLException e)
//        {
//            System.err.println("Can't get areas from DB");
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
//        return areas;
//    }

    private Map<Integer, Map<Integer, AreaCell>> getCellsInAreaByIdDB(long areaId)
    {
        // List<AreaCell> areaCells = new ArrayList<>();
        Area area = new Area();

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_CELLS_BY_AREA_ID);
            preparedStatement.setLong(1, areaId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                AreaCell cell = new AreaCell();

                cell.setId(resultSet.getInt("id_area_cell"));

                int fieldId = resultSet.getInt("id_farm");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                cell.setX(x);
                cell.setY(y);

                if (fieldId != 0)
                {
                    Field field = new Field();
                    field.setId(fieldId);
                    cell.setField(field);
                }
                int idTypeCell = resultSet.getInt("id_type_area_cell");
                if (idTypeCell == 1)
                {
                    cell.setType(AREA_TYPE.EMPTY);
                }
                if (idTypeCell == 2)
                {
                    cell.setType(AREA_TYPE.FARM);
                }
                cell.setArea(new Area(areaId));
                area.setCell(cell, x, y);
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
        return area.getCells();

//        List<AreaCell> areaCells = new ArrayList<>();
//
//        Connection connection = DaoUtils.getConnection();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try
//        {
//            preparedStatement = connection.prepareStatement(QueryConfig.GET_CELLS_BY_AREA_ID);
//            preparedStatement.setLong(1, areaId);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next())
//            {
//                AreaCell cell = new AreaCell();
//
//                cell.setId(resultSet.getInt("id_area_cell"));
//
//                int fieldId = resultSet.getInt("id_farm");
//                cell.setX(resultSet.getInt("x"));
//                cell.setY(resultSet.getInt("y"));
//
//                if (fieldId != 0)
//                {
//                    Field field = new Field();
//                    field.setId(fieldId);
//                    cell.setField(field);
//                }
//                int idTypeCell = resultSet.getInt("id_type_area_cell");
//                if (idTypeCell == 1)
//                {
//                    cell.setType(AREA_TYPE.EMPTY);
//                }
//                if (idTypeCell == 2)
//                {
//                    cell.setType(AREA_TYPE.FARM);
//                }
//                cell.setArea(new Area(areaId));
//                areaCells.add(cell);
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                DaoUtils.close(connection, preparedStatement, resultSet);
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return areaCells;
    }

//    @Override
//    public Area getAreaById(long areaId)
//    {
//        throw new NotImplementedException();
//    }

//    private void updateCashForAreaId(long areaId)
//    {
//        throw new NotImplementedException();
//    }

//    private Area getAreaByIdDB(long areaId)
//    {
//        Area area = null;
//        Connection connection = DaoUtils.getConnection();
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try
//        {
//            statement = connection.prepareStatement(QueryConfig.GET_AREA_BY_ID);
//            statement.setLong(1, areaId);
//            resultSet = statement.executeQuery();
//            while (resultSet.next())
//            {
//                area = new Area();
//                area.setX(resultSet.getInt("x"));
//                area.setY(resultSet.getInt("y"));
//                area.setId(areaId);
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                DaoUtils.close(connection, statement, resultSet);
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return area;
//    }

    @Override
    public Area getAreaByCoordinates(int x, int y)
    {
        return getArea(x, y);
    }


    @Override
    public Area getAreaByFieldId(long fieldId)
    {
        for (Map.Entry<Integer, Map<Integer, Area>> entryRows : areaCash.entrySet())
        {
            for (Map.Entry<Integer, Area> entry : entryRows.getValue().entrySet())
            {

                if (containFieldId(entry.getValue(), fieldId))
                {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void addNewArea(Long fieldId)
    {
        AreaCell newCell = getAvailableSpace();
        newCell.setField(new Field(fieldId));
        updateCellInDB(newCell);
    }


    //Метод возвращает место в котором может разместится ферма
    //в данном случае рандомно по всей карте.
    private AreaCell getAvailableSpace()
    {
        AreaCell cell = null;
        Random random = new Random();
        random.nextInt(5);
        //тут может быть случай когда область заполнится и не будет места для новой случайной клетки
        //и тогда мы попадем  вбесконечный цкл
        Area area = getAreaByCoordinates(random.nextInt(5), random.nextInt(5));
        do
        {
            cell = area.getCell(random.nextInt(5), random.nextInt(5));
            if (cell.getType().equals(AREA_TYPE.EMPTY))
            {
                return cell;
            }
        }
        while (true);
    }

    //Делаем из ячейки ферму
    private void updateCellInDB(AreaCell newCell)
    {
        Connection con = DaoUtils.getConnection();

        PreparedStatement statement = null;
        try
        {
            statement = con.prepareStatement(QueryConfig.UPDATE_AREA_CELL);
            statement.setLong(1,newCell.getField().getId());
            statement.setInt(2, 2);
            statement.setLong(3, newCell.getId());
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

    private boolean containFieldId(Area area, long fieldId)
    {
        if (area.getCells() == null || area.getCells().size() < 1)
        {
            return false;
        }
        for (Map.Entry<Integer, Map<Integer, AreaCell>> entryRows : (area.getCells().entrySet()))
        {
            for (Map.Entry<Integer, AreaCell> entry : entryRows.getValue().entrySet())
            {

                if (entry.getValue().getField() == null)
                {
                    continue;
                }
                if (entry.getValue().getField().getId() == fieldId)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Area getAreaById(long areaId)
    {
        return areaCashById.get(areaId);
    }


//    @Override
//    public List<Area> getAllAreas()
//    {
//        checkAndInitCash();
//        return areaCashList;
//    }
//
//    private void insertAreaCellsDB(List<Area> areaCashList)
//    {
//        for (Area area : areaCashList)
//        {
//            area.setCells(getCellsInAreaByIdDB(area.getId()));
//        }
//    }
//
//    private List<Area> getAllAreaDB()
//    {
//        List<Area> areas = new ArrayList<>();
//
//        Connection connection = DaoUtils.getConnection();
//        Statement statement = null;
//        ResultSet rs = null;
//
//        try
//        {
//            statement = connection.createStatement();
//            rs = statement.executeQuery(QueryConfig.GET_ALL_AREAS);
//
//            while (rs.next())
//            {
//                long id = rs.getLong("id_area");
//                int x = rs.getInt("x");
//                int y = rs.getInt("y");
//                areas.add(new Area(id, x, y));
//            }
//        }
//        catch (SQLException e)
//        {
//            System.err.println("Can't get areas from DB");
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
//        return areas;
//    }
//
//
//    @Override
//    public AreaCell getAreaCellById(long cellId)
//    {
//
//        for (Area area : areaCashList)
//        {
//            for (AreaCell targetCell : area.getCells())
//            {
//                if (targetCell.getId() == cellId)
//                {
//                    return targetCell;
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public List<AreaCell> getCellsInAreaById(long areaId)
//    {
//        for (Area searchArea : areaCashList)
//        {
//            if (searchArea.getId() == areaId && searchArea.getCells().size() > 0)
//            {
//                return searchArea.getCells();
//            }
//        }
//        return getCellsInAreaByIdDB(areaId);
//    }
//
//    private List<AreaCell> getCellsInAreaByIdDB(long areaId)
//    {
//        List<AreaCell> areaCells = new ArrayList<>();
//
//        Connection connection = DaoUtils.getConnection();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try
//        {
//            preparedStatement = connection.prepareStatement(QueryConfig.GET_CELLS_BY_AREA_ID);
//            preparedStatement.setLong(1, areaId);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next())
//            {
//                AreaCell cell = new AreaCell();
//
//                cell.setId(resultSet.getInt("id_area_cell"));
//
//                int fieldId = resultSet.getInt("id_farm");
//                cell.setX(resultSet.getInt("x"));
//                cell.setY(resultSet.getInt("y"));
//
//                if (fieldId != 0)
//                {
//                    Field field = new Field();
//                    field.setId(fieldId);
//                    cell.setField(field);
//                }
//                int idTypeCell = resultSet.getInt("id_type_area_cell");
//                if (idTypeCell == 1)
//                {
//                    cell.setType(AreaType.EMPTY);
//                }
//                if (idTypeCell == 2)
//                {
//                    cell.setType(AreaType.FARM);
//                }
//                cell.setArea(new Area(areaId));
//                areaCells.add(cell);
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                DaoUtils.close(connection, preparedStatement, resultSet);
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return areaCells;
//    }
//

//        updateCashForAreaId(areaId);
//        Area area = getAreaById(areaId);
//        if (area == null)
//        {
//            System.out.println("Не существует области с таким id");
//            return null;
//        }
//        return area;
//    }
//
//    private void updateCashForAreaId(long areaId)
//    {
//        Area area = getAreaByIdDB(areaId);
//        area.setCells(getCellsInAreaByIdDB(areaId));
//        areaCashList.add(area);
//    }
//
//    private Area getAreaByIdDB(long areaId)
//    {
//        Area area = null;
//        Connection connection = DaoUtils.getConnection();
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try
//        {
//            statement = connection.prepareStatement(QueryConfig.GET_AREA_BY_ID);
//            statement.setLong(1, areaId);
//            resultSet = statement.executeQuery();
//            while (resultSet.next())
//            {
//                area = new Area();
//                area.setX(resultSet.getInt("x"));
//                area.setY(resultSet.getInt("y"));
//                area.setId(areaId);
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                DaoUtils.close(connection, statement, resultSet);
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return area;
//    }
//
//    @Override
//    public Area getAreaByCoordinates(int x, int y)
//    {
//        for (Area targetArea : areaCashList)
//        {
//            if (targetArea.getX() == x && targetArea.getY() == y)
//            {
//                return targetArea;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Area getAreaByFieldId(long fieldId)
//    {
//        checkAndInitCash();
//        for (Area searchArea : areaCashList)
//        {
//            for (AreaCell areaCell : searchArea.getCells())
//            {
//                if (areaCell.getField().getId() == fieldId)
//                {
//                    return searchArea;
//                }
//            }
//        }
//        return null;
//    }
//
//    private void checkAndInitCash()
//    {
//        if (areaCashList.size() < 1)
//        {
//            areaCashList = getAllAreaDB();
//            insertAreaCellsDB(areaCashList);
//        }
//    }
//
//    @Override
//    public AreaCell getAreaCellByFieldId(long fielId)
//    {
//        checkAndInitCash();
//        for (Area area : areaCashList)
//        {
//            for (AreaCell cell : area.getCells())
//            {
//                if (cell.getField() != null && cell.getField().getId() == fielId)
//                {
//                    return cell;
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void updateAreaCell(Area area, AreaCell cell)
//    {
//        throw new NotImplementedException();
//    }
//
//    private AreaCell getAreaCellByFieldIdDB(long fieldId)
//    {
//        AreaCell areaCell = null;
//        Connection connection = DaoUtils.getConnection();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try
//        {
//            preparedStatement = connection.prepareStatement(QueryConfig.GET_AREA_CELL_BY_FIELD_ID);
//            preparedStatement.setLong(1, fieldId);
//
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next())
//            {
//                int cellId = resultSet.getInt("id_area_cell");
//                int areaId = resultSet.getInt("id_area");
//                int x = resultSet.getInt("x");
//                int y = resultSet.getInt("y");
//
//                Field field = new Field();
//                field.setId(fieldId);
//                areaCell = new AreaCell(cellId, x, y, AreaType.FARM, new Area(areaId), field);
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                DaoUtils.close(connection, preparedStatement, resultSet);
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return areaCell;
//    }
//    //получить в лист все области из бд
//    //для каждой области доставать и заполнять все клетки.
//


    public Area getArea(int x, int y)
    {
        Area area = null;
        Map<Integer, Area> mapY = areaCash.get(x);
        if (mapY != null && !mapY.isEmpty())
        {
            area = mapY.get(y);
        }
        return area;
    }

    public void setAreaCash(Area obj, int x, int y)
    {
        Map<Integer, Area> mapY = areaCash.get(x);

        if (mapY == null)
        {
            mapY = new ConcurrentHashMap<>();
            areaCash.put(x, mapY);
        }
        mapY.put(y, obj);
    }
}
