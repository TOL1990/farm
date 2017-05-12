package com.test.Area.entity;

/**
 * Created by Taras 
 */
public enum AREA_TYPE
{
    ERROR(-1, "Error"), FARM(1, "Farm"), EMPTY(2, "Empty");

    private int id;
    private String desc;

    AREA_TYPE(int id, String desc)
    {
        this.id = id;
        this.desc = desc;
    }

    public int getId()
    {
        return id;
    }

    public String getDesc()
    {
        return desc;
    }

    public static AREA_TYPE valueOf(int id)
    {
        for (AREA_TYPE e : AREA_TYPE.values())
        {
            if (e.id == id)
            {
                return e;
            }
        }
        return ERROR;
    }

    @Override
    public String toString()
    {
        return getDesc();
    }
}
