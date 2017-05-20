package com.test.player.dao;


import com.test.player.entity.Player;

import java.util.Map;

/**
 * Created by Taras on 09.03.2017.
 */
public interface PlayerDao
{
    Map<Long, Player> getAllPlayers();

    Player addPlayer(Player player);

    Player getPlayerByNick(String nick);

    Player getPlayerById(long playerId);

    void updatePlayerBallance(Player player);

}
