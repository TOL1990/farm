package com.test.player.conlroller;

import com.test.player.dao.PlayerDao;
import com.test.player.entity.Player;
import com.test.util.FactoryDao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras.
 */
public enum PlayerManager
{
    
    //переделать. не нужно Юзер менеджере. чтобы все хранилось в мапе. нужно просто пробрасывать просьбы в Даошку.
    INSTANCE;
    private Map<Long, Player> players;
    //  private Map<String, Long> playersByNick;
    private PlayerDao playerDao;

    PlayerManager()
    {
        players = new ConcurrentHashMap<>();
        this.playerDao = FactoryDao.getInstance().getPlayerDao();
    }

    public Player getPlayer(long playerId)
    {
        Player player = players.get(playerId);
        if (player == null)
        {
            player = addPlayer(playerId);
           // System.out.println("Ошибка от клиента если такоего нету");
        }
        return player;
    }

    private Player addPlayer(long playerId)
    {
        Player player = playerDao.getPlayerById(playerId);
        players.put(playerId, player);
        return player;
    }

    public Player getPlayerByNick(String nick)
    {
        return playerDao.getPlayerByNick(nick);
    }


    public void addPlayer(long playerId, Player player)
    {
        players.put(playerId, player);
    }

    public void updatePlayerBallance(Player player)
    {
        playerDao.updatePlayerBallance(player);
    }

    public Player addPlayer(String nickName, String password)
    {
        Player newGuy = new Player(nickName, password);
        playerDao.addPlayer(newGuy);
        newGuy = getPlayerByNick(nickName);
        if (newGuy != null)
        {
            addPlayer(newGuy.getId(), newGuy);
        }
        return newGuy;
    }
}