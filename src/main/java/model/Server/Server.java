package model.Server;

import model.service.propertyconfig.ConnectingConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Taras on 09.03.2017.
 */
public class Server {
    public static void main(String[] args) {
        new Server();
    }
    private List<ServerThread> serverThreads =
            Collections.synchronizedList(new ArrayList<ServerThread>());

    private ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(ConnectingConfig.PORT);

            System.out.println("Server lunched");
            while (true) {
                Socket socket = server.accept();

                ServerThread con = new ServerThread(socket, serverThreads, this);
                serverThreads.add(con);
                con.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
            System.out.println("Server closed!");
        }
        //Сгружаем всех юзеров.
    }


    public void closeAll() {
        try {
            server.close();

            //Run allow all serverThreads and shut them down
            synchronized (serverThreads) {
                Iterator<ServerThread> iter = serverThreads.iterator();
                while (iter.hasNext()) {
                    ((ServerThread) iter.next()).close();
                }
            }
        } catch (Exception e) {
            System.err.println("Threads weren't closed!");
        }
    }


}
