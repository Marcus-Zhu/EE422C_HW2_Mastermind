package assignment2;

public class Main {
	public static void main(String[] argv) {
		Game g;
		do {
			g = new Game(true);
			if (g.gameQuited())
				break;
			while (true) {
				if (g.gameEnded())
					break;				
				g.next();
			}
		} while(g.restart());
	}
}
