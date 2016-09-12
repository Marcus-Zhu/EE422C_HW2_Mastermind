/*  * EE422C Project 2 (Mastermind) submission by
 * Yilin Zhu
 * yz22778
 * <Your GIT URL>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment2;

import java.util.Scanner;

public class Driver {
	public static void main(String[] argv) {
		Game g;
		Scanner s = new Scanner(System.in);
		boolean gameParam = false;
		if (argv.length > 0) {
			gameParam = (argv[0].equals("1"));
		}
		do {
			// set param to false if don't want secret code shown
			g = new Game(gameParam); 
			g.setScanner(s);
			if (!g.runGame())
				break;
		} while(g.restart());
	}
}
