import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class App {

    private HiveGame game;

    public static void main(String[] args) {
        boolean playing = true;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Starting the game");
        while (playing) {
            try {
                String s = br.readLine();
                if (s.toLowerCase().equals("quit")) {
                    playing = false;
                    continue;
                }
                // play the game with the given input
                System.out.println(s.hashCode());
            } catch (IOException ignored) {
            }
        }
    }

}
