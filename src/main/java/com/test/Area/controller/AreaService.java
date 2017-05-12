package com.test.Area.controller;

import com.test.Area.dao.AreaDao;
import com.test.util.FactoryDao;
import com.test.Area.entity.Area;
import com.test.Area.entity.AreaCell;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Taras on 26.04.2017.
 */
public enum AreaService {
    INSTANCE;
    AreaDao areaDao;

    private Map<Long, Area> areas;
    private Map<Long, AreaCell> cellsByAreeaId;
    private AtomicLong idGenerator;

    AreaService() {
        areaDao = FactoryDao.getInstance().getAreaDao();
        areas = new ConcurrentHashMap<>();
        cellsByAreeaId = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong(0);
    }

    public void addArea(Area area)
    {
      //  areas.put(area.getId(),);
    }
    public void addCellToArea(AreaCell cell, Area area) {

    }

    public Area getAreaById(long areaId) {
        Area area = areas.get(areaId);
        if (area == null) {
            area = getAreaFromDB(areaId);

        }
        return areas.get(areaId);
    }

    private Area getAreaFromDB(long areaId) {
        return areaDao.getAreaById(areaId);
    }
}
