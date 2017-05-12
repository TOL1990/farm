package com.test.util.propertyconfig;

import com.test.Area.dao.AreaDao;
import com.test.Area.entity.Area;
import com.test.field.entity.Field;
import com.test.util.FactoryDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras
 */
public class AreaService {


    //Старая версия
    private AreaDao areaDao;

    public AreaService() {
        this.areaDao = FactoryDao.getInstance().getAreaDao();
    }

    public Area getArea(Field field) {
        Area area = areaDao.getAreaByFieldId(field.getId());
        return area;
    }

    public Area getArea(Area area) {
        if (area.getX() != 0 && area.getY() != 0)
            return areaDao.getAreaByCoordinates(area.getX(), area.getY());
        else if (area.getId() != 0)
            return areaDao.getAreaById(area.getId());
        else
            return null;
    }


    public List<Area> getNeighborAreas(Area centralArea) {
        int x = centralArea.getX();
        int y = centralArea.getY();

        List<Area> neighdorAreas = new ArrayList<>();

        neighdorAreas.add(getArea(new Area(x - 1, y)));
        neighdorAreas.add(getArea(new Area(x, y - 1)));
        neighdorAreas.add(getArea(new Area(x + 1, y)));
        neighdorAreas.add(getArea(new Area(x, y + 1)));
        neighdorAreas.add(getArea(new Area(x - 1, y - 1)));
        neighdorAreas.add(getArea(new Area(x + 1, y + 1)));
        neighdorAreas.add(getArea(new Area(x - 1, y + 1)));
        neighdorAreas.add(getArea(new Area(x + 1, y - 1)));
        return neighdorAreas;
    }
}
