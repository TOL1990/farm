package com.test.Area.entity;


import com.test.field.entity.Field;

/**
 * Created by Taras
 */
public class AreaCell
{
    private long id;
    private int x;
    private int y;
    private AREA_TYPE type;
    private int areaId;
    private Area area;
    private Field field;

    public AreaCell()
    {
    }

    public AreaCell(long id, int x, int y, AREA_TYPE type, Area area)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.area = area;
    }

    public AreaCell(long id, int x, int y, AREA_TYPE type, Area area, Field field)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.area = area;
        this.field = field;
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

    public AREA_TYPE getType()
    {
        return type;
    }

    public void setType(AREA_TYPE type)
    {
        this.type = type;
    }

    public Area getArea()
    {
        return area;
    }

    public void setArea(Area area)
    {
        this.area = area;
    }

    public Field getField()
    {
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        AreaCell areaCell = (AreaCell) o;

        if (id != areaCell.id)
        {
            return false;
        }
        if (x != areaCell.x)
        {
            return false;
        }
        if (y != areaCell.y)
        {
            return false;
        }
        if (type != areaCell.type)
        {
            return false;
        }
        return area.equals(areaCell.area);
    }

    @Override
    public int hashCode()
    {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + type.hashCode();
        result = 31 * result + area.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "AreaCell{" + "id=" + id + ", x=" + x + ", y=" + y + ", type=" + type +
                //   ", area=" + area +
                //   ", fieldID=" + field.getId() +
                '}';
    }
}
