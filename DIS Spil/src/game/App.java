package game;

import java.net.*;
import java.io.*;
import javafx.application.Application;
import sock.klientNetworkThread;

public class App {
	public static void main(String[] args) throws Exception {
		Socket suckIt = new Socket("localhost", 6969);
		klientNetworkThread knt = new klientNetworkThread(suckIt);
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(suckIt.getInputStream()));
		DataOutputStream outToServer = new DataOutputStream(suckIt.getOutputStream());
		outToServer.writeBytes("ConnectionEstablished" + '\n');
		if (inFromServer.readLine().equals("GameIsGo")) {
			System.out.println("Indtast spillernavn");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String navn = inFromUser.readLine();
			outToServer.writeBytes(navn + '\n');
			System.out.println("Game is go");
			Application.launch(Gui.class);
			knt.start();
		}
		else {
			System.out.println("Game is not go");
		}
	}
}
