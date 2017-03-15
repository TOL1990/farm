package model.dao.impl;

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

    private Field fieldCash; //тут будет лежать поле из бд

    public FieldDaoImpl() {
//        fieldCash = new Field(); // затащить всю из базы
        fieldCash = new Field();
    }

    public Field getField(long id) {
        return getField(new Player(id)); //это обновит кеш
    }

    /**
     * @return ферму из кеша. Если кеш пустой из БД по юзеру и обновляет кеш
     */
    public Field getField(Player player) {

        //todo затестить
        if (fieldCash.getId() == 0L) {
            getFieldDB(player);// обновляем кеш
        }
        return fieldCash;
    }

    private Field getFieldDB(Player player) {

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long fieldId = 0;
        try {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_FIELD_BY_USER_ID);
            preparedStatement.setLong(1, player.getId());
            resultSet = preparedStatement.executeQuery();

            ;
            while (resultSet.next()) {
                fieldId = resultSet.getLong("id_field"); // получаем ID фермы, заданного юзера
            }
            if (fieldId == 0) System.out.println("Не получилось получить поле по ид игрока");

            fieldCash.setId(fieldId);//назначаем в кеш ид фермы

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

                if (typeName.equals(CellType.Empty)) ;

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

    public void addField(Player player) {
        //todo создать новое поле и заполнить его пустыми клеточками, написать запрос
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;

        if (player == null || player.getId() == 0l) System.out.println("Не получается добавить поле по пустому юзеру");

        try {
            preparedStatement = connection.prepareStatement(QueryConfig.ADD_FIELD_BY_USER_ID);
            preparedStatement.setLong(1, player.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                DaoUtils.close(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        getField(player.getId());//обновить кеш //todo переделать по человечи

        addEmptyCells(fieldCash);

        //создать новое поле в бд для игрока с ником nickName

    }


    public void addEmptyCells(Field field) {
        if (field == null) System.out.println("Boroda. addEmptyCells" +
                " - не получается  добавить клеточки в поле. поле пустое");

        long id = field.getId();

        //todo заменить на булк
        // bulckEmptyCellsInsert(id)

        for (int i = 1; i < 8 + 1; i++) {
            for (int j = 1; j < 8 + 1; j++) {
                addEmptyCell(j, i, id); //засадим пустыми клетками //todo посмотреть правильно ли выводится х, у
            }
        }
    }

    private void addEmptyCell(int x, int y, long id) {
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(QueryConfig.ADD_NEW_EMPTY_CELL);
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                DaoUtils.close(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String bulckEmptyCellsInsert(long id) {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }

    private void addEmptyCells() {

    }


}
