package model.dao.impl;

import model.dao.AreaDao;
import model.dao.utils.DaoUtils;
import model.entity.Area;
import model.entity.AreaCell;
import model.entity.AreaType;
import model.entity.Field;
import model.service.propertyconfig.QueryConfig;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras
 */
public class AreaDaoImpl implements AreaDao {
    Field myField;
    List<Area> areaCashList; //здесь будет храниться кеш для областей. Если кактой инфы нет, она добавится из базы

    public AreaDaoImpl() {
        this.areaCashList = new ArrayList<>();
    }


    @Override
    public List<Area> getAllAreas() {
        checkAndInitCash();
        return areaCashList;
    }

    private void insertAreaCellsDB(List<Area> areaCashList) {
        for (Area area : areaCashList) {
            area.setCells(getCellsInAreaByIdDB(area.getId()));
        }
    }

    private List<Area> getAllAreaDB() {
        List<Area> areas = new ArrayList<>();

        Connection connection = DaoUtils.getConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(QueryConfig.GET_ALL_AREAS);

            while (rs.next()) {
                long id = rs.getLong("id_area");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                areas.add(new Area(id, x, y));
            }
        } catch (SQLException e) {
            System.err.println("Can't getting areas from DB");
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(connection, statement, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return areas;
    }


    @Override
    public AreaCell getAreaCellById(long cellId) {

        for (Area area : areaCashList) {
            for (AreaCell targetCell :
                    area.getCells()) {
                if (targetCell.getId() == cellId) return targetCell;
            }
        }
        return null;
    }

    @Override
    public List<AreaCell> getCellsInAreaById(long areaId) {
        for (Area searchArea :
                areaCashList) {
            if (searchArea.getId() == areaId && searchArea.getCells().size() > 0)
                return searchArea.getCells();
        }
        return getCellsInAreaByIdDB(areaId);
    }

    private List<AreaCell> getCellsInAreaByIdDB(long areaId) {
        List<AreaCell> areaCells = new ArrayList<>();

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_CELLS_BY_AREA_ID);
            preparedStatement.setLong(1, areaId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AreaCell cell = new AreaCell();

                cell.setId(resultSet.getInt("id_area_cell"));

                int fieldId = resultSet.getInt("id_farm");
                cell.setX(resultSet.getInt("x"));
                cell.setY(resultSet.getInt("y"));

                if (fieldId != 0) {
                    Field field = new Field();
                    field.setId(fieldId);
                    cell.setField(field);
                }
                int idTypeCell = resultSet.getInt("id_type_area_cell");
                if (idTypeCell == 1) cell.setType(AreaType.Empty);
                if (idTypeCell == 2) cell.setType(AreaType.Farm);
                cell.setArea(new Area(areaId));
                areaCells.add(cell);
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
        return areaCells;
    }

    @Override
    public Area getAreaById(long areaId) {
        for (Area area :
                areaCashList) {
            if (area.getId() == areaId) {
                if (area.getCells() == null)
                    area.setCells(getCellsInAreaById(areaId));
                return area;
            }
        }
        updateCashForAreaId(areaId);
        Area area = getAreaById(areaId);
        if (area == null) {
            System.out.println("Не существует области с таким id");
            return null;
        }
        return area;
    }

    private void updateCashForAreaId(long areaId) {
        Area area = getAreaByIdDB(areaId);
        area.setCells(getCellsInAreaByIdDB(areaId));
        areaCashList.add(area);
    }

    private Area getAreaByIdDB(long areaId) {
        Area area = null;
        Connection connection = DaoUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(QueryConfig.GET_AREA_BY_ID);
            statement.setLong(1, areaId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                area = new Area();
                area.setX(resultSet.getInt("x"));
                area.setY(resultSet.getInt("y"));
                area.setId(areaId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(connection, statement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return area;
    }

    @Override
    public Area getAreaByCoordinates(int x, int y) {
        for (Area targetArea : areaCashList) {
            if (targetArea.getX() == x && targetArea.getY() == y) return targetArea;
        }
        return null;
    }

    @Override
    public Area getAreaByFieldId(long fieldId) {
        checkAndInitCash();
        for (Area searchArea :
                areaCashList) {
            for (AreaCell areaCell :
                    searchArea.getCells()) {
                if (areaCell.getField().getId() == fieldId) return searchArea;
            }
        }
        return null;
    }

    private void checkAndInitCash() {
        if (areaCashList.size() < 1) {
            areaCashList = getAllAreaDB();
            insertAreaCellsDB(areaCashList);
        }
    }

    @Override
    public AreaCell getAreaCellByFieldId(long fielId) {
       checkAndInitCash();
            for (Area area :
                    areaCashList) {
                for (AreaCell cell :
                        area.getCells()) {
                    if (cell.getField().getId() == fielId) return cell;
                }
            }
        return null;
    }

    @Override
    public void updateAreaCell(Area area, AreaCell cell) {
     throw new NotImplementedException();
    }

    private AreaCell getAreaCellByFieldIdDB(long fieldId) {
        AreaCell areaCell = null;
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_AREA_CELL_BY_FIELD_ID);
            preparedStatement.setLong(1, fieldId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int cellId = resultSet.getInt("id_area_cell");
                int areaId = resultSet.getInt("id_area");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");

                Field field = new Field();
                field.setId(fieldId);
                areaCell = new AreaCell(cellId, x, y, AreaType.Farm, new Area(areaId), field);
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
        return areaCell;
    }
    //получить в лист все области из бд
    //для каждой области доставать и заполнять все клетки.
}
