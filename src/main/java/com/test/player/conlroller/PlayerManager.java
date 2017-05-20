package com.test.player.conlroller;

import com.test.player.dao.PlayerDao;
import com.test.player.entity.Player;
import com.test.util.FactoryDao;

import java.util.Map;

/**
 * Created by Taras.
 */
public enum PlayerManager
{
    
    //переделать. не нужно Юзер менеджере. чтобы все хранилось в мапе. нужно просто пробрасывать просьбы в Даошку.
    INSTANCE;
    private PlayerDao playerDao;

    PlayerManager()
    {

        this.playerDao = FactoryDao.getInstance().getPlayerDao();
    }

    public Player getPlayer(long playerId)
    {
        return playerDao.getPlayerById(playerId);
    }


    public Player getPlayerByNick(String nick)
    {
        return playerDao.getPlayerByNick(nick);
    }


    public void updatePlayerBallance(Player player)
    {
        playerDao.updatePlayerBallance(player);
    }

    public Player addPlayer(String nickName, String password)
    {

        return playerDao.addPlayer(new Player(nickName, password));
    }

    public Map<Long, Player> getAllPlayers()
    {
        return playerDao.getAllPlayers();
    }
}