package model.Server.connection.src.main.java.com.fenix.user.controller;

import model.Server.connection.src.main.java.com.fenix.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Andrew.
 */
public enum UserManager
{
    INSTANCE;

    private Map<Long, User> users;
    private Map<String, Long> usersByDeviceInfo;
    private AtomicLong idGenerator;

    UserManager()
    {
        users = new ConcurrentHashMap<>();
        usersByDeviceInfo = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong(0);
    }

    public User addUser(String deviceInfo)
    {
        long id = idGenerator.incrementAndGet();
        User user = new User(id, deviceInfo);
        users.put(id, user);
        usersByDeviceInfo.put(deviceInfo, id);

        return user;
    }

    public User getUser(long userId)
    {
        return users.get(userId);
    }

    public User getUserByDeviceInfo(String deviceInfo)
    {
        User user = null;
        Long userId = usersByDeviceInfo.get(deviceInfo);
        if (userId != null)
        {
            user = getUser(userId);
        }
        return user;
    }

    public List<User> getAllUsers()
    {
        return new ArrayList<>(users.values());
    }
}
