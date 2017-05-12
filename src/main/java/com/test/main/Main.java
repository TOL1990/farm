package com.test.main;

import com.aad.myutil.logger.MYLoggerFactory;
import com.aad.myutil.server.MYServer;
import com.test.buffer.BufferStatic;
import com.test.controller.CommandExecutor;
import com.test.fenix.user.controller.AuthorizationBuffer;

/**
 * Created by Andrew.
 */
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            BufferStatic.initBuffers();
            final MYServer server = new MYServer(AuthorizationBuffer.INSTACE, CommandExecutor.INSTANCE);
            server.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
    }
}
