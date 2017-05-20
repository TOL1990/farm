package com.test.core.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Taras.
 */
@XmlRootElement(name = "buildings")
@XmlAccessorType(XmlAccessType.FIELD)
public class BuildingsConfiger
{
    @XmlElement(name = "building")
    private List<BuildingConfig> buildings  = null;

    public List<BuildingConfig> getBuildings()
    {
        return buildings;
    }

    public void setBuildings(List<BuildingConfig> buildings)
    {
        this.buildings = buildings;
    }
}
