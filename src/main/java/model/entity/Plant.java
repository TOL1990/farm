package model.entity;


import java.sql.Timestamp;

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
    private Timestamp plantedTime;


    public Plant(int xPosition, int yPosition, int typeId, Timestamp plantedTime) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.typeId = typeId;
        this.plantedTime = plantedTime;
        this.setType(CellType.Plant);
    }

    public  Plant(long id, String name, long price, long proseed, int growTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.proseed = proseed;
        this.growTime = growTime;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Timestamp getPlantedTime() {return plantedTime;}

    public void setPlantedTime(Timestamp plantedTime) {this.plantedTime = plantedTime;}

    /**
     * Для сравнивания не учитывается время посадки
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plant plant = (Plant) o;

        if (id != plant.id) return false;
        if (typeId != plant.typeId) return false;
        if (price != plant.price) return false;
        if (proseed != plant.proseed) return false;
        if (growTime != plant.growTime) return false;
        return name != null ? name.equals(plant.name) : plant.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (int) (proseed ^ (proseed >>> 32));
        result = 31 * result + growTime;
        return result;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "[" + this.xPosition +
                " , " + this.yPosition +
                "] id=" + id +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", price=" + price +
                ", proseed=" + proseed +
                ", growTime=" + growTime +
                ", plantedTime=" + plantedTime +
                '}';
    }
}
