package model.Server;

import model.entity.Player;
import model.service.PlayerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class ServerThread extends  Thread{
    List<ServerThread> connections;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String nick = "";
    private Server server;

    public ServerThread(Socket socket, List<ServerThread> connections, Server server) {
        this.socket = socket;
        this.connections = connections;
        this.server = server;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void run() {
        try {

            authorisation();
            synchronized (connections) {
                for (ServerThread con :
                        connections) {
                    con.out.println(nick + " connected");
                }
            }

            String msg = "";
            while (true) {
                msg = in.readLine();
                if (msg.equals("exit")) break;

                synchronized (connections) {
                    for (ServerThread con :
                            connections) {
                        con.out.println(nick + ": " + msg);
                    }
                }
            }
            synchronized (connections) {
                synchronized (connections) {
                    for (ServerThread con :
                            connections) {
                        con.out.println(nick + " has left.");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();

        }
    }

    /**
     * method login user. If login isn't exist we create new user.
     */
    private boolean authorisation() {

        while (true) {
            try {
                out.println("Write a login");
                nick = in.readLine();

                out.println("Write a password");
                String pass = in.readLine();
                Player loginedPlayer = new Player(nick, pass);
                ArrayList<Player> players = (ArrayList<Player>) new PlayerService().getAllPlayers();

                if (isLoginExist(nick, players)) {
                    if (isPasswordCorrect(loginedPlayer, players)) {
                        break; // If login and password correct break from authorization
                    } else {
                        out.println("Wrong password, Try again.");
                        continue;
                    }
                } else {
                    addNewUser(nick, pass);
                    out.println("new User added");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean isPasswordCorrect(Player loginedPlayer, ArrayList<Player> players) {
        for (Player pl :
                players) {
            if (pl.getNick().equals(loginedPlayer.getNick())) {
                if (pl.getPassword().equals(loginedPlayer.getPassword())) return true;
                else return false;
            }
        }
        return false;
    }

    private boolean isLoginExist(String nickName, ArrayList<Player> players) {
        for (Player pl :
                players) {
            if (pl.getNick().equals(nickName)) return true;
        }
        return false;
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();

            connections.remove(this);
            if (connections.size() == 0) {
                server.closeAll();
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("Threads weren't close");
            e.printStackTrace();
        }
    }

    /**
     * Add new user to DB
     */
    public void addNewUser(String nickName, String password) {
        new PlayerService().addPlayer(nickName, password);
    }

}
