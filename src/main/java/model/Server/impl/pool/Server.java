package model.Server.impl.pool;

import model.entity.Player;
import model.service.GameService;
import model.service.propertyconfig.ConnectingConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Taras on 07.03.2017.
 */
public class Server {
    //pool with all connections for send
    private BlockingQueue<SocketExecutor> socketsQueue = new LinkedBlockingQueue<SocketExecutor>();
    private ServerSocket serverSocket;
    private Thread serverThread;

    public Server() {

        try {
            serverSocket = new ServerSocket(ConnectingConfig.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        serverThread = Thread.currentThread();//get main server thread for interrupt him

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("Server lunched");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (serverThread.isInterrupted()) break;
            else if (socket != null) // if connect successful
            {
                try {
                    //вальнуть финалы и демона
                    final SocketExecutor executor = new SocketExecutor(socket);
                    final Thread thread = new Thread(executor);
                    thread.setDaemon(true);
                    thread.start();
                    socketsQueue.offer(executor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //turn off the server
    public void closeAll() {
        for (SocketExecutor s :
                socketsQueue) {
            s.close();
        }
        if (!serverSocket.isClosed())
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public class SocketExecutor implements Runnable {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;
        private String nick = null;

        private boolean isAutorised;
        private GameService gameService;

        private List<Player> players;

        public SocketExecutor(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());

            gameService = new GameService();
            players = gameService.getAllPlayers();   //поднимаем из базы лист игроков
            isAutorised = false;
        }

        public void run() {


            while (!socket.isClosed()) {

                if (!isAutorised) {
                    authorisation();
                    send(nick + " залогинился в игру");
                }

                String msg = null;

                try {
                    msg = in.readLine().toUpperCase(); //
                } catch (IOException e) {
                    close();  //if we can't reading we shutdown the server
                }
                String[] strArr = msg.split(" ");//розбиваем команду пользователя
//                 на составляющие и в зав.  от команды указывает параметры

                if (msg.equals("FARM STATUS")) {
                    send(gameService.soutFarm());
                } else if (msg.equals("EXIT")) {
                    break;
                } else if (msg.equals("EXIT SERVER")) {
                    serverThread.interrupt();
                    try {
                        new Socket("localhost", ConnectingConfig.PORT); // make new connection to go out from .accept())
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeAll(); //turn off the server
                    }
                } else if (msg.equals("GET AV P")) {
                    send(gameService.getAllPlants().toString());
                } else if (msg.equals("GET AV B")) {
                    send(gameService.getAllBuildings().toString());
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
// PICK UP FROM 2 1
                } else if (strArr[0].equals("DEL") && strArr[1].equals("PLANT")) {
                    String x = strArr[2];
                    String y = strArr[3];
                    gameService.delPlant(x, y);
                } else if (strArr[0].equals("SET") && strArr[1].equals("BUILDING")) {
                    String buildingName = strArr[2];
                    String x = strArr[4];
                    String y = strArr[5];
                    gameService.setBuilding(buildingName, x, y);
//  SET BUILDING ТЕПЛИЦА TO 2 2
                } else if (strArr[0].equals("DEL") && strArr[1].equals("BUILDING")) {
                    String x = strArr[2];
                    String y = strArr[3];
                    gameService.delBuilding(x, y);
//                    DEL BUILDING 2 2
                } else {
                    out.println(msg + " Данная команда не найдена. попробуйте еще");
                    send("command:" + msg);
                }
            }
        }

        /**
         * write to socket msg
         */
        public synchronized void send(String msg) {
            out.println(msg);
            out.flush(); // sending
        }

        /**
         * close socket from queue
         */
        public synchronized void close() {
            socketsQueue.remove(this);
            if (!socket.isClosed()) {
                try {
                    socket.close();
                    in.close();
                    out.close();

                    if (socketsQueue.size() == 0) {
                        closeAll();
                        System.exit(0);
                    }
                } catch (IOException e) {
                    System.out.println("Can't close connection");
                    e.printStackTrace();
                }
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

                    if (isLoginExist(nick, (ArrayList<Player>) players)) {
                        if (isPasswordCorrect(loginedPlayer, (ArrayList<Player>) players)) {
                            gameService.setPlayer(loginedPlayer); //назначаем игрока
                            gameService.getField(gameService.getPlayer());
                            isAutorised = true;
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


    }
}
