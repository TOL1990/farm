package com.test.core.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Taras.
 */
@XmlRootElement(name = "plants")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlantsConfiger
{
    @XmlElement(name = "plant")
    private  List<PlantConfig> plants = null;

    public List<PlantConfig> getPlants()
    {
        return plants;
    }

    public void setPlants(List<PlantConfig> plants)
    {
        this.plants = plants;
    }
}
