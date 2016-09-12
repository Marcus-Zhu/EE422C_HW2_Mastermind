/*  * EE422C Project 2 (Mastermind) submission by
 * Yilin Zhu
 * yz22778
 * <Your GIT URL>
 * Slip days used: <0>
 * Fall 2016
 */

 package assignment2;

public class Main {
	public static void main(String[] argv) {
		Game g;
		boolean gameParam = false;
		if (argv.length > 0) {
			gameParam = (argv[0].equals("1"));
		}
		System.out.println(gameParam);
		do {
			// set param to false if don't want secret code shown
			g = new Game(gameParam); 
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
