package model.client.impl.singlethread;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Taras on 09.03.2017.
 */
public class ClientThread extends Thread {
    private boolean isStop;
    BufferedReader in;
    public ClientThread(BufferedReader in){
        this.in = in;
    }

    public void setStop()
    {
        isStop = true;
    }

    @Override
    public void run() {
        try {
            while (!isStop)
            {
                String str = in.readLine();
                if(str == null)
                {
                    System.out.println("Server closed connection");
                    setStop();
                    break;
                }
                if(str.equals("exit"))
                {
                    System.out.println("Server closed connection with command exit");
                    setStop();
                    break;
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.println("Fail during getting msg");
            e.printStackTrace();
        }
    }
}
