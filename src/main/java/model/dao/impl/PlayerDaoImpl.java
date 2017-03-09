package model.dao.impl;

import model.dao.PlayerDao;
import model.dao.utils.DaoUtils;
import model.entity.Player;
import model.service.propertyconfig.QueryConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class PlayerDaoImpl implements PlayerDao {
private static long PLAYERS_STARTING_MONEY = 1000;
    private static List<Player> playersList;

    public static List<Player> getAllPlayersFromDB() {

        Connection connection = DaoUtils.getConnection();
        List<Player> players = new ArrayList<Player>();


        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QueryConfig.GET_ALL_PLAYERS);

            while (rs.next()) {
                int id = rs.getInt("id_player");
                String nick = rs.getString("nick_name");
                String password = rs.getString("password");
                long balance = (long) rs.getInt("ballance");
                players.add(new Player(id, nick, password, balance));
            }

            DaoUtils.close(connection, statement, rs);
        } catch (SQLException e) {
            System.err.println("Can't getting players from DB");
            e.printStackTrace();
        }
        return players;
    }

    public void addPlayer(Player player) {
        Connection connection = DaoUtils.getConnection();
        ////гдето добавить стартовые бабки юзеру
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueryConfig.ADD_PLAYER);

            statement.setString(1, player.getNick());
            statement.setString(2, player.getPassword());
            statement.setInt(3, (int) PLAYERS_STARTING_MONEY); // Скользкое место. Если клиент богач
            statement.executeUpdate();
            updatePlayersCash();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Player> getAllPlayers() {
        if (playersList == null) {
            playersList = getAllPlayersFromDB();
            return playersList;
        } else
            return playersList;
    }

    public void updatePlayersCash() {
        PlayerDaoImpl.playersList = getAllPlayersFromDB();
    } // переписать
}
