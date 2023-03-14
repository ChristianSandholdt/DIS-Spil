package sock;
import game.ConcurrentArrayList;
import game.GameLogic;
import game.Player;

import java.net.*;
import java.io.*;
public class ServerThread extends Thread{
	Socket connSocket;
	private Player player;
	
	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());
			String clientSentence = inFromClient.readLine();
			System.out.println(clientSentence);
			if (clientSentence.equals("ConnectionEstablished")) {
				outToClient.writeBytes("GameIsGo" + '\n' );
				System.out.println(outToClient);
			}
			GameLogic.makePlayers(inFromClient.readLine());
			for (Player p : GameLogic.players) {
				System.out.println(p.getXpos() + p.getYpos() + p.getDirection());
				outToClient.writeBytes(p.getXpos() + " " + p.getYpos() + " " + p.getDirection() + '\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		// do the work here
	}
}
