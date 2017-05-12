package com.test.Area.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras on 26.04.2017.
 */
public class Area
{
    private long id;

    private int x;
    private int y;

  //  private List<AreaCell> cells;
    private Map<Integer, Map<Integer, AreaCell>> cells;

    public Area()
    {
        cells = new ConcurrentHashMap<>();
    }

    public Area(long id)
    {
        this.id = id;
    }

    public Area(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Area(long id, int x, int y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Area(long id, int x, int y, Map<Integer, Map<Integer, AreaCell>> cells)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.cells = cells;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public Map<Integer, Map<Integer, AreaCell>> getCells()
    {
        return cells;
    }

    public void setCells(Map<Integer, Map<Integer, AreaCell>> cells)
    {
        this.cells = cells;
    }
    public AreaCell getCell(int x, int y)
    {
        AreaCell cell = null;
        Map<Integer, AreaCell> mapY = cells.get(x);
        if (mapY != null && !mapY.isEmpty())
        {
            cell = mapY.get(y);
        }
        return cell;
    }
    public void setCell(AreaCell obj, int x, int y)
    {
        Map<Integer, AreaCell> mapY = cells.get(x);

        if (mapY == null)
        {
            mapY = new ConcurrentHashMap<>();
            cells.put(x, mapY);
        }

        mapY.put(y, obj);
    }
    @Override
    public String toString()
    {
        return "Area{" + "id=" + id + ", x=" + x + ", y=" + y + ", cells=" + cells + '}';
    }
}
