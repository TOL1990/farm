import model.Server.Server;
import model.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Taras on 09.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        verse2();
    }

    public static void verse2() {
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
