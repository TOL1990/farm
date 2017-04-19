package model.Server.connection.src.main.java.com.fenix.user.controller;

import com.aad.myutil.server.client.MYAuthorizationIF;
import com.aad.myutil.server.client.MYClientIF;
import com.aad.myutil.server.client.MYLoginIF;
import model.Server.connection.src.main.java.com.fenix.user.model.User;

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

        String deviceInfo = myLoginIF.getParams()[0];//// todo gameservis login
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
}
