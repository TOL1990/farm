package model.Server.connection.src.main.java.com.fenix.util;

/**
 * Created by Taras. Клас нужен для передачи  инфы о клетке поля
 */
public class TransferCellInfo {
   public int x;
    public  int y;
    public String typeName;
    public String name;// название строения или растения. Reactor, Carrot, Cabbage
    public String planted; //когда посажено

    public TransferCellInfo() {

    }
    public TransferCellInfo(int x, int y, String typeName, String name, String planted) {
        this.x = x;
        this.y = y;
        this.typeName = typeName;
        this.name = name;
        this.planted = planted;
    }
}
