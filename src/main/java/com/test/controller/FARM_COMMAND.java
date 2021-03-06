package com.test.controller;

import com.aad.myutil.server.client.MYCommandKeyIF;

public enum FARM_COMMAND implements MYCommandKeyIF<Integer> {
    UNKNOW(-1),  FARM_STATUS(1), GET_AVAILABLE_BUILDINGS(2), GET_AVAILABLE_PLANTS(3), PICK_UP_PLANT(4),
    PLANT_NOT_GROWN(5), SET_PLANT(6), DELETE_PLANT(7), SET_BUILDING(8), DELETE_BUILDING(9), MONEY_BALANCE(10), FARM_ERROR(11);

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
