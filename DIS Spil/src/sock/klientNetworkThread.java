package sock;

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
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(connSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
