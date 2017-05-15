package com.test.field.entity;

/**
 * Created by Taras on 09.03.2017.
 */
public enum CELL_TYPE
{
    ERROR(-1, "Error"), EMPTY(1, "Empty"), PLANT(2, "Plant"), BUILDING(3, "Building");
    private int id;
    private String desc;

    CELL_TYPE(int id, String desc)
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

    public static CELL_TYPE valueOf(int id)
    {
        for (CELL_TYPE e : CELL_TYPE.values())
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
