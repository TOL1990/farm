package model.entity;

/**
 * Created by Taras on 09.03.2017.
 */
public class Plant extends Cell{
    private long id;
    private String name;
    private long price;
    private long proseed; //выручка
    private int  growTime;

    public Plant() {
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
}
