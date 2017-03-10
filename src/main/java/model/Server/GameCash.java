package model.Server;

import model.entity.Field;
import model.entity.Player;

/**
 * Created by Taras on 10.03.2017.
 */
public class GameCash {
    private Field field;
    private Player player;
    private static GameCash ourInstance = new GameCash();

    public static GameCash getInstance() {
        return ourInstance;
    }

    private GameCash() {
    }
}
