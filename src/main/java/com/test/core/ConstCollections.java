package com.test.core;

import com.test.core.command.BuildingConfig;
import com.test.core.command.BuildingsConfiger;
import com.test.core.command.PlantConfig;
import com.test.core.command.PlantsConfiger;
import com.test.field.entity.Building;
import com.test.field.entity.BuildingBonus;
import com.test.field.entity.Plant;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by KVAS Taras.
 */
public class ConstCollections
{
    public static final String PLANTS_XML_PATH = "src/main/resources/properties/plantsConfig.xml";
    public static final String BUILDINGS_XML_PATH = "src/main/resources/properties/buildingsConfig.xml";

    public static Map<Integer, Plant> availablePlants;
    public static Map<String, Integer> plantsByName;

    public static Map<Integer, Building> availableBuildings;
    public static Map<String, Integer> buildingsByName;


    static
    {
        init();
////        MYXmlParserAbstract< Plant> parser = new MYXmlParserAbstract<Plant>()
////        {
////            @Override
////            protected void subnodePreAction(Plant model)
////            {
////                
////            }
////
////            @Override
////            protected void processValueNode(Plant model, Plant t1, String s)
////            {
////
////            }
////
////            @Override
////            protected void subnodePostAction(Plant model)
////            {
////
////            }
////            
//        };
////        parser.parseXml();
////        availablePlants = Collections.unmodifiableList(parser.getMap());
    }

    public static void init()
    {
        plantsByName = new ConcurrentHashMap<>();
        buildingsByName = new ConcurrentHashMap<>();

        availablePlants = getPlantsFromXML();
        availableBuildings = getBuildingsFromXML();
    }

    public static Plant getPlant(int id)
    {
        return availablePlants.get(id);
    }

    public static Building getBuilding(int id)
    {
        return availableBuildings.get(id);
    }

    public static Building getBuilding(String name)
    {
        int id = buildingsByName.get(name.toLowerCase());
        return getBuilding(id);
    }

    private static Map<Integer, Plant> getPlantsFromXML()
    {
        Map<Integer, Plant> plantMap = new ConcurrentHashMap<>();
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(PlantsConfiger.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            PlantsConfiger emps = (PlantsConfiger) jaxbUnmarshaller.unmarshal(new File(PLANTS_XML_PATH));

            for (PlantConfig plant : emps.getPlants())
            {
                Plant newPlant = new Plant();
                newPlant.setId(plant.getId());
                newPlant.setName(plant.getName());
                newPlant.setPrice(plant.getPrice());
                newPlant.setProceed(plant.getProceed());
                newPlant.setGrowTime(plant.getGrowTime());
                
                plantMap.put((int) plant.getId(), newPlant);
                plantsByName.put(newPlant.getName(), (int) newPlant.getId());
            }

        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
        return plantMap;
    }

    private static Map<Integer, Building> getBuildingsFromXML()
    {
        Map<Integer, Building> buildingMap = new ConcurrentHashMap<>();
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(BuildingsConfiger.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            BuildingsConfiger buildingConfig = (BuildingsConfiger) jaxbUnmarshaller.unmarshal(new File(BUILDINGS_XML_PATH));

            for (BuildingConfig building : buildingConfig.getBuildings())
            {
                Building newBuilding = new Building();
                newBuilding.setId(building.getId());
                newBuilding.setName(building.getName());
                newBuilding.setPrice(building.getPrice());
                int buildingTimeBonus = building.getTimeBonus();
                int buildingProceedBonus = building.getTimeBonus();
                newBuilding.setBonus(new BuildingBonus(buildingTimeBonus, buildingProceedBonus));

                buildingMap.put((int) newBuilding.getId(), newBuilding);
                buildingsByName.put(newBuilding.getName(), (int) newBuilding.getId());
            }
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
        return buildingMap;
    }

    public static void toPlantXmlFile(PlantsConfiger configer, String path)
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(PlantsConfiger.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Marshal the employees list in console
            jaxbMarshaller.marshal(configer, System.out);

            //Marshal the employees list in file
            jaxbMarshaller.marshal(configer, new File(path));
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }

    public static Building getBulding(String buildingName)
    {
        int id = buildingsByName.get(buildingName);
        return getBuilding(id);
    }

    public static Plant getPlant(String plantName)
    {
        int id = plantsByName.get(plantName.toLowerCase());
        return getPlant(id);
    }
}
