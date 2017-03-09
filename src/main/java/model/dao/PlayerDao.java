package model.dao;

import model.entity.Player;

import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public interface PlayerDao {
    //    public Player findById(Long id);
    public List<Player> getAllPlayers();

    public void addPlayer(Player player);
//    public void updatePlayer(Long player_id, Player player) ;
//    public Player getPlayerById(Long empl_id);
//    public void deletePlayer(Player player) ;

}
