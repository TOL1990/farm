package model.dao.impl;

import model.entity.Area;
import model.entity.AreaCell;

import java.util.List;

/**
 * Created by Taras on 08.05.2017.
 */
public class AreaCash {
    public List<List<Area>> areaCash;

    public AreaCash(List<List<Area>> areaCash) {
        this.areaCash = areaCash;
    }

    public AreaCash() {
    }

    public List<List<Area>> getAreaCash() {
        return areaCash;
    }

    public void setAreaCash(List<List<Area>> areaCash) {
        this.areaCash = areaCash;
    }

    public Area getAreaByCoordinates(int x, int y) {
        return this.areaCash.get(x - 1).get(y - 1);
    }

    public Area getAreaByFieldId(long fielId) {
        for (List<Area> columnList :
                areaCash) {
            for (Area area :
                    columnList) {
                for (AreaCell cell : area.getCells()) {
                    if (cell.getField() != null && cell.getField().getId() == fielId) return area;
                }
            }
        }
        return null;
    }
}
