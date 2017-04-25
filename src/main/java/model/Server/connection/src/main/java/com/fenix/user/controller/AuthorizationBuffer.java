package model.Server.connection.src.main.java.com.fenix.user.controller;

import com.aad.myutil.server.client.MYAuthorizationIF;
import com.aad.myutil.server.client.MYClientIF;
import com.aad.myutil.server.client.MYLoginIF;
import model.Server.GameManager;
import model.Server.connection.src.main.java.com.fenix.user.model.User;
import model.entity.Player;
import model.service.GameService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andrew.
 */
public enum AuthorizationBuffer implements MYAuthorizationIF {
    INSTACE;

    @Override
    public MYClientIF login(MYLoginIF myLoginIF) {
        String nickName = myLoginIF.getParams()[0];
        String pass = myLoginIF.getParams()[1];

        GameService gs = new GameService();
        List<Player> players = gs.getAllPlayers();

        Player loginPlayer = new Player(nickName, pass);

        long userId = -1;
        MYClientIF clientIF = null;
        if (isLoginExist(nickName, (ArrayList<Player>) players)) {
            if (isPasswordCorrect(loginPlayer, (ArrayList<Player>) players)) {
                gs.setPlayer(loginPlayer); //назначаем игрока
                gs.getField(gs.getPlayer());
                userId = gs.getPlayer().getId();
                clientIF = gs.getPlayer();
                GameManager.INSTANCE.addGameService(userId, gs);
            } else {
                // out.println("Wrong password, Try again."); //заслать неверный пароль
            }
        }
        if (clientIF != null) {
            clientIF.setOnline(true);
        }
        return clientIF;
    }

    @Override
    public MYClientIF registration(MYLoginIF myLoginIF) {
        String nickName = myLoginIF.getParams()[0];
        String pass = myLoginIF.getParams()[1];

        GameService gs = new GameService();
        gs.addNewPlayer(nickName, pass);

        long userId = -1;
        MYClientIF clientIF = null;

        gs.setPlayer(new Player(nickName, pass)); //назначаем игрока
        gs.getField(gs.getPlayer());
        userId = gs.getPlayer().getId();
        clientIF = gs.getPlayer();
        GameManager.INSTANCE.addGameService(userId, gs);

        if (clientIF != null) {
            clientIF.setOnline(true);
        }
        return clientIF;
    }

    @Override
    public void loggon(long userId) {
        MYClientIF clientIF = UserManager.INSTANCE.getUser(userId);
        if (clientIF != null) {
            clientIF.setOnline(false);
        }
    }

    @Override //не переопределен
    public List<Long> getClientsAll(boolean onlyOnline) {
        List<Long> userIds = new ArrayList<>();
        List<User> allUsers = UserManager.INSTANCE.getAllUsers();
        Iterator<User> iter = allUsers.iterator();
        User user;
        while (iter.hasNext()) {
            user = iter.next();
            if ((onlyOnline && user.isOnline()) || !onlyOnline) {
                userIds.add(user.getId());
            }
        }
        return userIds;
    }

    private boolean isPasswordCorrect(Player loginedPlayer, ArrayList<Player> players) {
        for (Player pl :
                players) {
            if (pl.getNick().equals(loginedPlayer.getNick())) {
                if (pl.getPassword().equals(loginedPlayer.getPassword())) return true;
                else return false;
            }
        }
        return false;
    }

    private boolean isLoginExist(String nickName, ArrayList<Player> players) {
        for (Player pl :
                players) {
            if (pl.getNick().equals(nickName)) return true;
        }
        return false;
    }


/* Реализация Андрея
    @Override
    public MYClientIF login(MYLoginIF myLoginIF)
    {
        String deviceInfo = myLoginIF.getParams()[0];///
        String login = myLoginIF.getParams()[0];//
        String pass= myLoginIF.getParams()[1];///
        MYClientIF clientIF = UserManager.INSTANCE.getUserByDeviceInfo(deviceInfo);
        if (clientIF != null)
        {
            clientIF.setOnline(true);
        }
        return clientIF;
    }

    @Override
    public MYClientIF registration(MYLoginIF myLoginIF)
    {
        String deviceInfo = myLoginIF.getParams()[0];
        String login = myLoginIF.getParams()[0];////
        String pass= myLoginIF.getParams()[1];////
        MYClientIF clientIF = UserManager.INSTANCE.addUser(deviceInfo);
        if (clientIF != null)
        {
            clientIF.setOnline(true);
        }
        return clientIF;
    }

    @Override
    public void loggon(long userId)
    {
        MYClientIF clientIF = UserManager.INSTANCE.getUser(userId);
        if (clientIF != null)
        {
            clientIF.setOnline(false);
        }
    }

    @Override
    public List<Long> getClientsAll(boolean onlyOnline)
    {
        List<Long> userIds = new ArrayList<>();
        List<User> allUsers = UserManager.INSTANCE.getAllUsers();
        Iterator<User> iter = allUsers.iterator();
        User user;
        while (iter.hasNext())
        {
            user = iter.next();
            if ((onlyOnline && user.isOnline()) || !onlyOnline)
            {
                userIds.add(user.getId());
            }
        }
        return userIds;
    }
  /*  */
}
