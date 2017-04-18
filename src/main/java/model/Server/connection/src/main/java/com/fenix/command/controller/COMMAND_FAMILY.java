package model.Server.connection.src.main.java.com.fenix.command.controller;

import com.aad.myutil.server.client.MYCommandKeyIF;

/**
 * Created by Andrew.
 */
public enum COMMAND_FAMILY implements MYCommandKeyIF<Integer>
{
    TEST(1), LOGIN(2), FARM(3);
    private Integer key;

    COMMAND_FAMILY(Integer key)
    {
        this.key = key;
    }

    @Override
    public Integer getKey()
    {
        return key;
    }

    public static COMMAND_FAMILY valueOf(int key)
    {
        for (COMMAND_FAMILY e : COMMAND_FAMILY.values())
        {
            if (e.key.intValue() == key)
            {
                return e;
            }
        }
        return null;
    }
}
