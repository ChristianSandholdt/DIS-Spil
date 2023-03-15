package sock;
import game.*;

import java.net.*;
import java.io.*;
import java.util.List;

public class ServerThread extends Thread{
	Socket connSocket;
	Player player;
	
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
			this.player = GameLogic.makePlayers(playerName);
			player.setOutToClient(outToClient);
			// Cannot assign field "direction" because "game.GameLogic.me" is null
			while (true) {
				StringBuilder sb = new StringBuilder();
				String moveSet = inFromClient.readLine();
				String[] moves = moveSet.split(",");
				updatePlayer(player, Integer.parseInt(moves[0]), Integer.parseInt(moves[1]), moves[2]);
				for (Player p : GameLogic.players) {
					sb.append(p.getName() + "," + p.getXpos() + "," + p.getYpos() + "," + p.getDirection() + "," + p.getPoints() + ",");
				}
				String playerInfo = sb.toString();
				//System.out.println(playerInfo);
				for (Player p : GameLogic.players) {
					System.out.println(playerInfo);
					p.sendMessage(playerInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		// do the work here
	}

	public static void updatePlayer(Player player, int delta_x, int delta_y, String direction)
	{
		player.setDirection(direction);
		int x = player.getXpos(),y = player.getYpos();

		if (Generel.board[y+delta_y].charAt(x+delta_x)=='w') {
			player.addPoints(-1);
		}
		else {
			// collision detection
			Player p = GameLogic.getPlayerAt(x+delta_x,y+delta_y);
			if (p!=null) {
				player.addPoints(10);
				//update the other player
				p.addPoints(-10);
				pair pa = GameLogic.getRandomFreePosition();
				p.setLocation(pa);
				pair oldpos = new pair(x+delta_x,y+delta_y);
			} else
				player.addPoints(1);
			pair oldpos = player.getLocation();
			pair newpos = new pair(x+delta_x,y+delta_y);
			player.setLocation(newpos);
		}
	}
}
