package com.test.controller;

import com.aad.myutil.server.client.MYCommandKeyIF;

/**
 * Created by Andrew.
 */
public enum LOGIN_COMMAND implements MYCommandKeyIF<Integer>
{
    UNKNOW(-1), LOGIN(1), REGISTRATION(2), DEVICE_INFO(3), QUIT(4), LOGIN_FAIL(5), LOGIN_SUCCESS(6), OTVETKA(7), LOGIN_ERROR(8);

    private Integer key;

    LOGIN_COMMAND(Integer key)
    {
        this.key = key;
    }

    @Override
    public Integer getKey()
    {
        return key;
    }

    public static LOGIN_COMMAND valueOf(int key)
    {
        for (LOGIN_COMMAND e : LOGIN_COMMAND.values())
        {
            if (e.key == key)
            {
                return e;
            }
        }
        return null;
    }
}
