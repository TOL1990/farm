package model.Server.connection.src.main.java.com.fenix.util;

import model.entity.CELL_TYPE;

import java.io.Serializable;

/**
 * Created by Taras. Клас нужен для передачи  инфы о клетке поля
 */
public class TransferCellInfo implements Serializable
{
    public int x;
    public int y;
    public CELL_TYPE type;
    public String name;// название строения или растения. Reactor, Carrot, Cabbage

    public String planted; //когда посажено
    public long proseed;
    public long growTime;

    public int timeBonus;
    public int proseedBonus;

    public TransferCellInfo()
    {

    }

    public TransferCellInfo(int x, int y, CELL_TYPE type, String name, String planted)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.name = name;
        this.planted = planted;
    }

    public TransferCellInfo(int x, int y, CELL_TYPE type, String name, String planted, long proseed, int growTime, int timeBonus, int proseedBonus)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.name = name;
        this.planted = planted;
        this.proseed = proseed;
        this.growTime = growTime;
        this.timeBonus = timeBonus;
        this.proseedBonus = proseedBonus;
    }
}
