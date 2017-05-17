package com.test.fenix.user.controller;

import com.aad.myutil.logger.MYLoggerFactory;
import com.aad.myutil.server.client.MYAuthorizationIF;
import com.aad.myutil.server.client.MYClientIF;
import com.aad.myutil.server.client.MYLoginIF;
import com.test.Area.controller.AreaManager;
import com.test.GameManager;
import com.test.fenix.user.model.User;
import com.test.field.controller.FieldManager;
import com.test.player.conlroller.PlayerManager;
import com.test.player.entity.Player;
import com.test.util.propertyconfig.LoginErrorConfig;
import oldStaff.service.GameService;

import java.util.ArrayList;
import java.util.Iterator;
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

            Player findPlayer= PlayerManager.INSTANCE.getPlayerByNick(nickName);

            Player loginPlayer = new Player(nickName, pass);

            long userId = -1;
            if (findPlayer != null)
            {
                if (isPasswordCorrect(loginPlayer, findPlayer))
                {
                    PlayerManager.INSTANCE.addPlayer(findPlayer.getId(), findPlayer);

                    clientIF = findPlayer;
                }
                else
                {
                    myLoginIF.getParams()[0] = LoginErrorConfig.WRONG_LOGIN_AND_PASSWORD;
                    // out.println("Wrong password, Try again."); //заслать неверный пароль
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
                System.out.println("Ииди нахуй, такой пиздюк уже есть");
                return null;
            }
           Player newPlayer =  PlayerManager.INSTANCE.addPlayer(nickName, pass);
            FieldManager.INSTANCE.addField(newPlayer.getId());
            long fieldId = FieldManager.INSTANCE.getFieldByUserId(newPlayer.getId()).getId();
            AreaManager.INSTANCE.addNewArea(fieldId); //добавляем новую ферму на карту мира
            
            clientIF = login(myLoginIF);
          //  long userId = -1;

//            if (clientIF != null)
//            {
//                clientIF.setOnline(true);
//            }
        // Не нужно т.к. этот функционал выполняется в 
        //    PlayerManager.INSTANCE.addPlayer(findPlayer.getId(), findPlayer);
        }
        catch (Exception e)
        {
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
        return clientIF;
    }

    @Override
    public void loggon(long userId)
    {
        //ToDO: Переделать на свой manager
        GameService gameService = GameManager.INSTANCE.getGameService(userId);
        Player player = gameService.getPlayer();
        if (player != null)
        {
            player.setOnline(false);
        }
    }

    @Override //не переопределен
    public List<Long> getClientsAll(boolean onlyOnline)
    {
        List<Long> userIds = new ArrayList<>();
        //TODO: Переделать на свой manager
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

    private boolean isPasswordCorrect(Player loginingPlayer, Player findPlayer)
    {
        if (loginingPlayer.getPassword().equals(findPlayer.getPassword()))
        {
            return true;
        }
        return false;
    }

}
