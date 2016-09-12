package assignment2;

public class Main {
	public static void main(String[] argv) {
		Game g;
		do {
			// set param to false if don't want secret code shown
			g = new Game(false); 
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
