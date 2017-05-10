package model.dao.impl;

import model.dao.PlayerDao;
import model.dao.utils.DaoUtils;
import model.entity.Player;
import model.service.propertyconfig.QueryConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras on 09.03.2017.
 */
public class PlayerDaoImpl implements PlayerDao
{
    private static long PLAYERS_STARTING_MONEY = 1000;
    // private static List<Player> playersList;
    private Map<Long, Player> players;
    private Map<String, Long> playersBylogin;

    public PlayerDaoImpl()
    {
        players = new ConcurrentHashMap<>();
        playersBylogin = new ConcurrentHashMap<>();
        loadPlayers();
    }

    public List<Player> getAllPlayers()
    {
        return new ArrayList<>(players.values());
    }

    private void loadPlayers()
    {

        Connection connection = DaoUtils.getConnection();

        Statement statement = null;
        ResultSet rs = null;
        try
        {
            statement = connection.createStatement();
            rs = statement.executeQuery(QueryConfig.GET_ALL_PLAYERS);

            while (rs.next())
            {
                long id = rs.getLong("id_player");
                String nick = rs.getString("nick_name");
                String password = rs.getString("password");
                long balance = (long) rs.getInt("ballance");

                players.put(id, new Player(id, nick, password, balance));
                playersBylogin.put(nick, id);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Can't getting players from DB");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(connection, statement, rs);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    //todo может и не нужен этот метод
//    public List<Player> getAllPlayers() {
//        if (playersList == null) {
//            playersList = loadPlayers();
//            return playersList;
//        } else
//            return playersList;
//    }

    public void addPlayer(Player player)
    {
        player.setBalance(PLAYERS_STARTING_MONEY);
        long id = addPlayerDB(player);
        if (id >= 0)
        {
            player.setId(id);

            players.put(id, player);
            playersBylogin.put(player.getNick(), id);
        }
    }

    public long addPlayerDB(Player player)
    {
        long ret_key = -1;
        Connection conn = DaoUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            ps = conn.prepareStatement(QueryConfig.ADD_PLAYER);

            ps.setString(1, player.getNick());
            ps.setString(2, player.getPassword());
            ps.setLong(3, player.getBalance());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                ret_key = rs.getLong(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(conn, ps, rs);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return ret_key;
    }


    /**
     * @return игрока по нику из кеша. Если кеш пустой из БД и обновляет кеш
     */
    public Player getPlayerByNick(String nick)
    {
        Player player = null;
        Long playerId = playersBylogin.get(nick);
        if (playerId != null)
        {
            player = players.get(playerId);
        }

        return player;
    }

    public Player getPlayerById(long id)
    {
        return players.get(id);
    }

    public void updatePlayerBallance(Player player)
    {
        Connection con = DaoUtils.getConnection();

        PreparedStatement statement = null;
        try
        {
            statement = con.prepareStatement(QueryConfig.UPDATE_PLAYER_BALLANCE);
            statement.setLong(1, player.getBalance());
            statement.setLong(2, player.getId());
            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(con, statement);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }


    public Player getPlayerByNickDB(String nick)
    {
        Connection connection = DaoUtils.getConnection();
        Player player = null;

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement(QueryConfig.GET_PLAYER_BY_NICK);
            preparedStatement.setString(1, nick);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                long id = rs.getLong("id_player");
                String nickName = rs.getString("nick_name");
                String password = rs.getString("password");
                long balance = rs.getLong("ballance");

                player = new Player(id, nickName, password, balance);
            }
            DaoUtils.close(connection, preparedStatement, rs);

        }
        catch (SQLException e)
        {
            System.err.println("Can't GET_PLAYER_BY_NICK from DB");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DaoUtils.close(preparedStatement);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return player;
    }
}
