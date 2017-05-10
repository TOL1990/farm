package model.entity;


import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras on 09.03.2017.
 */
public class Plant extends Cell
{
    private long id;
    private String name;
    private long price;
    private long proseed; //выручка
    private long growTime;
    private Timestamp plantedTime;
    private Map<Integer, Map<Integer, Object>> field = new ConcurrentHashMap<>();

    public Object getObject(int x, int y)
    {
        Object obj = null;
        Map<Integer, Object> mapY = field.get(x);
        if (mapY != null && !mapY.isEmpty())
        {
            obj = mapY.get(y);
        }
        return obj;
    }

    public void addObject(int x, int y, Object obj)
    {
        Map<Integer, Object> mapY = field.get(x);

        if (mapY == null)
        {
            mapY = new ConcurrentHashMap<>();
            field.put(x, mapY);
        }

        mapY.put(y, obj);
    }


    public Plant(int xPosition, int yPosition, int id, Timestamp plantedTime)
    {
        this.id = (long) id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.plantedTime = plantedTime;
        this.setType(CELL_TYPE.PLANT);
    }

    public Plant(long id, String name, long price, long proseed, long growTime)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.proseed = proseed;
        this.growTime = growTime;
        this.setType(CELL_TYPE.PLANT);
    }

    public Plant(long id, String name, long price, long proseed, long growTime, Timestamp plantedTime, int x, int y)
    {
        this.xPosition = x;
        this.yPosition = y;
        this.id = id;
        this.name = name;
        this.price = price;
        this.proseed = proseed;
        this.growTime = growTime;
        this.plantedTime = plantedTime;
        this.setType(CELL_TYPE.PLANT);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getPrice()
    {
        return price;
    }

    public void setPrice(long price)
    {
        this.price = price;
    }

    public long getProseed()
    {
        return proseed;
    }

    public void setProseed(long proseed)
    {
        this.proseed = proseed;
    }

    public long getGrowTime()
    {
        return growTime;
    }

    public void setGrowTime(long growTime)
    {
        this.growTime = growTime;
    }

    public Timestamp getPlantedTime()
    {
        return plantedTime;
    }

    public void setPlantedTime(Timestamp plantedTime)
    {
        this.plantedTime = plantedTime;
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

        Plant plant = (Plant) o;

        if (id != plant.id)
        {
            return false;
        }
        if (price != plant.price)
        {
            return false;
        }
        if (proseed != plant.proseed)
        {
            return false;
        }
        if (growTime != plant.growTime)
        {
            return false;
        }
        if (!name.equals(plant.name))
        {
            return false;
        }
        return plantedTime.equals(plant.plantedTime);
    }

    @Override
    public int hashCode()
    {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (int) (proseed ^ (proseed >>> 32));
        result = 31 * result + (int) (growTime ^ (growTime >>> 32));
        result = 31 * result + plantedTime.hashCode();
        return result;
    }

    /**
     * Для сравнивания не учитывается время посадки
     */


    @Override
    public String toString()
    {
        return "Plant{" + "[" + this.xPosition + " , " + this.yPosition + "] id=" + id + ", name='" + name + '\'' + ", price=" + price + ", proseed=" + proseed + ", growTime=" + growTime + ", plantedTime=" + plantedTime + '}';
    }
}
