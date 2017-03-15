package model.entity;

import java.sql.Date;

/**
 * Created by Taras on 09.03.2017.
 */
public class Plant extends Cell {
    private long id;
    private String name;
    private int typeId; // это нужно для того чтобы по нему сравнивать из лейвой сущности // todo перепроверить это
    private long price;
    private long proseed; //выручка
    private int growTime;
    private Date plantedTime;

    public Plant(int xPosition, int yPosition, int typeId, Date plantedTime) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.typeId = typeId;
        this.plantedTime = plantedTime;
        this.setType(CellType.Plant);
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getProseed() {
        return proseed;
    }

    public void setProseed(long proseed) {
        this.proseed = proseed;
    }

    public int getGrowTime() {
        return growTime;
    }

    public void setGrowTime(int growTime) {
        this.growTime = growTime;
    }

    public Date getPlantedTime() {
        return plantedTime;
    }

    public void setPlantedTime(Date plantedTime) {
        this.plantedTime = plantedTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
