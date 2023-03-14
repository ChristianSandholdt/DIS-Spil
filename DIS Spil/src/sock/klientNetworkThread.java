package sock;

import game.GameLogic;
import game.Gui;
import game.Player;
import game.pair;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class klientNetworkThread extends Thread {
    Socket connSocket;

    public klientNetworkThread(Socket connSocket){
        this.connSocket = connSocket;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(connSocket.getOutputStream());
            // write a string split that gets the player x the player y and the player direction
            // and then updates the player on the screen
            String[] playerInfo = inFromServer.readLine().split(",");
            System.out.println(playerInfo[0] + " " + playerInfo[1] + " " + playerInfo[2]);
            pair pair = new pair(Integer.parseInt(playerInfo[0]), Integer.parseInt(playerInfo[1]));
            Gui.placePlayerOnScreen(pair, playerInfo[2]);
            while (true) {

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
