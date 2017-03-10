package model.Server;

import model.core.BuildingConstruct;
import model.entity.Building;
import model.entity.CellType;
import model.entity.Field;

/**
 * Created by Taras on 10.03.2017.
 */
public class ClientServer {
    public static void main(String[] args) {
        createBuilding();
    }


    public static void method() {
        Field field = new Field();
        field.createEmptyCells();
//            field.consoleSoutField();

        Building reactor = new Building();
        reactor.setName("reactor");


//        BuildingConstruct buildingConstruct = new BuildingConstruct(reactor, field.getCell(1, 1));
//buildingConstruct.run();
            field.setCell(reactor, 2, 5);

           Building b = (Building) field.getCell(2,5);
//        field.consoleSoutField();
    }

    public static void createBuilding(){
        Building reactor = new Building();
        Field field = new Field();
        field.createEmptyCells();
        new BuildingConstruct(reactor, field, 2, 1).run();
        field.consoleSoutField();
    }
}
