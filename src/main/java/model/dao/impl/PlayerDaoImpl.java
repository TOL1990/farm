package model.dao.impl;

import model.Server.GameCash;
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
    // private static List<Player> playersList;
    private List<Player> playersCash;
    private GameCash gameCash;

    public PlayerDaoImpl() {
//        gameCash = new GameCash();
    }

    public static List<Player> getAllPlayersFromDB() {

        Connection connection = DaoUtils.getConnection();
        List<Player> players = new ArrayList<Player>();


        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(QueryConfig.GET_ALL_PLAYERS);

            while (rs.next()) {
                long id = rs.getLong("id_player");
                String nick = rs.getString("nick_name");
                String password = rs.getString("password");
                long balance = (long) rs.getInt("ballance");
                players.add(new Player(id, nick, password, balance));
            }
        } catch (SQLException e) {
            System.err.println("Can't getting players from DB");
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(connection, statement, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    //todo может и не нужен этот метод
//    public List<Player> getAllPlayers() {
//        if (playersList == null) {
//            playersList = getAllPlayersFromDB();
//            return playersList;
//        } else
//            return playersList;
//    }
    public List<Player> getAllPlayers() {
        if (playersCash == null) {
            playersCash = getAllPlayersFromDB();
            return playersCash;
        } else
            return playersCash;
    }

    public void addPlayer(Player player) {
        Connection connection = DaoUtils.getConnection();
        ////гдето добавить стартовые бабки юзеру
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueryConfig.ADD_PLAYER);

            statement.setString(1, player.getNick());
            statement.setString(2, player.getPassword());
            statement.setLong(3, PLAYERS_STARTING_MONEY);
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


    /**
     * @return игрока по нику из кеша. Если кеш пустой из БД и обновляет кеш
     */
    public Player getPlayerByNick(String nick) {

        if (playersCash != null) {
            for (Player p :
                    playersCash) {
                if (p.getNick().equals(nick)) return p;
            }
            System.err.println("getPlayerByNick - ник в кеше не найден");
            return null; // не нашел игрока с таким ником
        }
       else
        {
            return getPlayerByNickDB(nick); //тащим юзера из БД
        }

    }


    public Player getPlayerByNickDB(String nick) {
        Connection connection = DaoUtils.getConnection();
        Player player = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_PLAYER_BY_NICK);
            preparedStatement.setString(1, nick);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id_player");
                String nickName = rs.getString("nick_name");
                String password = rs.getString("password");
                long balance = rs.getLong("ballance");

                player = new Player(id, nickName, password, balance);
            }
            DaoUtils.close(connection, preparedStatement, rs);

        } catch (SQLException e) {
            System.err.println("Can't GET_PLAYER_BY_NICK from DB");
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return player;
    }

    public void updatePlayersCash() {
        playersCash = getAllPlayersFromDB();
    } // переписать
}
