package com.test.core.command;

import com.test.field.entity.Plant;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Taras.
 */
public class Testplantconf
{
    public static void main(String[] args)
    {
        //  toXMLfile();
        // getfromxml();
//PlantsConfiger plantsConfiger = new PlantsConfiger();
//plantsConfiger.setPlants(new ArrayList<>());
//        PlantConfig wheet = new PlantConfig();
//        wheet.setId(1);
//        wheet.setName("wheet");
//        wheet.setPrice(1000);
//        wheet.setProceed(120);
//
//        PlantConfig corn = new PlantConfig();
//        corn.setId(2);
//        corn.setName("Corn");
//        corn.setPrice(20);
//        corn.setProceed(300);
//        plantsConfiger.getPlants().add(wheet);
//        plantsConfiger.getPlants().add(corn);
//        
//        toPlantXmlFile(plantsConfiger);
//
//    }
//
//    private static void toXMLfile()
//    {
//        Plant wheet = new Plant();
//        wheet.setId(1);
//        wheet.setName("wheet");
//        wheet.setPrice(1000);
//        wheet.setProceed(120);
//
//        Plant corn = new Plant();
//        corn.setId(2);
//        corn.setName("Corn");
//        corn.setPrice(20);
//        corn.setProceed(300);
//        try
//        {
//            File file = new File("src/main/resources/properties/plantsConfig.xml");
//            JAXBContext jaxbContext = JAXBContext.newInstance(Plant.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            jaxbMarshaller.marshal(wheet, file);
//            jaxbMarshaller.marshal(corn, file);
//            jaxbMarshaller.marshal(wheet, System.out);
//        }
//        catch (JAXBException e)
//        {
//            e.printStackTrace();
//        }
        fromXMLConfiger();
    }

    public static void getfromxml()
    {
        try
        {

            File file = new File("src/main/resources/properties/plantsConfig.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Plant.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Plant plant = (Plant) jaxbUnmarshaller.unmarshal(file);
            System.out.println(plant);

        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }



    public static void fromXMLConfiger()
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(PlantsConfiger.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            //We had written this file in marshalling example
            PlantsConfiger emps = (PlantsConfiger) jaxbUnmarshaller.unmarshal(new File("src/main/resources/properties/plantsConfig.xml"));


            for (PlantConfig plant : emps.getPlants())
            {
                System.out.println(plant.getId());
                System.out.println(plant.getName());
            }

        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }
}
