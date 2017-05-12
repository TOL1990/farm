package com.test.util;

/**
 * Created by Andrew.
 */
public enum KEYS
{
    COMMAND_FAMILY("-cmf"),
    COMMAND("-cm"),
    DATA("-dt"),
    GAME_ID("-gi"),
    SUCCESS("-sc"),
    DECK_SIZE("-ds"),
    TRUMP("-tr"),
    MODEL_DATA("-md");

    private String key;

    KEYS(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
