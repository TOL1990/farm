package model.service;

import model.dao.util.FactoryDao;
import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class PlayerService {
    public List<Player> getAllPlayers() {
        return FactoryDao.getInstance().getPlayerDao().getAllPlayers();
    }

    public void addPlayer(String nick, String password) {
        FactoryDao.getInstance().getPlayerDao().addPlayer(new Player(nick, password));
    }
}
