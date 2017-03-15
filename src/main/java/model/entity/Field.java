package model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class Field {
    private final int CELL_LIMIT = 64;
    private final int HORIZONTAL_CELL = 8;
    private final int VERTICAL_CELL = 8;
    private List<Cell> cells;
    private Player player;
    private long id;
    private List<Plant> avaliablePlants;
    private List<Building> avaliableBuildings;

    public Field() {
        cells = new ArrayList<Cell>();
    }

    public Field(Player player) {
        cells = new ArrayList<Cell>();
        this.player = player;
    }

    public Field(Field copyField) {
        this.cells = copyField.getCells();
        this.player = copyField.getPlayer();
        this.id = copyField.getId();
        this.avaliablePlants = copyField.getAvaliablePlants();
        this.avaliableBuildings =   copyField.getAvaliableBuildings();
    }

    public void createEmptyCells() {
        if (cells != null) {
            for (int i = 1; i < HORIZONTAL_CELL + 1; i++) {
                for (int j = 1; j < VERTICAL_CELL + 1; j++) {
                    cells.add(new EmptyCell(j, i)); //засадим пустыми клетками
                }
            }
        } else
            System.out.println("Не получилось инициализировать пустыми клетками, поле");
    }

    public Cell getCell(int xPos, int yPos) {
        for (Cell c :
                cells) {
            if (c.getxPosition() == xPos && c.getyPosition() == yPos) return c;
        }
        System.out.println("Не найдена ячейка x = " + xPos + ", y = " + yPos);
        return null; // нету такой
    }

    /**
     * устанавливаем в клетку obj(растение, сооружение, пусто)
     *
     * @param x  - номер клетки по горизонтали
     * @param y- номер клетки по верикали
     */
    public void setCell(Cell obj, int x, int y) {
        ///вариант с instanceOf
//        if (obj instanceof Building) {
//            cells.set(getIndexInList(x, y), obj);
//        }
//        if (obj instanceof Plant) {
//            new Plant();
//        }
//        if (obj instanceof Building) {
//            new EmptyCell(x, y);
//        }

        //вариант с флагом

        //установить значение ячейки Наверно это нужно вынести в конструктор зданий
        obj.setxPosition(x);
        obj.setyPosition(y);

        cells.set(getIndexInList(x, y), obj);
    }

    /**
     * @return позицию файла в листе
     */
    private int getIndexInList(int x, int y) {
        return ((y - 1) + 8 * (x - 1));//индексы с 0 начинаются
    }

    public void consoleSoutField() {//20 клеток
        for (int i = 0; i < 8; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();
        for (int i = 8; i < 16; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();
        for (int i = 16; i < 24; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();

    }

    public long getAvaliableMoney() {
        return player.getBalance();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Plant> getAvaliablePlants() {
        return avaliablePlants;
    }

    public void setAvaliablePlants(List<Plant> avaliablePlants) {
        this.avaliablePlants = avaliablePlants;
    }

    public List<Building> getAvaliableBuildings() {
        return avaliableBuildings;
    }

    public void setAvaliableBuildings(List<Building> avaliableBuildings) {
        this.avaliableBuildings = avaliableBuildings;
    }
}
