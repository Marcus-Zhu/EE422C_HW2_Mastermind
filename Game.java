/*  * EE422C Project 2 (Mastermind) submission by
 * Yilin Zhu
 * yz22778
 * <Your GIT URL>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment2;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private boolean inTestMode;
	private boolean gameStarted = false;
	private boolean gameTerminated = false;
	private int guessCnt;
	private String secretCode = "";
	private Board board = new Board();
	private Scanner scanner = new Scanner(System.in);
	private String initGreetingStr1 = "Welcome to Mastermind. Here are the rules.\n\n"
			+ "This is a text version of the classic board game Mastermind. "
			+ "The computer will think of a secret code. The code consists of ";
	private String initGreetingStr2 = " colored pegs.\nThe pegs MUST be one of six colors: "
			+ "blue, green, orange, purple, red, or yellow. A color may appear more than "
			+ "once in the code. You try to guess what colored pegs are in the code and w"
			+ "hat order they are in. After you make a valid guess the result (feedback) "
			+ "will be\ndisplayed.\nThe result consists of a black peg for each peg you "
			+ "have guessed exactly correct (color and position) in your guess. For each "
			+ "peg in the guess that is the correct color, but is out of position, you get "
			+ "a white peg. For each peg that is fully incorrect, you get no feedback.\n"
			+ "Only the first letter of the color is displayed. B for Blue, R for Red, "
			+ "and so forth.\nWhen entering guesses you only need to enter the first "
			+ "character of each color as a capital letter.\n\nYou have ";
	private String initGreetingStr3 = " guesses to figure out the secret code or you "
			+ "lose the game. Are you ready to play? (Y/N):";

	/**
	 * Constructor for Game class.
	 * 
	 * @param isTesting
	 */
	public Game(boolean isTesting) {
		inTestMode = isTesting;
		System.out.print(initGreetingStr1 + GameConfiguration.pegNumber + initGreetingStr2
				+ GameConfiguration.guessNumber + initGreetingStr3);
		gameTerminated = !inputYN();
		if (!gameTerminated) {
			gameStarted = true;
			generateCode();
		}
	}

	public boolean inputYN() {
		char start;
		boolean inputValid = true;
		do {
			try {
				if (!inputValid)
					System.out.print("Input invalid! Type \"Y\" or \"N\" (Y/N):");
				start = scanner.nextLine().charAt(0);
				if (Character.toUpperCase(start) == 'N') {
					return false;
				} else if (Character.toUpperCase(start) == 'Y') {
					return true;
				}
				inputValid = false;
			} catch (NoSuchElementException e) {
				System.err.println("Scanner Error!");
			}
		} while (!inputValid);
		return false;
	}

	public boolean restart() {
		System.out.print("Are you ready for another game (Y/N)");
		return inputYN();
	}

	public boolean gameQuited() {
		if (gameTerminated)
			System.out.println("Thank you for playing. Goodbye.");
		return gameTerminated;
	}

	public boolean gameEnded() {
		return !gameStarted;
	}

	/**
	 * Generate the secret code.
	 */
	public void generateCode() {
		System.out.println("\nGenerating secret code ....\n");
		secretCode = "";
		Random rand = new Random();
		for (int i = 0; i < GameConfiguration.pegNumber; i++) {
			secretCode += GameConfiguration.colors[rand.nextInt(GameConfiguration.colors.length)];
		}
		board.setCode(secretCode);
		int currentCnt = (GameConfiguration.guessNumber - guessCnt);
		System.out.println("You have " + currentCnt + " guess" + (currentCnt > 1 ? "es" : "") + " left.");
	}

	public boolean next() {
		if (guessCnt == GameConfiguration.guessNumber) {
			System.out.println("Sorry, you are out of guesses. You lose, boo-hoo.\n");
			gameStarted = false;
			return false;
		}
		if (inTestMode)
			System.out.println("TEST MODE: secret code is: " + secretCode);

		System.out.print("What is your next guess?\n" + "Type in the characters for your guess and press enter.\n"
				+ "Enter guess:");

		String[] code = new String[1];
		int[] pegs = checkInput(code);
		if (pegs.length == 2) {
			if (pegs[0] == 4)
				gameStarted = false;
			return true;
		}
		return false;
	}

	public int[] checkInput(String[] inputStr) {
		try {
			inputStr[0] = scanner.nextLine();
		} catch (NoSuchElementException e) {
			System.err.println("Scanner Error!");
			return new int[1];
		}
		if (inputStr[0].toLowerCase().equals("history")) {
			board.displayHist();
			return new int[1];
		} else {
			return guess(inputStr[0]);
		}
	}

	public int[] guess(String code) {
		int[] result = new int[2];
		if (isValidCode(code)) {
			guessCnt++;
			System.out.print("\n" + code + " -> Result:\t");
			result = validate(code);
			board.printPegs(result);
			board.setHist(code, result);
			if (result[0] != 4) {
				int currentCnt = (GameConfiguration.guessNumber - guessCnt);
				System.out.println("\n\nYou have " + currentCnt + " guess" + (currentCnt > 1 ? "es" : "") + " left.");
			} else {
				System.out.println(" - You Win!!\n");
			}
			return result;
		} else
			return new int[1];
	}

	public int[] validate(String code) {
		int[] result = new int[] { 0, 0 };
		if (code.equals(secretCode)) {
			result[0] = 4;
			result[1] = 0;
		} else {
			StringBuilder str1 = new StringBuilder(secretCode);
			StringBuilder str2 = new StringBuilder(code);
			for (int i = 0; i < str1.length(); i++) {
				if (str1.charAt(i) == str2.charAt(i)) {
					result[0]++;
					str1.deleteCharAt(i);
					str2.deleteCharAt(i);
					i--;
				}
			}
			for (int i = 0; i < str1.length(); i++) {
				for (int j = 0; j < str2.length(); j++) {
					if (str1.charAt(i) == str2.charAt(j)) {
						result[1]++;
					}
				}
			}
		}
		return result;
	}

	public boolean isValidCode(String code) {
		if (code.length() != GameConfiguration.pegNumber) {
			System.out.println(code + " -> INVALID GUESS\n");
			return false;
		}
		for (int i = 0; i < GameConfiguration.pegNumber; i++) {
			if (!Arrays.asList(GameConfiguration.colors).contains(code.substring(i, i + 1))) {
				System.out.println(code + " -> INVALID GUESS\n");
				return false;
			}
		}
		return true;
	}
}