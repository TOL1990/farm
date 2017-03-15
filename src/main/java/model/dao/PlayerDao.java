package model.dao;

import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public interface PlayerDao {
    //    public Player findById(Long id);
     List<Player> getAllPlayers();

     void addPlayer(Player player);

    Player getPlayerByNick(String nick);
//    public void updatePlayer(Long player_id, Player player) ;
    public Player getPlayerById(long playerId);
//    public void deletePlayer(Player player) ;

}
