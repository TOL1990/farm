package com.test.Area.dao;

import com.test.Area.entity.Area;
import com.test.Area.entity.AreaCell;

import java.util.List;

/**
 * Created by Taras
 */
public interface AreaDao {
    List<Area> getAllAreas();
    Area getAreaById(long areaId);
    Area getAreaByCoordinates(int x, int y);
    Area getAreaByFieldId(long fieldId);    
  //  AreaCell getAreaCellByFieldId(long fielId);

    /**
     * метод для обновления клетки на карте мира
     * @param area передать или area_id или x && y где изменить состояние клетки
     * @param cell на какую клетку поменять. Установить ферму или опустошить клетку
     */
    void updateAreaCell(Area area, AreaCell cell);

}
