package sock;
import game.*;

import java.net.*;
import java.io.*;
import java.util.List;

public class ServerThread extends Thread{
	Socket connSocket;
	Player player;
	Fruit fruit;
	
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
			this.fruit = GameLogic.makeFruit();
			fruit.setOutToClient(outToClient);
			// Cannot assign field "direction" because "game.GameLogic.me" is null
			while (true) {
				StringBuilder sb = new StringBuilder();
				StringBuilder fsb = new StringBuilder();
				String moveSet = inFromClient.readLine();
				String[] moves = moveSet.split(",");
				updatePlayer(player, Integer.parseInt(moves[0]), Integer.parseInt(moves[1]), moves[2]);
				for (Player p : GameLogic.players) {
					sb.append(p.getName() + "," + p.getXpos() + "," + p.getYpos() + "," + p.getDirection() + "," + p.getPoints() + ",");
				}
				for (Fruit f : GameLogic.fruits) {
					fsb.append(f.getXpos() + "," + f.getYpos() + ",");
				}
				String playerInfo = sb.toString();
				String fruitInfo = fsb.toString();
				//System.out.println(playerInfo);
//				for (Fruit f : GameLogic.fruits) {
//					//System.out.println(fruitInfo);
//					f.sendFruit(fruitInfo);
//				}
				for (Player p : GameLogic.players) {
					//System.out.println(playerInfo);
					p.sendMessage(playerInfo + " " + fruitInfo);
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
			Fruit fruit = GameLogic.getFruitAt(x+delta_x,y+delta_y);
			if (fruit!=null) {
				player.addPoints(25);
				updateFruit(fruit);
			}
			if (p!=null) {
				player.addPoints(10);
				//update the other player
				p.addPoints(-10);
				pair pa = GameLogic.getRandomFreePosition();
				p.setLocation(pa);
			}
			pair newpos = new pair(x+delta_x,y+delta_y);
			player.setLocation(newpos);
		}
	}

	public static void updateFruit(Fruit fruit) {
		pair pa = GameLogic.getRandomFreePosition();
		fruit.setLocation(pa);
	}
}
