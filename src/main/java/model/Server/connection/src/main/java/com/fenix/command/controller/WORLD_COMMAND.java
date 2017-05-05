package model.Server.connection.src.main.java.com.fenix.command.controller;

import com.aad.myutil.server.client.MYCommandKeyIF;

/**
 * Created by Taras on 28.04.2017.
 */
public enum WORLD_COMMAND implements MYCommandKeyIF<Integer> {
    UNKNOW(-1), GET_AREA(1), GET_HOME_AREA(2);

    private Integer key;

    WORLD_COMMAND(Integer key)
    {
        this.key = key;
    }

    @Override
    public Integer getKey()
    {
        return key;
    }

    public static WORLD_COMMAND valueOf(int key)
    {
        for (WORLD_COMMAND e : WORLD_COMMAND.values())
        {
            if (e.key == key)
            {
                return e;
            }
        }
        return null;
    }
}
