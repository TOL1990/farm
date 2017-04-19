import com.aad.myutil.server.MYServer;
import model.Server.impl.singlethread.Server;
import model.client.impl.singlethread.Client;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Taras on 09.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        // com.fenix.main.Main.main();
       // model.Server.connection.src.main.java.com.fenix.main.Main.main(args); // создадим конекшн
        serverConncetion(args);
      //  runInPool();
     //   runInSingleThreads();
    }

    public static void serverConncetion(String[] args)
    {
        model.Server.connection.src.main.java.com.fenix.main.Main.main(args); // создадим конекшн
       new model.Server.impl.pool.Server();
    }
    public static void runInPool() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Choose server  \" S \" or client \" C \"  ");

        while (true) {
            String answer = "";
            try {
                answer = reader.readLine().toLowerCase();

                if (answer.equals("s")) {
                    new model.Server.impl.pool.Server();
                    break;
                } else if (answer.equals("c")) {
                    new model.client.impl.pool.Client();
                    break;
                } else
                    System.out.println("Wrong symbol. Please, enter correct symbol ");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runInSingleThreads() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Choose server  \" S \" or client \" C \"  ");

        while (true) {
            String answer = "";
            try {
                answer = reader.readLine().toLowerCase();

                if (answer.equals("s")) {
                    new Server();
                    break;
                } else if (answer.equals("c")) {
                    new Client();
                    break;
                } else
                    System.out.println("Wrong symbol. Please, enter correct symbol ");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
