package sock;
import game.ConcurrentArrayList;
import game.GameLogic;
import game.Player;
import game.pair;

import java.net.*;
import java.io.*;
import java.util.List;

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
			}
			String playerName = inFromClient.readLine();
			GameLogic.makePlayers(playerName);
			for (Player p : GameLogic.players) {
				System.out.println(p.getXpos() + "," + p.getYpos() + "," + p.getDirection());
				outToClient.writeBytes(p.getXpos() + "," + p.getYpos() + "," + p.getDirection() + '\n');
				GameLogic.me = p;
			}
			// Cannot assign field "direction" because "game.GameLogic.me" is null
			while (true) {
				String updatePosition = inFromClient.readLine();
				String[] playerInfo = updatePosition.split(",");
				GameLogic.updatePlayer(Integer.parseInt(playerInfo[0]), Integer.parseInt(playerInfo[1]), playerInfo[2]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		// do the work here
	}
}
