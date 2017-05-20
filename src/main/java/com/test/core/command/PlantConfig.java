package com.test.core.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Taras.
 */
@XmlRootElement(name = "plant")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlantConfig
{
    private long id;
    private String name;
    private long price;
    private long proceed; //выручка
    private long growTime;

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
}
