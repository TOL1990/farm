package com.test.buffer;

import com.test.controller.COMMAND_FAMILY;
import com.test.controller.CommandExecutor;

/**
 * Created by Andrew.
 */
public class BufferStatic
{
    public static void initBuffers()
    {
        CommandExecutor.INSTANCE.addCommands(COMMAND_FAMILY.LOGIN, new LoginBuffer());
        CommandExecutor.INSTANCE.addCommands(COMMAND_FAMILY.FARM, new FarmBuffer());
        CommandExecutor.INSTANCE.addCommands(COMMAND_FAMILY.WORLD, new WorldBuffer());
    }
}
