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


    public List<Cell> cells;

    public Field() {
        cells = new ArrayList<Cell>();
    }

    public void createEmptyCells() {
        if(cells != null) {
            for (int i = 1; i < HORIZONTAL_CELL+1; i++) {
                for (int j = 1; j < VERTICAL_CELL+1; j++) {
                    cells.add(new EmptyCell(i, j)); //засадим пустыми клетками
                }
            }
        }
        else
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

    public void consoleSoutField()
    {//20 клеток
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
}
