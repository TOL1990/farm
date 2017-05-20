package com.test.field.entity;

/**
 * Created by Taras on 09.03.2017.
 */

public class Plant extends Cell
{
    private long id;
    private String name;
    private long price;
    private long proceed; //выручка
    private long growTime;
    private long plantedTime;

    public Plant(){setType(CELL_TYPE.PLANT);}
    public Plant(Plant copyPlant)
    {
        this.id = copyPlant.getId();
        this.name = copyPlant.getName();
        this.price = copyPlant.getPrice();
        this.proceed = copyPlant.getProceed();
        this.growTime = copyPlant.getGrowTime();
        this.plantedTime = copyPlant.getPlantedTime();
    }

    //    private Map<Integer, Map<Integer, Object>> field = new ConcurrentHashMap<>();
//
//    public Object getObject(int x, int y)
//    {
//        Object obj = null;
//        Map<Integer, Object> mapY = field.get(x);
//        if (mapY != null && !mapY.isEmpty())
//        {
//            obj = mapY.get(y);
//        }
//        return obj;
//    }
//
//    public void addObject(int x, int y, Object obj)
//    {
//        Map<Integer, Object> mapY = field.get(x);
//
//        if (mapY == null)
//        {
//            mapY = new ConcurrentHashMap<>();
//            field.put(x, mapY);
//        }
//
//        mapY.put(y, obj);
//    }


    public Plant(int xPosition, int yPosition, int id, long plantedTime)
    {
        this.id = (long) id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.plantedTime = plantedTime;
        this.setType(CELL_TYPE.PLANT);
    }

    public Plant(long id, String name, long price, long proceed, long growTime)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.proceed = proceed;
        this.growTime = growTime;
        this.setType(CELL_TYPE.PLANT);
    }

        public Plant(long id, String name, long price, long proceed, long growTime, long plantedTime, int x, int y)
    {
        this.xPosition = x;
        this.yPosition = y;
        this.id = id;
        this.name = name;
        this.price = price;
        this.proceed = proceed;
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

    public long getProceed()
    {
        return proceed;
    }

    public void setProceed(long proceed)
    {
        this.proceed = proceed;
    }

    public long getGrowTime()
    {
        return growTime;
    }

    public void setGrowTime(long growTime)
    {
        this.growTime = growTime;
    }

    public long getPlantedTime()
    {
        return plantedTime;
    }

    public void setPlantedTime(long plantedTime)
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
        if (proceed != plant.proceed)
        {
            return false;
        }
        if (growTime != plant.growTime)
        {
            return false;
        }
        if (plantedTime != plant.plantedTime)
        {
            return false;
        }
        return name != null ? name.equals(plant.name) : plant.name == null;
    }

    @Override
    public int hashCode()
    {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (int) (proceed ^ (proceed >>> 32));
        result = 31 * result + (int) (growTime ^ (growTime >>> 32));
        result = 31 * result + (int) (plantedTime ^ (plantedTime >>> 32));
        return result;
    }

    /**
     * Для сравнивания не учитывается время посадки
     */


    @Override
    public String toString()
    {
        return "Plant{" + "[" + this.xPosition + " , " + this.yPosition + "] id=" + id + ", name='" + name + '\'' + ", price=" + price + ", proceed=" + proceed + ", growTime=" + growTime + ", plantedTime=" + plantedTime + '}';
    }
}
