package com.test.fenix.user.model;

import com.aad.myutil.server.client.MYClientIF;

/**
 * Created by Andrew.
 */
public class User implements MYClientIF
{
    private long id;
    private String deviceInfo;
    private boolean online;

    public User(long id, String deviceInfo)
    {
        this.id = id;
        this.deviceInfo = deviceInfo;
    }

    public long getId()
    {
        return id;
    }

    @Override
    public void setOnline(boolean online)
    {
        this.online = online;
    }

    @Override
    public boolean isOnline()
    {
        return online;
    }

    @Override
    public String getName()
    {
        return "User" + id;
    }

    public String getDeviceInfo()
    {
        return deviceInfo;
    }
}
