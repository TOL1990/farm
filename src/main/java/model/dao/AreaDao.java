package model.dao;

import model.entity.Area;
import model.entity.AreaCell;

import java.util.List;

/**
 * Created by Taras
 */
public interface AreaDao {
    List<Area> getAllAreas();
    AreaCell getAreaCellById(long cellId);
    List<AreaCell> getCellsInAreaById(long areaId);
    Area getAreaById(long areaId);
    Area getAreaByCoordinates(int x, int y);
    Area getAreaByFieldId(long fieldId);
    AreaCell getAreaCellByFieldId(long fielId);

}
