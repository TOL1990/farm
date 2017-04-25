package model.entity;

import com.aad.myutil.server.client.MYClientIF;

import java.math.BigDecimal;

/**
 * Created by Taras on 09.03.2017.
 */
public class Player implements MYClientIF{
    private long id;
    private String nick;
    private String password;
    private long  balance;
    private boolean online;

    public Player(long id, String nick, String password, long balance) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.balance = balance;
    }

    public Player(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }

    public Player(long id, String nick, String password) {
        this.id = id;
        this.nick = nick;
        this.password = password;
    }

    public Player(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public void setOnline(boolean online)
    {
        this.online = online;
    }

    @Override
    public boolean isOnline() {return online;}

    @Override
    public String getName() {return nick;}

    public void setId(long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (id != player.id) return false;
        if (balance != player.balance) return false;
        if (!nick.equals(player.nick)) return false;
        return password != null ? password.equals(player.password) : player.password == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + nick.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) (balance ^ (balance >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
