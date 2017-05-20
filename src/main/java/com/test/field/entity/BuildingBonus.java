package com.test.field.entity;

/**
 * Created by Taras on 09.03.2017.
 */
public class BuildingBonus {
private long id;
private String name;
private int time; //ускорение времени
private int proseed; // увеличение урожая

    public BuildingBonus(long id, String name, int time, int proseed) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.proseed = proseed;
    }

    public BuildingBonus(int time, int proseed)
    {   
        this.time = time;
        this.proseed = proseed;
    }

    public BuildingBonus(long id) {
        this.id = id;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getProseed() {
        return proseed;
    }

    public void setProseed(int proseed) {
        this.proseed = proseed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingBonus that = (BuildingBonus) o;

        if (id != that.id) return false;
        if (time != that.time) return false;
        if (proseed != that.proseed) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + time;
        result = 31 * result + proseed;
        return result;
    }

    @Override
    public String toString() {
        return "BuildingBonus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", proseed=" + proseed +
                '}';
    }
}
