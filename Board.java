/*  * EE422C Project 2 (Mastermind) submission by
 * Yilin Zhu
 * yz22778
 * <Your GIT URL>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment2;

import java.util.Arrays;

public class Board {
	private int guessCnt = 0;
	private String secretCode;
	private String[] guessHist = new String[GameConfiguration.guessNumber];
	private int[][] guessResult = new int[GameConfiguration.guessNumber][2];

	/**
	 * Get secret code from Game Runner.
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		secretCode = code;
		// System.out.println("Code get: "+secretCode);
		guessCnt = 0;
		for (int[] row : guessResult) {
			Arrays.fill(row, 0);
		}
	}

	/**
	 * Add player's current guess to history.
	 * 
	 * @param code
	 *            player's guess
	 * @return history added
	 */
	public boolean setHist(String code, int[] pegs) {
		if (guessCnt == GameConfiguration.guessNumber)
			return false;
		guessHist[guessCnt] = code;
		guessResult[guessCnt][0] = pegs[0];
		guessResult[guessCnt][1] = pegs[1];
		guessCnt++;
		return true;
	}

	/**
	 * Display player's guess history and guess results.
	 */
	public void displayHist() {
//		System.out.println("\n---\tFollowing is the history\t---\n");
//		System.out.println("You have made " + guessCnt + " guess" + (guessCnt > 1 ? "es" : "") + ".\n");
		System.out.println();
		for (int i = 0; i < guessCnt; i++) {
			System.out.print(guessHist[i] + "\t\t");
			System.out.print(guessResult[i][0] + "B_"+guessResult[i][1]+"W\n");			
//			printPegs(guessResult[i]);
//			System.out.println();
		}
		System.out.println();
//		System.out.println("\n---\t-------------------------\t---\n");
	}
	
	/**
	 * Print guess result
	 * @param guessResult int[2] of pegs count
	 */
	public void printPegs(int[] guessResult) {
		if (guessResult[0] == 1)
			System.out.print(guessResult[0] + " black peg\t");
		else if (guessResult[0] > 1)
			System.out.print(guessResult[0] + " black pegs\t");
		if (guessResult[1] == 1)
			System.out.print(guessResult[1] + " white peg");
		else if (guessResult[1] > 1)
			System.out.print(guessResult[1] + " white pegs");
		if (guessResult[0] + guessResult[1] == 0)
			System.out.print("No pegs");
	}

}
