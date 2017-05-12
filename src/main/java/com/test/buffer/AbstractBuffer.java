package com.test.buffer;

import com.aad.myutil.server.MYServer;
import com.aad.myutil.server.client.MYCommandIF;
import com.aad.myutil.server.client.MYCommandKeyIF;
import com.test.controller.COMMAND_FAMILY;
import com.test.controller.CommandExecutor;
import org.json.simple.JSONObject;

/**
 * Created by Andrew.
 */
public abstract class AbstractBuffer<T extends Enum<T> & MYCommandKeyIF> implements MYCommandIF<T>
{
    private COMMAND_FAMILY commandFamily;

    public AbstractBuffer(COMMAND_FAMILY commandFamily)
    {
        this.commandFamily = commandFamily;
    }

    protected  void sendData(long userId, T command, JSONObject response)
    {
        byte[] bytes = CommandExecutor.INSTANCE.parseMessage(commandFamily, command, response);
        MYServer.sendDataToClient(userId, bytes);
    }
}
