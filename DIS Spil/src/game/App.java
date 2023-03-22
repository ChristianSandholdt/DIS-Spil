package game;

import java.net.*;
import java.io.*;
import javafx.application.Application;
import sock.klientNetworkThread;

public class App {
	static DataOutputStream outToServer;
	public static void main(String[] args) throws Exception {
		Socket s = new Socket("10.10.138.161", 6969);
		klientNetworkThread knt = new klientNetworkThread(s);
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
		outToServer = new DataOutputStream(s.getOutputStream());
		outToServer.writeBytes("ConnectionEstablished" + '\n');
		if (inFromServer.readLine().equals("GameIsGo")) {
			System.out.println("Indtast spillernavn");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String navn = inFromUser.readLine();
			outToServer.writeBytes(navn + '\n');
			System.out.println("Game is go");
			knt.start();
			Application.launch(Gui.class);
		}
		else {
			System.out.println("Game is not go");
		}
	}
}
