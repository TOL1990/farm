package model.Server;

import model.entity.Field;
import model.entity.Player;
import model.service.GameService;
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
public class ServerThread extends Thread {
    List<ServerThread> connections;
    private List<Player> players;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String nick = "";
    private Player player;
    private Field field;
    private GameService gameService;

    private Server server;

    public ServerThread(Socket socket, List<ServerThread> connections, Server server) {
        this.socket = socket;
        this.server = server;
        this.connections = connections;

        gameService = new GameService();
        players = gameService.getAllPlayers();   //поднимаем из базы лист игроков

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void run() {
        try {

            authorisation();
            send(nick + " залогинился в игру");

            // Команды пользовательского ввода

            String msg = "";
            while (true) {
                msg = in.readLine().toUpperCase();
                String[] strArr = msg.split(" ");//розбиваем команду пользователя
//                 на составляющие и в зав.  от команды указывает параметры
                if (msg.equals("FARM STATUS")) {
                    System.out.println("msg = " + msg);
                    send(gameService.soutFarm());
                } else if (msg.equals("EXIT")) {
                    break;
                } else if (msg.equals("EXIT SERVER")) {
                    server.closeAll();
                } else if (msg.equals("GET AV Р")) {
                    send(gameService.getAllPlants().toString());
                } else if (msg.equals("GET AV B")) {
                    out.println(gameService.getAllBuildings());
                } else if (strArr[0].equals("SET") && strArr[1].equals("PLANT")) {
                    String plantName = strArr[2];
                    String x = strArr[4];
                    String y = strArr[5];
                    gameService.setPlant(plantName, x, y);
//SET PLANT Арбуз TO 2 1
                } else if (strArr[0].equals("PICK") && strArr[1].equals("UP")) {
                    String x = strArr[3];
                    String y = strArr[4];
                    gameService.pickupPlant(x, y);
                } else if (strArr[0].equals("DEL") && strArr[1].equals("PLANT")) {
                    String x = strArr[2];
                    String y = strArr[3];
                    gameService.delPlant(x, y);
                } else if (strArr[0].equals("SET") && strArr[1].equals("BUILDING")) {
                    String buildingName = strArr[2];
                    String x = strArr[4];
                    String y = strArr[5];
                    gameService.setBuilding(buildingName, x, y);
                } else if (strArr[0].equals("DEL") && strArr[1].equals("PLANT")) {
                    String x = strArr[2];
                    String y = strArr[3];
                    gameService.delBuilding(x, y);
                } else {
                    out.println(msg + " Данная команда не найдена. попробуйте еще");
                    send("command:" + msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getClass().getName());
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
                send("Write a login");
                nick = in.readLine();
                send("Write a password");
                String pass = in.readLine();

                Player loginedPlayer = new Player(nick, pass);
//                ArrayList<Player> players = (ArrayList<Player>) new PlayerService().getAllPlayers();

                if (isLoginExist(nick, (ArrayList<Player>) players)) {
                    if (isPasswordCorrect(loginedPlayer, (ArrayList<Player>) players)) {
                        gameService.setPlayer(loginedPlayer); //назначаем игрока
                        gameService.getField(gameService.getPlayer());
                        break; // If login and password correct break from authorization
                    } else {
                        out.println("Wrong password, Try again.");
                        continue;
                    }
                } else {
                    gameService.addNewPlayer(nick, pass);
                    players = gameService.getAllPlayers();
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

    private void send(String message) {
        out.println(message);
        out.flush();
    }
}
