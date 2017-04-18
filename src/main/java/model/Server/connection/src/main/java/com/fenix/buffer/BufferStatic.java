package model.Server.connection.src.main.java.com.fenix.buffer;

import model.Server.connection.src.main.java.com.fenix.command.controller.COMMAND_FAMILY;
import model.Server.connection.src.main.java.com.fenix.command.controller.CommandExecutor;

/**
 * Created by Andrew.
 */
public class BufferStatic
{
    public static void initBuffers()
    {
        CommandExecutor.INSTANCE.addCommands(COMMAND_FAMILY.LOGIN, new LoginBuffer());
    }
}
