package model.dao.impl;

import model.Server.GameCash;
import model.dao.FieldDao;
import model.dao.utils.DaoUtils;
import model.entity.Cell;
import model.entity.CellType;
import model.entity.Field;
import model.entity.Player;
import model.service.propertyconfig.QueryConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 13.03.2017.
 */
public class FieldDaoImpl implements FieldDao {
    //    GameCash gameCash = GameCash.getInstance();
    GameCash gameCash;

    /**
     * @return ферму из кеша. Если кеш пустой из БД по юзеру и обновляет кеш
     */
    public Field getField(Player player) {
        if (gameCash.getField() == null) {
            Field field = getFieldDB(player);
            gameCash.setField(field); // обновляем кеш
        }
        return gameCash.getField();
    }

    /**
     * Возвращет лист клеток по ид поля из БД
     */
    public List<Cell> getCellsByFieldId(long id) {
        List<Cell> cells = new ArrayList<Cell>();

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_CELLS_BY_FIELD_ID);
            preparedStatement.setLong(1, id);

            while (resultSet.next()) {
                int x = resultSet.getInt("pos_x");
                int y = resultSet.getInt("pos_y");
                int typeId = resultSet.getInt("type_id");
                String typeName = resultSet.getString("name");
                Date plantedDate = resultSet.getDate("planted");

                if(typeName.equals(CellType.Empty));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Field getFieldDB(Player player) {
        Field field = null;

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long fieldId = Long.parseLong(null);
        try {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_FIELD_BY_USER_ID);
            preparedStatement.setLong(1, player.getId());
            resultSet = preparedStatement.executeQuery();

            fieldId = resultSet.getLong("id_field"); // получаем ID фермы, заданного юзера
            gameCash.getField().setId(fieldId);//назначаем в кеш ид фермы

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        gameCash.getField().setCells(getCellsByFieldId(fieldId)); //заполняем наши клетки

        return gameCash.getField();
    }
}
