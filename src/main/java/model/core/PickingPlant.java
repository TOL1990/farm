package model.core;

import model.entity.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 17.03.2017.
 */
public class PickingPlant extends Command {
    private Plant plant;
    private Field field;
    private long timeBonus = 0; // бонус к созреваниб
    private long proseedBonus = 0;//бонус к урожаю
    private int x;
    private int y;

    private long proseed;


    public PickingPlant(Field field, int x, int y) {
        this.field = field;
        this.x = x;
        this.y = y;
        this.isValid = true;

    }

    public boolean run() {

        validation();
        if (!isValid) return false;

        long earning = plant.getProseed() + (plant.getProseed() * proseedBonus / 100);

        Player player = field.getPlayer();
        player.setBalance(player.getBalance() + earning);

        field.setCell(new EmptyCell(x,y), x, y); // делаем ячейку пустой в кеше поля
        return true;
    }


    private void validation() {

        if (field.getCell(x, y).getType() != CellType.Plant) {
            System.out.println("В ячейке не расстение.");
            setValid(false);
        } else {
            plant = (Plant) field.getCell(x, y);
        }


        List<Cell> cells = field.getCells();
        List<Plant> plants = field.getAllPlants();
        List<Building> buildings = field.getAllBuildings();

        initBonuces(cells); //присвоить бонусы если есть

////проверяем выросло ли растение
        if (!isAlreadyGrownUp()) setValid(false);
    }

    private int getIndexInList(int x, int y) {
        return ((x - 1) + 8 * (y - 1));
    }

    private void initBonuces(List<Cell> cells) {
        List<Cell> neighborCells = checkNearCellsForBonus(cells);
        for (Cell c :
                neighborCells) {
            if (c.getType() == CellType.Building) {
                Building building = (Building) c; // кастонули ячейку к зданию чтобы получить бонус

                long time = building.getBonus().getTime();
                long proseed = building.getBonus().getProseed();

                if (time > timeBonus) timeBonus = time;
                if (proseed > proseedBonus) proseedBonus = proseed;
            }
        }
    }

    /**
     * метод проверяет существует ли ячейка по указанному индексу и если да то добавляет ее в лист
     *
     * @param cells
     * @return лист соседних ячеек
     */
    private List<Cell> checkNearCellsForBonus(List<Cell> cells) {
        List<Cell> cellList = new ArrayList<Cell>();
        //проверяем на есть ли ячейка и
        try {
            int index = getIndexInList(x - 1, y);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }//игнорим ошибку. Если клеточка не может быть х-1, у. просто не добавим в лист

        try {
            int index = getIndexInList(x, y - 1);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }

        try {
            int index = getIndexInList(x + 1, y);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }

        try {
            int index = getIndexInList(x, y + 1);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }

        try {
            int index = getIndexInList(x - 1, y - 1);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }

        try {
            int index = getIndexInList(x + 1, y + 1);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }

        try {
            int index = getIndexInList(x - 1, y + 1);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }

        try {
            int index = getIndexInList(x + 1, y - 1);
            cellList.add(cells.get(index));
        } catch (Exception e) {
        }

        return cellList;
    }

    private boolean isAlreadyGrownUp() {
        long oneMin = 60000;
        long plantedTime = plant.getPlantedTime().getTime();

        long growingTime = (long) plant.getGrowTime() * oneMin;   //здесь присвоим из переданного растения, или снова стучать в базу

        long bonusTime = (growingTime * timeBonus) / 100;

        long currenTime = new Timestamp(System.currentTimeMillis()).getTime();

        if ((plantedTime + (growingTime - bonusTime)) < currenTime) return true;

        else return false;
    }
}