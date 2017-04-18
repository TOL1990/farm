package model.Server.connection.src.main.java.com.fenix.command.controller;

import com.aad.myutil.server.client.MYCommandKeyIF;

public enum FARM_COMMAND implements MYCommandKeyIF<Integer> {
    UNKNOW(-1), LOGIN(1), REGISTRATION(2), DEVICE_INFO(3), QUIT(4), LOGIN_FAIL(5), LOGIN_SUCCESS(6), FARM_INFO(7);

    private Integer key;

    FARM_COMMAND(Integer key) {
        this.key = key;
    }

    public static FARM_COMMAND valueOf(int key) {
        for (FARM_COMMAND e : FARM_COMMAND.values()) {
            if (e.key == key) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Integer getKey() {
        return key;
    }
}
