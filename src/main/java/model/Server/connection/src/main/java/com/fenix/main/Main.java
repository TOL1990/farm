package model.Server.connection.src.main.java.com.fenix.main;

import com.aad.myutil.logger.MYLoggerFactory;
import com.aad.myutil.server.MYServer;
import model.Server.connection.src.main.java.com.fenix.buffer.BufferStatic;
import model.Server.connection.src.main.java.com.fenix.command.controller.CommandExecutor;
import model.Server.connection.src.main.java.com.fenix.user.controller.AuthorizationBuffer;

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
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
    }
}
