package com.test;

import oldStaff.service.GameService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras on 19.04.2017.
 */
public enum GameManager {
    INSTANCE;

    private Map<Long, GameService> gameServices;

    GameManager() {
        gameServices = new ConcurrentHashMap<>();
    }

    public GameService getGameService(long userId) {
        GameService gameService = gameServices.get(userId);
        if (gameService == null) {
            gameService = addGameService(userId);
        }
        return gameService;
    }

    private GameService addGameService(long userId) {
        GameService gameService = new GameService();
        gameServices.put(userId, gameService);

        return gameService;
    }

    public void addGameService(long userId,GameService gameService) {
        gameServices.put(userId, gameService);
    }
}