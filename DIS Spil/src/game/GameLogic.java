package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class GameLogic {

	public static List<Player> players = new ArrayList<Player>();
	public static Player me;
	public static List<Fruit> fruits = new ArrayList<>();
	public static Fruit fruit;
	
	
	public static Player makePlayers(String name) {
		pair p=getRandomFreePosition();
		me = new Player(name,p,"up");
		players.add(me);
		return me;
	}

	public static Fruit makeFruit() {
		pair p = getRandomFreePosition();
		fruit = new Fruit(p);
		fruits.add(fruit);
		return fruit;
	}
	
	public static pair getRandomFreePosition()
	// finds a random new position which is not wall 
	// and not occupied by other players 
	{
		int x = 1;
		int y = 1;
		boolean foundfreepos = false;
		while  (!foundfreepos) {
			Random r = new Random();
			x = Math.abs(r.nextInt()%18) +1;
			y = Math.abs(r.nextInt()%18) +1;
			if (Generel.board[y].charAt(x)==' ') // er det gulv ?
			{
				foundfreepos = true;
				for (Player p: players) {
					if (p.getXpos()==x && p.getYpos()==y) //pladsen optaget af en anden 
						foundfreepos = false;
				}

				for (Fruit f: fruits) {
					if (f.getXpos()==x && f.getYpos()==y) //pladsen optaget af en anden
						foundfreepos = false;
				}

			}
		}
		pair p = new pair(x,y);
		return p;
	}
	
	public static Player getPlayerAt(int x, int y) {
		for (Player p : players) {
			if (p.getXpos()==x && p.getYpos()==y) {
				return p;
			}
		}
		return null;
	}

	public static Fruit getFruitAt(int x, int y) {
		for (Fruit f : fruits) {
			if (f.getXpos()==x && f.getYpos()==y) {
				return f;
			}
		}
		return null;
	}
}
