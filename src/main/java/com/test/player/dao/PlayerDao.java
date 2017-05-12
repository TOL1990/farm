package com.test.player.dao;


import com.test.player.entity.Player;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public interface PlayerDao
{
    //    public Player findById(Long id);
    List<Player> getAllPlayers();

    void addPlayer(Player player);

    Player getPlayerByNick(String nick);

    public Player getPlayerById(long playerId);

    void updatePlayerBallance(Player player);
//    public void deletePlayer(Player player) ;

}
