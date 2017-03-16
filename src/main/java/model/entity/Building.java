package model.entity;

import model.core.BuildingConfig;

/**
 * Created by Taras on 09.03.2017.
 */
public class Building extends Cell{
    private long id;
    private String name;
    private int typeId;
    private BuildingBonus bonus;
    private long price;
    private byte increase;

    public Building() {
        setType(CellType.Building);
    }

    public Building(BuildingConfig config) {
        this.id = config.id;
        this.name = config.name;
        this.bonus = config.bonus;
        this.price = config.price;
        this.increase = config.increase;
    }

    public Building(int x, int y, int typeId) {
       this.xPosition = x;
       this.yPosition = y;
       this.typeId = typeId;
        setType(CellType.Building);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildingBonus getBonus() {
        return bonus;
    }

    public void setBonus(BuildingBonus bonus) {
        this.bonus = bonus;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public byte getIncrease() {
        return increase;
    }

    public void setIncrease(byte increase) {
        this.increase = increase;
    }

    public int getTypeId() {return typeId;}

    public void setTypeId(int typeId) {this.typeId = typeId;}
}
