package model.Server.connection.src.main.java.com.fenix.command.controller;

import com.aad.myutil.server.client.MYCommandKeyIF;

public enum FARM_COMMAND implements MYCommandKeyIF<Integer> {
    UNKNOW(-1),  FARM_STATUS(1), GET_AVAILABLE_BUILDINGS(2), GET_AVAILABLE_PLANTS(3), PICK_UP_PLANT(11), PLANT_NOT_GROWN(12);

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
