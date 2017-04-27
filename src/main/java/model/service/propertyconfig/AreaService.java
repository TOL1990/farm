package model.service.propertyconfig;

import model.dao.AreaDao;
import model.dao.util.FactoryDao;
import model.entity.Area;
import model.entity.AreaCell;
import model.entity.Field;

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
        AreaCell cell = areaDao.getAreaCellByFieldId(field.getId());
        Area area = areaDao.getAreaById(cell.getArea().getId());
        return area;
    }

    public Area getArea(Area area) {
        if (area.getX() != 0 && area.getY() != 0)
            return areaDao.getAreaByCoordinates(area.getX(), area.getY());
        if (area.getId() != 0)
            return areaDao.getAreaById(area.getId());
        else
            return null;
    }

    public List<Area> getAllAreas() {
        return areaDao.getAllAreas();
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
