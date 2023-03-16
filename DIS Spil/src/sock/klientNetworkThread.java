package sock;

import game.*;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class klientNetworkThread extends Thread {
    Socket connSocket;

    public klientNetworkThread(Socket connSocket){
        this.connSocket = connSocket;
    }

    public void run() {
        try {
            Thread.sleep(3000);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(connSocket.getOutputStream());
            // write a string split that gets the player x the player y and the player direction
            // and then updates the player on the screen
            while (true) {
//                String[] playerInfo = inFromServer.readLine().split(",");
//                //System.out.println(playerInfo[0] + " " + playerInfo[1] + " " + playerInfo[2]);
//                pair pair = new pair(Integer.parseInt(playerInfo[0]), Integer.parseInt(playerInfo[1]));
//                Gui.placePlayerOnScreen(pair, playerInfo[2]);
                String fruitData = inFromServer.readLine();
                String[] fruitInfo = fruitData.split(",");
                String playerData = inFromServer.readLine();
                System.out.println(playerData);
                String[] playerInfo = playerData.split(",");
                for (int i = 0; i < fruitInfo.length; i+= 2) {
                    pair pair = new pair(Integer.parseInt(fruitInfo[i]), Integer.parseInt(fruitInfo[i+1]));
                    GameLogic.fruits.add(new Fruit(pair));
                }
                for (Fruit f : GameLogic.fruits) {
                    pair pair = new pair(f.getXpos(), f.getYpos());
                    Gui.placeFruitOnScreen(pair);
                }
                for (Player p : GameLogic.players){
                    pair pair = new pair(p.getXpos(), p.getYpos());
                    Gui.removePlayerOnScreen(pair);
                }
                GameLogic.players.clear();
                for (int i = 0; i < playerInfo.length; i+= 5) {
                    pair pair = new pair(Integer.parseInt(playerInfo[i+1]), Integer.parseInt(playerInfo[i+2]));
                    Player p = new Player(playerInfo[i], pair, playerInfo[i+3]);
                    p.addPoints(Integer.parseInt(playerInfo[i+4]));
                    GameLogic.players.add(p);
                }
                for (Player p : GameLogic.players){
                    pair pair = new pair(p.getXpos(), p.getYpos());
                    Gui.placePlayerOnScreen(pair, p.getDirection());
                    Gui.updateScoreTable();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
