package com.test.controller;

import com.aad.myutil.server.client.MYCommandIF;
import com.aad.myutil.server.client.MYCommandKeyIF;
import com.aad.myutil.server.client.MYCommandParserIF;
import com.aad.myutil.server.client.MYLoginIF;
import com.test.util.JSONHelper;
import com.test.util.KEYS;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew.
 */
public enum CommandExecutor implements MYCommandParserIF {
    INSTANCE;

    private Map<Enum, MYCommandIF> commands;

    CommandExecutor() {
        commands = new HashMap<>();
    }

    @Override
    public void addCommands(Enum anEnum, MYCommandIF myCommandIF) {
        commands.put(anEnum, myCommandIF);
    }

    @Override
    public Map<Enum, MYCommandIF> getCommandExecutors() {
        return commands;
    }

    @Override
    public Map.Entry<Enum[], Object> parseCommands(byte[] bytes) {
        JSONObject json = JSONHelper.bytesToJSON(bytes);
        if (json != null) {
            int commandFamilyId = Integer.valueOf(json.get(KEYS.COMMAND_FAMILY.getKey()).toString());
            Enum commandFamily = COMMAND_FAMILY.valueOf(commandFamilyId);

            MYCommandIF commandIF = commands.get(commandFamily);
            int commandId = Integer.valueOf(json.get(KEYS.COMMAND.getKey()).toString());
            Enum command = commandIF.getCommand(commandId);

            Enum[] commands = new Enum[]{
                    commandFamily,
                    command
            };

            Object data = json.get(KEYS.DATA.getKey());

            System.out.println(json.toString());

            return new AbstractMap.SimpleEntry<Enum[], Object>(commands, data);
        }
        return null;
    }

    @Override
    public byte[] parseMessage(MYCommandKeyIF var1, MYCommandKeyIF var2, Object o) {
        JSONObject json = new JSONObject();
        json.put(KEYS.COMMAND_FAMILY.getKey(), var1.getKey());
        json.put(KEYS.COMMAND.getKey(), var2.getKey());
        json.put(KEYS.DATA.getKey(), o);
        return JSONHelper.JSONToBytes(json);
    }

    @Override
    public byte[] getAutorityCommand() {
        JSONObject json = new JSONObject();
        json.put(KEYS.COMMAND_FAMILY.getKey(), COMMAND_FAMILY.LOGIN.getKey());
        json.put(KEYS.COMMAND.getKey(), LOGIN_COMMAND.DEVICE_INFO.getKey());
        JSONObject param = new JSONObject();
        param.put(KEYS.MODEL_DATA.getKey(), true);
        json.put(KEYS.DATA.getKey(), param);
        return JSONHelper.JSONToBytes(json);
    }

    @Override
    public byte[] getFailCommand(MYLoginIF myLoginIF) {
        JSONObject json = new JSONObject();
        json.put(KEYS.COMMAND_FAMILY.getKey(), COMMAND_FAMILY.LOGIN.getKey());
        Integer commandId = Integer.valueOf(myLoginIF.getParams()[0]);
        LOGIN_COMMAND command = LOGIN_COMMAND.valueOf(commandId);
        json.put(KEYS.COMMAND.getKey(), command.getKey());

        JSONObject param = new JSONObject();
        param.put(KEYS.MODEL_DATA.getKey(), myLoginIF.getParams()[1]);
        json.put(KEYS.DATA.getKey(), param);
        return JSONHelper.JSONToBytes(json);
    }

    @Override
    public MYLoginIF parseAuthorityInfo(byte[] bytes) {
        JSONObject json = JSONHelper.bytesToJSON(bytes);
        JSONObject data = (JSONObject) json.get(KEYS.DATA.getKey());
        JSONArray arr = (JSONArray) data.get(KEYS.MODEL_DATA.getKey());
        String login = JSONHelper.getString(arr.get(0));
        String pass = JSONHelper.getString(arr.get(1));
//        String deviceInfo = JSONHelper.getString(arr.get(0));

        String[] params = new String[]{
                login, pass
        };

        System.out.println("Authority params = " + params);

        return () -> params;
    }
}
