package model.Server.connection.src.main.java.com.fenix.util;

/**
 * Created by Taras. Клас нужен для передачи  инфы о клетке поля
 */
public class TransferCellInfo {
    public int x;
    public int y;
    public String typeName;
    public String name;// название строения или растения. Reactor, Carrot, Cabbage

    public String planted; //когда посажено
    public long proseed;
    public long growTime;

    public int timeBonus;
    public int proseedBonus;

    public TransferCellInfo() {

    }

    public TransferCellInfo(int x, int y, String typeName, String name, String planted) {
        this.x = x;
        this.y = y;
        this.typeName = typeName;
        this.name = name;
        this.planted = planted;
    }

    public TransferCellInfo(int x, int y, String typeName, String name, String planted, long proseed, int growTime, int timeBonus, int proseedBonus) {
        this.x = x;
        this.y = y;
        this.typeName = typeName;
        this.name = name;
        this.planted = planted;
        this.proseed = proseed;
        this.growTime = growTime;
        this.timeBonus = timeBonus;
        this.proseedBonus = proseedBonus;
    }
}
