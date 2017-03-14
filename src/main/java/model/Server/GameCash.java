package model.Server;

import model.entity.Field;
import model.entity.Player;
import model.service.FieldService;
import model.service.PlayerService;

/**
 * Created by Taras on 10.03.2017.
 */
public class GameCash {
    private Field field;
    private Player player;

    public GameCash() {
    }

    public GameCash(Player player) {
        this.player = player;
    }

    public GameCash(Field field, Player player) {
        this.field = field;
        this.player = player;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void init(Player player) {
        this.player = player;
        PlayerService playerService = new PlayerService();
        this.player = playerService.getPlayerByNick(player.getNick());
        FieldService fs = new FieldService();
        this.field = fs.getField(player);
    }
}
