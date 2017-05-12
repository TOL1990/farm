package com.test.user.conlroller;

import com.test.user.dao.PlayerDao;
import com.test.user.entity.Player;
import com.test.util.FactoryDao;

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

    public void updatePlayerBallance(Player player) { playerDao.updatePlayerBallance(player);}
}
