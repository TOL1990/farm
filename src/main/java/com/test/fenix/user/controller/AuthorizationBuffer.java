package com.test.fenix.user.controller;

import com.aad.myutil.logger.MYLoggerFactory;
import com.aad.myutil.server.client.MYAuthorizationIF;
import com.aad.myutil.server.client.MYClientIF;
import com.aad.myutil.server.client.MYLoginIF;
import com.test.Area.controller.AreaManager;
import com.test.field.controller.FieldManager;
import com.test.player.conlroller.PlayerManager;
import com.test.player.entity.Player;
import com.test.util.propertyconfig.LoginErrorConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew.
 */
public enum AuthorizationBuffer implements MYAuthorizationIF
{
    INSTACE;

    @Override
    public MYClientIF login(MYLoginIF myLoginIF)
    {
        MYClientIF clientIF = null;
        try
        {
            String nickName = myLoginIF.getParams()[0];
            String pass = myLoginIF.getParams()[1];

            Player findPlayer = PlayerManager.INSTANCE.getPlayerByNick(nickName);

            Player loginPlayer = new Player(nickName, pass);

            long userId = -1;
            if (findPlayer != null)
            {
                if (isPasswordCorrect(loginPlayer, findPlayer))
                {
                    // PlayerManager.INSTANCE.addPlayer(findPlayer.getId(), findPlayer);
                    clientIF = findPlayer;
                }
                else
                {
                    myLoginIF.getParams()[0] = LoginErrorConfig.WRONG_LOGIN_AND_PASSWORD;
                }
            }
            if (clientIF != null)
            {
                clientIF.setOnline(true);
            }
        }
        catch (Exception e)
        {
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
        return clientIF;
    }

    @Override
    public MYClientIF registration(MYLoginIF myLoginIF)
    {
        MYClientIF clientIF = null;
        try
        {
            String nickName = myLoginIF.getParams()[0];
            String pass = myLoginIF.getParams()[1];

            Player findPlayer = PlayerManager.INSTANCE.getPlayerByNick(nickName);
            if (findPlayer != null)
            {
                System.out.println("GO fuck yourself, name already exist");
                return null;
            }
            Player newPlayer = PlayerManager.INSTANCE.addPlayer(nickName, pass);
            FieldManager.INSTANCE.addField(newPlayer.getId());
            long fieldId = FieldManager.INSTANCE.getFieldByUserId(newPlayer.getId()).getId();
            AreaManager.INSTANCE.addNewArea(fieldId); //до

            clientIF = login(myLoginIF);

        }
        catch (Exception e)
        {
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
        return clientIF;
    }

    @Override
    public void loggon(long playerId)
    {
        Player player = PlayerManager.INSTANCE.getPlayer(playerId);
        if (player != null)
        {
            player.setOnline(false);
        }
    }

    @Override //не переопределен
    public List<Long> getClientsAll(boolean onlyOnline)
    {
        List<Long> userIds = new ArrayList<Long>(PlayerManager.INSTANCE.getAllPlayers().keySet());
        return userIds;
//        List<User> allUsers = UserManager.INSTANCE.getAllUsers();
//        Iterator<User> iter = allUsers.iterator();
//        User user;
//        while (iter.hasNext())
//        {
//            user = iter.next();
//            if ((onlyOnline && user.isOnline()) || !onlyOnline)
//            {
//                userIds.add(user.getId());
//            }
//        }
    }

    private boolean isPasswordCorrect(Player loginingPlayer, Player findPlayer)
    {
        if (loginingPlayer.getPassword().equals(findPlayer.getPassword()))
        {
            return true;
        }
        return false;
    }

}
