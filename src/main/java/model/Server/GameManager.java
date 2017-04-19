package model.Server;

import model.service.GameService;

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
            gameService = addGameservice(userId);
        }
        return gameService;
    }

    private GameService addGameservice(long userId) {
        GameService gameService = new GameService();
        gameServices.put(userId, gameService);

        return gameService;
    }
}
