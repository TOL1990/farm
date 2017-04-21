package model.entity;

import model.core.BuildingConfig;

/**
 * Created by Taras on 09.03.2017.
 */
public class Building extends Cell{
    private long id;
    private String name;
//    private int typeId;
    private BuildingBonus bonus;
    private long price;

    public Building() {
        setType(CellType.Building);
    }

    public Building(BuildingConfig config) {
        this.id = config.id;
        this.name = config.name;
        this.bonus = config.bonus;
        this.price = config.price;
    }

    public Building(int x, int y, int id) {
       this.xPosition = x;
       this.yPosition = y;
       this.id = id;
        setType(CellType.Building);
    }
    public Building(long id, String name,  BuildingBonus bonus, long price, int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.id = id;
        this.name = name;
        this.price = price;
        this.bonus = bonus;
        setType(CellType.Building);
    }

    /**
     * конструктор нужен для создания листа всех доступных строений
     * @param id
     * @param name
     * @param bonus
     * @param price
     */
    public Building(long id, String name, BuildingBonus bonus, long price) {
        this.id = id;
        this.name = name;
        this.bonus = bonus;
        this.price = price;

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



    /**
     * Для равности не сравнивается класс с бонусами
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Building building = (Building) o;

        if (id != building.id) return false;
//        if (typeId != building.typeId) return false;
        if (price != building.price) return false;
        return name != null ? name.equals(building.name) : building.name == null;
    }
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + typeId;
        result = 31 * result + (int) (price ^ (price >>> 32));

        return result;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", typeId=" + typeId +
                ", bonus=" + bonus +
                ", price=" + price +
                '}';
    }
}
