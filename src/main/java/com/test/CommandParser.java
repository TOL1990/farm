package com.test;

import com.aad.myutil.server.client.MYCommandIF;
import com.aad.myutil.server.client.MYCommandKeyIF;
import com.aad.myutil.server.client.MYCommandParserIF;
import com.aad.myutil.server.client.MYLoginIF;

import java.util.Map;

/**
 * Created by Taras on 13.04.2017.
 */
public class CommandParser implements MYCommandParserIF {
    public void addCommands(Enum anEnum, MYCommandIF myCommandIF) {

    }

    public Map<Enum, MYCommandIF> getCommandExecutors() {
        return null;
    }

    public Map.Entry<Enum[], Object> parseCommands(byte[] bytes) {
        return null;
    }

    public byte[] parseMessage(MYCommandKeyIF myCommandKeyIF, MYCommandKeyIF myCommandKeyIF1, Object o) {
        return new byte[0];
    }

    public byte[] getAutorityCommand() {
        return new byte[0];
    }

    public byte[] getFailCommand(MYLoginIF myLoginIF) {
        return new byte[0];
    }

    public MYLoginIF parseAuthorityInfo(byte[] bytes) {
        return null;
    }
}
