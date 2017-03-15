package model.service;

import model.dao.PlayerDao;
import model.dao.util.FactoryDao;
import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class PlayerService {
    private PlayerDao playerDao;

    public PlayerService() {
        this.playerDao = FactoryDao.getInstance().getPlayerDao();
    }

    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    public void addPlayer(String nick, String password) {
        playerDao.addPlayer(new Player(nick, password));
    }

    public Player getPlayerByNick(String nick) {
        return playerDao.getPlayerByNick(nick);
    }
}
