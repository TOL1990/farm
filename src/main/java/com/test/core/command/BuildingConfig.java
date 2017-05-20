package com.test.core.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Taras.
 */
@XmlRootElement(name  = "building")
@XmlAccessorType(XmlAccessType.FIELD)
public class BuildingConfig
{
    private long id;
    private String name;
    private long price;
    private int timeBonus;
    private int proceedBunus;

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

    public int getTimeBonus()
    {
        return timeBonus;
    }

    public void setTimeBonus(int timeBonus)
    {
        this.timeBonus = timeBonus;
    }

    public int getProceedBunus()
    {
        return proceedBunus;
    }

    public void setProceedBunus(int proceedBunus)
    {
        this.proceedBunus = proceedBunus;
    }
}
