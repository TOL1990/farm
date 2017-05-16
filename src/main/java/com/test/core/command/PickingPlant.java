package com.test.core.command;

import com.test.field.controller.FieldManager;
import com.test.field.entity.CELL_TYPE;
import com.test.field.entity.EmptyCell;
import com.test.field.entity.Field;
import com.test.field.entity.Plant;
import com.test.player.conlroller.PlayerManager;
import com.test.player.entity.Player;

/**
 * Created by Taras on 17.03.2017.
 */
public class PickingPlant extends Command
{
    private Plant plant;
    private Field field;
    private Player player;
    private long timeBonus = 0; // бонус к созреваниб
    private long proseedBonus = 0;//бонус к урожаю
    private int x;
    private int y;


    public PickingPlant(Field field, int x, int y)
    {
        this.field = field;
        this.x = x;
        this.y = y;
        setValid(true);
    }

    public PickingPlant(long playerId, int x, int y)
    {
        this.field = FieldManager.INSTANCE.getFieldByUserId(playerId);
        this.player = PlayerManager.INSTANCE.getPlayer(playerId);
        this.x = x;
        this.y = y;
        setValid(true);
    }

    public boolean run()
    {
        boolean run = false;
        validation();

        if (isValid())
        {
            long earning = plant.getProseed();

            player.setBalance(player.getBalance() + earning);
            PlayerManager.INSTANCE.updatePlayerBallance(player);
            
            field.setCell(new EmptyCell(x, y), x, y); // делаем ячейку пустой в кеше поля
            FieldManager.INSTANCE.updateCell(field.getId(), field.getCell(x, y));
            run = true;
        }
        return run;
    }

    private void validation()
    {
        if (field.getCell(x, y).getType() != CELL_TYPE.PLANT)
        {
            System.out.println("В ячейке не расстение.");
            setValid(false);
        }
        else
        {
            plant = (Plant) field.getCell(x, y);
        }

        // List<Cell> cells = field.getCells();
//        Map<Integer, Map<Integer, Cell>> cells = field.getCells();
//
//        List<Plant> plants = field.getAllPlants();
//        List<Building> buildings = field.getAllBuildings();

        //     initBonuces(cells); //присвоить бонусы если есть // не нужно присваивать бонусы т.к. они инкрементятся на этапе поднятия поля из базы
////проверяем выросло ли растение
        if (!isAlreadyGrownUp())
        {
            setValid(false);
        }
    }

    private boolean isAlreadyGrownUp()
    {
        long oneSec = 1000;
        long plantedTime = plant.getPlantedTime();
        System.out.println("Planted time = " + plantedTime);

        long growingTime = plant.getGrowTime() * oneSec;   //здесь присвоим из переданного растения, или снова стучать в базу
        System.out.println("grow time = " + plant.getGrowTime() + ", growingtime = " + growingTime);

        //  long bonusTime = (growingTime * timeBonus) / 100;
        long currenTime = System.currentTimeMillis();
        System.out.println("Current time = " + currenTime);
        
        if ((plantedTime + growingTime) < currenTime)
        {
            
            return true;
        }
        else
        {
            return false;
        }
    }

//    private void initBonuces(Map<Integer, Map<Integer, Cell>> cells)
//    {
//        List<Cell> neighborCells = checkNearCellsForBonus(cells);
//        for (Cell c : neighborCells)
//        {
//            if (c.getType() == CELL_TYPE.BUILDING)
//            {
//                Building building = (Building) c; // кастонули ячейку к зданию чтобы получить бонус
//
//                long time = building.getBonus().getTime();
//                long proseed = building.getBonus().getProseed();
//
//                if (time > timeBonus)
//                {
//                    timeBonus = time;
//                }
//                if (proseed > proseedBonus)
//                {
//                    proseedBonus = proseed;
//                }
//            }
//        }
//    }

//    /**
//     * метод проверяет существует ли ячейка по указанному индексу и если да то добавляет ее в лист
//     * @param cells
//     * @return лист соседних ячеек
//     */
//    private List<Cell> checkNearCellsForBonus(Map<Integer, Map<Integer, Cell>> cells)
//    {
//        List<Cell> cellList = new ArrayList<Cell>();
//        //проверяем на есть ли ячейка и
////        try
////        {
////            int index = getIndexInList(x - 1, y);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }//игнорим ошибку. Если клеточка не может быть х-1, у. просто не добавим в лист
////
////        try
////        {
////            int index = getIndexInList(x, y - 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x + 1, y);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x, y + 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x - 1, y - 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x + 1, y + 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x - 1, y + 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x + 1, y - 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
//        try
//        {
//            cellList.add(getCell(cells, x , y-1));
//        }
//        catch (Exception e)
//        {
//        }//игнорим ошибку. Если клеточка не может быть х-1, у. просто не добавим в лист
//
//        try
//        {
//            cellList.add(getCell(cells,x, y+1));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells, x+1, y+1));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x-1, y ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x+1,y ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x-1, y+1 ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x+1,y-1 ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells, x-1,y-1));
//        }
//        catch (Exception e)
//        {
//        }
//
//        return cellList;
//    }
//
//    private boolean isAlreadyGrownUp()
//    {
//        long oneSec = 1000;
//        long plantedTime = plant.getPlantedTime();
//
//        long growingTime = plant.getGrowTime() * oneSec;   //здесь присвоим из переданного растения, или снова стучать в базу
//
//        long bonusTime = (growingTime * timeBonus) / 100;
//        long currenTime = new Timestamp(System.currentTimeMillis()).getTime();
//
//        if ((plantedTime + (growingTime - bonusTime)) < currenTime)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }

//    public Field getField()
//    {
//        return field;
//    }
//
//    public void setField(Field field)
//    {
//        this.field = field;
//    }
//
//    public Cell getCell(Map<Integer, Map<Integer, Cell>> cellList, int x, int y)
//    {
//        Cell cell = null;
//        Map<Integer, Cell> mapY = cellList.get(x);
//        if (mapY != null && !mapY.isEmpty())
//        {
//            cell = mapY.get(y);
//        }
//        return cell;
//    }


    ////////////////////////////

//    private Plant plant;
//    private Field field;
//    private long timeBonus = 0; // бонус к созреваниб
//    private long proseedBonus = 0;//бонус к урожаю
//    private int x;
//    private int y;
//
//    public PickingPlant(Field field, int x, int y)
//    {
//        this.field = field;
//        this.x = x;
//        this.y = y;
//        setValid(true);
//    }
//
//    public boolean run()
//    {
//        boolean run = false;
//        validation();
//
//        if (isValid())
//        {
//            long earning = plant.getProseed() + (plant.getProseed() * proseedBonus / 100);
//
//            Player player = field.getPlayer();
//            player.setBalance(player.getBalance() + earning);
//
//            field.setCell(new EmptyCell(x, y), x, y); // делаем ячейку пустой в кеше поля
//            run = true;
//        }
//        return run;
//    }
//
//    private void validation()
//    {
//        if (field.getCell(x, y).getType() != CELL_TYPE.PLANT)
//        {
//            System.out.println("В ячейке не расстение.");
//            setValid(false);
//        }
//        else
//        {
//            plant = (Plant) field.getCell(x, y);
//        }
//
//        // List<Cell> cells = field.getCells();
//        Map<Integer, Map<Integer, Cell>> cells = field.getCells();
//
//        List<Plant> plants = field.getAllPlants();
//        List<Building> buildings = field.getAllBuildings();
//
//        initBonuces(cells); //присвоить бонусы если есть
//////проверяем выросло ли растение
//        if (!isAlreadyGrownUp())
//        {
//            setValid(false);
//        }
//    }
//
//    private int getIndexInList(int x, int y)
//    {
//        return ((x - 1) + 8 * (y - 1));
//    }
//
//    private void initBonuces(Map<Integer, Map<Integer, Cell>> cells)
//    {
//        List<Cell> neighborCells = checkNearCellsForBonus(cells);
//        for (Cell c : neighborCells)
//        {
//            if (c.getType() == CELL_TYPE.BUILDING)
//            {
//                Building building = (Building) c; // кастонули ячейку к зданию чтобы получить бонус
//
//                long time = building.getBonus().getTime();
//                long proseed = building.getBonus().getProseed();
//
//                if (time > timeBonus)
//                {
//                    timeBonus = time;
//                }
//                if (proseed > proseedBonus)
//                {
//                    proseedBonus = proseed;
//                }
//            }
//        }
//    }
//
//    /**
//     * метод проверяет существует ли ячейка по указанному индексу и если да то добавляет ее в лист
//     * @param cells
//     * @return лист соседних ячеек
//     */
//    private List<Cell> checkNearCellsForBonus(Map<Integer, Map<Integer, Cell>> cells)
//    {
//        List<Cell> cellList = new ArrayList<Cell>();
//        //проверяем на есть ли ячейка и
////        try
////        {
////            int index = getIndexInList(x - 1, y);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }//игнорим ошибку. Если клеточка не может быть х-1, у. просто не добавим в лист
////
////        try
////        {
////            int index = getIndexInList(x, y - 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x + 1, y);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x, y + 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x - 1, y - 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x + 1, y + 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x - 1, y + 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
////
////        try
////        {
////            int index = getIndexInList(x + 1, y - 1);
////            cellList.add(cells.get(index));
////        }
////        catch (Exception e)
////        {
////        }
//        try
//        {
//            cellList.add(getCell(cells, x , y-1));
//        }
//        catch (Exception e)
//        {
//        }//игнорим ошибку. Если клеточка не может быть х-1, у. просто не добавим в лист
//
//        try
//        {
//            cellList.add(getCell(cells,x, y+1));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells, x+1, y+1));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x-1, y ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x+1,y ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x-1, y+1 ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells,x+1,y-1 ));
//        }
//        catch (Exception e)
//        {
//        }
//
//        try
//        {
//            cellList.add(getCell(cells, x-1,y-1));
//        }
//        catch (Exception e)
//        {
//        }
//
//        return cellList;
//    }
//
//    private boolean isAlreadyGrownUp()
//    {
//        long oneSec = 1000;
//        long plantedTime = plant.getPlantedTime();
//
//        long growingTime = plant.getGrowTime() * oneSec;   //здесь присвоим из переданного растения, или снова стучать в базу
//
//        long bonusTime = (growingTime * timeBonus) / 100;
//        long currenTime = new Timestamp(System.currentTimeMillis()).getTime();
//
//        if ((plantedTime + (growingTime - bonusTime)) < currenTime)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
//
//    public Field getField()
//    {
//        return field;
//    }
//
//    public void setField(Field field)
//    {
//        this.field = field;
//    }
//
//    public Cell getCell(Map<Integer, Map<Integer, Cell>> cellList, int x, int y)
//    {
//        Cell cell = null;
//        Map<Integer, Cell> mapY = cellList.get(x);
//        if (mapY != null && !mapY.isEmpty())
//        {
//            cell = mapY.get(y);
//        }
//        return cell;
//    }
}