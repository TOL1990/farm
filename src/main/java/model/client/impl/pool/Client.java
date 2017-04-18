package model.client.impl.pool;


import model.service.propertyconfig.ConnectingConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader in; //incomming stream
    private PrintWriter out; //outpuut stream

    public Client() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            socket = new Socket(ConnectingConfig.IP, ConnectingConfig.PORT);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            new Thread(new ClientThread()).start(); // make thread to read from socket

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Welcome to SOFT CHAT!");

        while (true) {
            String msgStr = null;
            try {
                msgStr = reader.readLine();
            } catch (IOException ignored) {
            }
            if (msgStr.equals("exit") || socket.isClosed()) {
                close();
                break;
            } else {
                out.println(msgStr);
                out.flush();
            }
        }
    }

    private synchronized void close() {
        if (!socket.isClosed()) {
            try {
                in.close();
                out.close();
                socket.close();
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Threads were't be close");
                e.printStackTrace();
            }
        }
    }

    private class ClientThread implements Runnable {

        public void run() {

            while (!socket.isClosed()) { //сходу проверяем коннект.
                String line = null;
                try {
                    line = in.readLine(); // пробуем прочесть
                } catch (IOException e) { // если в момент чтения ошибка, то...
                    // проверим, что это не банальное штатное закрытие сокета сервером
                    if ("Socket closed".equals(e.getMessage())) {
                        break;
                    }
                    System.out.println("Connection lost"); // а сюда мы попадем в случае ошибок сети.
                    close(); // ну и закрываем сокет (кстати, вызвается метод класса ChatClient, есть доступ)
                }
                if (line == null) {  // строка будет null если сервер прикрыл коннект по своей инициативе, сеть работает
                    System.out.println("Server has closed connection");
                    close(); // ...закрываемся
                } else { // иначе печатаем то, что прислал сервер.
                    System.out.println(line);
                }
            }

        }

    }
}
