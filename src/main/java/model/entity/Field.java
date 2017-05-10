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
    private List<Plant> allPlants;
    private List<Building> allBuildings;

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
        this.allPlants = copyField.getAllPlants();
        this.allBuildings = copyField.getAllBuildings();
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
            if (c.getXPosition() == xPos && c.getYPosition() == yPos) return c;
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
    public void     setCell(Cell obj, int x, int y) {
        obj.setXPosition(x);
        obj.setYPosition(y);
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getXPosition() == x && cells.get(i).getYPosition() == y)
                cells.set(i, obj);
        }
    }

    public void consoleSoutField() {
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
        for (int i = 24; i < 32; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();
        for (int i = 32; i < 40; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();
        for (int i = 40; i < 48; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();
        for (int i = 48; i < 56; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();
        for (int i = 56; i < 64; i++) {
            System.out.print(cells.get(i) + "  ");
        }
        System.out.println();

    }

    public String getConsoleSoutField() {
        StringBuilder builder = new StringBuilder();
        builder.append("Хазяин поля " + player.toString());
        builder.append("\n");

        builder.append("id = " + id);
        builder.append("\n");


        for (int i = 0; i < 8; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");
        for (int i = 8; i < 16; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");
        for (int i = 16; i < 24; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");
        for (int i = 24; i < 32; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");
        for (int i = 32; i < 40; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");
        for (int i = 40; i < 48; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");
        for (int i = 48; i < 56; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");
        for (int i = 56; i < 64; i++) {
            builder.append(cells.get(i) + "  ");
        }
        builder.append("\n");

        return builder.toString();
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

    public List<Plant> getAllPlants() {
        return allPlants;
    }

    public void setAllPlants(List<Plant> allPlants) {
        this.allPlants = allPlants;
    }

    public List<Building> getAllBuildings() {
        return allBuildings;
    }

    public void setAllBuildings(List<Building> allBuildings) {
        this.allBuildings = allBuildings;
    }
}
