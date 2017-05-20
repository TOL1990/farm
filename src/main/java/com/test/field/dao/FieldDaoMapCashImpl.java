package com.test.field.dao;

import com.test.core.ConstCollections;
import com.test.field.entity.*;
import com.test.player.entity.Player;
import com.test.util.DaoUtils;
import com.test.util.propertyconfig.QueryConfig;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras.
 */
public class FieldDaoMapCashImpl implements FieldDao
{
    private Map<Long, Field> fields;//<field Id, Field>
    private Map<Long, Long> fieldByUserId;//<UserId, FieldId>


    public FieldDaoMapCashImpl()
    {
        fields = new ConcurrentHashMap<>();
        fieldByUserId = new ConcurrentHashMap<>();
    }


    @Override
    public Field getField(Player player)
    {
        if (player != null)
        {
            return getFieldByPlayerId(player.getId());
        }
        return null;
    }

    @Override
    public void updateCell(long fieldId, int x, int y)
    {
        Field field = fields.get(fieldId);      
        updateCellDB(fieldId, field.getCell(x,y));
    }

    private Field getFieldByPlayerId(long playerId)
    {
        Field field = null;
        Long fieldId = fieldByUserId.get(playerId);
        if (fieldId != null)
        {
            field = fields.get(fieldId);
        }
        else
        {
           field = addFieldToCash(playerId);
        }
        return field;
    }

    private Field addFieldToCash(Long playerId)
    {
        Field field = null;
        field = getFieldFromDBByPlayerId(playerId);
        if (field != null)
        {
            fields.put(field.getId(), field);
            fieldByUserId.put(playerId, field.getId());
        }
        return field;
    }

    private Field getFieldFromDBByPlayerId(Long playerId)
    {
        Field field = null;
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Long fieldId = null;
        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_FIELD_BY_USER_ID);
            preparedStatement.setLong(1, playerId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                fieldId = resultSet.getLong("id_field"); // получаем ID фермы, заданного юзера
            }
            if (fieldId == null)
            {
                System.out.println("Не получилось получить поле по ид игрока");
            }
            field = new Field();
            field.setId(fieldId);
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
        field.setCells(getCellsByFieldId(fieldId));
        return field;
    }

    /**
     * Возвращет лист клеток по ид поля из БД
     */
    @Override
    public Map<Integer, Map<Integer, Cell>> getCellsByFieldId(long id)
    {
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
                    // cells.add(new EmptyCell(x, y));
                    field.setCell(new EmptyCell(x, y), x, y);
                }
                if (typeName.equals(CELL_TYPE.PLANT.toString()))
                {
                    Plant plantInfo = ConstCollections.getPlant(typeId);
                    Plant plant = new Plant((long) typeId, plantInfo.getName(), plantInfo.getPrice(), plantInfo.getProceed(), plantInfo.getGrowTime(), plantedDate.getTime(), x, y);
                    //   cells.add(plant);
                    field.setCell(plant, x, y);
                }
                if (typeName.equals(CELL_TYPE.BUILDING.toString()))
                {
                    Building buildInfo = ConstCollections.getBuilding(typeId);
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

  
    @Override
    public void addField(Long playerId)
    {
        Field field = addFieldDB(playerId);
        if (field.getId() >= 0)
        {
            fields.put(field.getId(), field);
            fieldByUserId.put(playerId, field.getId());
        }
    }
    
    public Field addFieldDB(Long playerId)
    {
        Field newField = new Field();
        long retKey = -1; // будем в него ловить сгенеренный базой ИД после добавления поля
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        if (playerId == null)
        {
            System.out.println("Не получается добавить поле по пустому юзеру");
        }

        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.ADD_FIELD_BY_USER_ID, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, playerId);
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next())
            {
                retKey = rs.getLong(1);
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
                connection.close();
                DaoUtils.close(preparedStatement);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        newField.setId(retKey);
        newField.setPlayer(new Player(playerId));
        newField.setCells(addEmptyCells(newField.getId()));
            return newField;
    }

    private Map<Integer,Map<Integer,Cell>> addEmptyCells(long fieldId)
    {
        Field fildCellcontainer = new Field();

        //todo заменить на булкInsert
        for (int x = 1; x < 8 + 1; x++)
        {
            for (int y = 1; y < 8 + 1; y++)
            {
               fildCellcontainer.setCell(addEmptyCell(y, x, fieldId),x,y); //засадим пустыми клетками
            }
        }
        return fildCellcontainer.getCells();
    }
    private Cell addEmptyCell(int x, int y, long fieldId)
    {
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.ADD_NEW_EMPTY_CELL);
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setLong(3, fieldId);
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
        return new EmptyCell(x,y);
    }
    private String bulckEmptyCellsInsert(long id)
    {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.toString();
    }
    
    private void updateCellDB(long fieldId, Cell cell)
    {
        Connection con = DaoUtils.getConnection();
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

            statement.setTimestamp(1, new Timestamp(time));
            statement.setLong(2, cell.getType().getId());//1.EMPTY 2.Plant 3.Building
            statement.setInt(3, id);

            statement.setInt(4, cell.getXPosition());
            statement.setInt(5, cell.getYPosition());
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
}
