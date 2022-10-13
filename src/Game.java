import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

	private int index;                        // Determines the position to input the next character.
	private Scanner scanner;                // Reads the input from the command line.
	public String msg;
	public Cryptogram cryptogram;
	private String input;                    // Stores the input as a string.
	private Character inputChar;            // Stores the first character from the player's input.
	public Character messageChar;            // Stores a character from the phrase at position index.
	private Random random;                    // Allows for use of random number generators.
	private String inputMap;
	private String name;                    // Shows the player's inputs in a Hangman-esque map.
	public Player player;                    // Stores the player as an instance of the Player class.
	public String resetMessage;
	public int hintCount;


	/*
	 * Replaces all instances of the character grabbed from the current index with the input character.
	 * Returns the altered phrase.
	 */
	public String enterLetter(char input) {
		
		msg = msg.replaceAll(String.valueOf(Character.toLowerCase(messageChar)), String.valueOf(Character.toUpperCase(input)));
		
		if (player != null) {
			if (Character.toUpperCase(input) == Character.toUpperCase(cryptogram.Decipher().charAt(index))) {
				player.addNumCorrectGuesses(1);
			}
			player.addNumGuesses(1);
		}
		
		if(!msg.equals(msg.toUpperCase())) {
			updateIndex();
		}
		
		updateMap(msg);
		return msg;
	}


	/*
	 * Removes a previously entered letter from the phrase.
	 * Returns the altered phrase
	 */
	public String removeLetter(char input) {
		
		// Finds character initially replaced by letter entry to squash bug allowing user to alter letters in the base phrase.
		Character replaceChar = resetMessage.charAt(msg.indexOf(input));

		msg = msg.replaceAll(String.valueOf(Character.toUpperCase(input)), String.valueOf(Character.toLowerCase(replaceChar)));
		
		updateIndex();
		updateMap(msg);
		return msg;
	}
	
	
	/*
	 * Allows the player to have the next character solved for them.
	 */
	public void hint() {
		
		if(hintCount < 3 && hintCount >= 0) {
			messageChar = msg.charAt(index);
			inputChar = cryptogram.Decipher().charAt(index);
			msg = enterLetter(inputChar);
			hintCount++;
			System.out.println("Hint used. You have " + (3-hintCount) + " hints left.");
		}
		else {
			System.out.println("You have no hints left.");
		}
	}


	/*
	 * Reads user input from the terminal and returns it as a string.
	 */
	private String readInput() {

		scanner = new Scanner(System.in);
		input = scanner.nextLine().toLowerCase();

		return input;

	}


	/*
	 * Determines if a letter has already been entered.
	 * Returns true if so, otherwise false.
	 */
	private Boolean letterEntered(String msg, Character ltr) {

		Boolean entered = false;

		if (msg.contains(String.valueOf(ltr).toUpperCase())) {
			entered = true;
		}

		return entered;

	}


	/*
	 * Updates index to point to the first empty space in the puzzle.
	 */
	public void updateIndex() {
		
		index = 0;
		char ch = msg.charAt(index);;
		
		while (!Character.isLowerCase(ch)) {
			index++;
			ch = msg.charAt(index);
		}
	}


	/*
	 * Updates the input map to indicate the entered letters.
	 */
	public void updateMap(String msg) {

		int i = 0;
		inputMap = "";

		while (i < msg.length()) {
			char ch = msg.charAt(i);
			
			if (!Character.isLowerCase(ch)) {
				inputMap += msg.charAt(i);
			}
			else {
				inputMap += "_";
			}
			
			i++;
		}
	}
	
	
	/*
	 * Displays the scoreboard.
	 */
	public void displayScoreboard() {
		
		ArrayList<String> scoreboard = player.getScoreboard();
		
		if(scoreboard.size() > 0) {
			for(int i = 0; i < 20; i += 2) {
				if(i < scoreboard.size()) {
					System.out.println(i/2 + 1 + ". " + scoreboard.get(i) + ": " + scoreboard.get(i+1));
				}
			}
		}
		else {
			System.out.println("There are no player stats saved on file.");
		}
	}
	

	/*
	 * Displays login message on start-up.
	 */
	public void displayLogin() {

		System.out.println("Welcome to Team 6's CS207 Crytpograms game.");
		System.out.println("To begin, please enter your name:");

	}


	/*
	 * Provides functionality for initialising player.
	 */
	public void login() {

		displayLogin();
		name = readInput();
		player = new Player(name, 0, 0, 0);
		player.loadStats();

	}


	/*
	 * Prints the menu text.
	 */
	public void displayMenu(String name) {
		System.out.println("\nMAIN MENU - " + name + "\n1. Play game\n2. Load game\n3. View scoreboard\n4. View player stats\n5. Quit game\n");
		System.out.println("Pick an option: ");
	}
	
	
	/*
	 * Provides main menu functionality.
	 */
	public void menu() {
		
		while (true) {

			displayMenu(name);
			String menuSelection = readInput();

			if(menuSelection.equals("1") || menuSelection.equals("play") || menuSelection.equals("game") || menuSelection.equals("play game")) {

				newGame();

			}else if(menuSelection.equals("2") || menuSelection.equals("load") || menuSelection.equals("load game")){

				loadGame();

			} else if(menuSelection.equals("3") || menuSelection.equals("Scoreboard") || menuSelection.equals("Scoreboard")){

				displayScoreboard();

			} else if(menuSelection.equals("4") || menuSelection.equals("stats") || menuSelection.equals("view player stats") || menuSelection.equals("player stats")) {

				System.out.println("Name: " + player.getName());
				System.out.println("Cryptograms completed: " + player.getCryptogramsCompleted());
				System.out.println("Total guesses: " + player.getNumGuesses());
				System.out.println("Total correct guesses: " + player.getNumCorrectGuesses());
				System.out.println("Guess accuracy: " + player.getAccuracy());

			} else if(menuSelection.equals("5") || menuSelection.equals("quit") || menuSelection.equals("exit") || menuSelection.equals("")) {

				System.out.println("\nThanks for playing!");
				player.saveStats();
				System.exit(0);

			}
			else {
				System.out.println("\nInavlid selection. Please enter '1','2','3','4' or '5'.");
			}

		}
	}

	
	/*
	 * Saves the progress of a cryptogram to a file.
	 */
	public void saveGame(String msg, String inputMsg, String cypher, Integer hintc) {
		ScannerWriter sw = new ScannerWriter("./src/SavedGames.txt", "./src/SavedGames.txt");
		String fileinput = msg + "\n" +inputMsg + "\n" + cypher + "\n" + hintc.toString();
		sw.writeFile("");
		sw.writeFile(fileinput);
	}

	
	/*
	 * Initialises the cryptogram for a new game.
	 */
	public void newGame(){
		//Phrase reading from file & Writing
		ScannerWriter sw = new ScannerWriter("./src/Phrases.txt", "./src/writetest.txt");
		ArrayList<String> readPhrase = sw.readLines();

		// Initialises Random object to produce random integers
		random = new Random();

		// Initialises ciphered phrase and solution
		String inputMsg = readPhrase.get(random.nextInt(readPhrase.size()-1)); // Picks random phrase from file
		cryptogram = new Cryptogram(inputMsg); // Generates cryptogram from phrase
		String cypher = cryptogram.getCypher();
		msg = cryptogram.getPhrase().toLowerCase();

		updateMap(msg);
		hintCount = 0;
		playGame(inputMsg, cryptogram, msg, cypher);
	}

	
	/*
	 * Restores a previously unsolved cryptogram from a save file. 
	 */
	public void loadGame(){
		ScannerWriter sw = new ScannerWriter("./src/SavedGames.txt", "./src/SavedGames.txt");
		ArrayList<String> loadSave = sw.readLines();

		String inputMap = loadSave.get(0);
		String inputMsg = loadSave.get(1);
		String cypher = loadSave.get(2);
		String hintC = loadSave.get(3);

		cryptogram = new Cryptogram(inputMsg, cypher);

		updateMap(inputMap);
		hintCount = Integer.parseInt(hintC);
		playGame(inputMsg, cryptogram, inputMap, cypher);
	}
	
	
	/*
	 * Allows user to play a single game with a given cryptogram.
	 */
	private void playGame(String inputMsg, Cryptogram cryptogram, String msg, String cypher) {
		resetMessage = cryptogram.getPhrase().toLowerCase(); // Saves initial ciphered phrase for replacing letters to their original state

		updateIndex(); // Points to the first letter of the phrase
		
		//	DEV MODE
			System.out.println(cryptogram.Decipher().toUpperCase());
			System.out.println(cryptogram.getkey());
		
		System.out.println("\nGame started. Enter a letter of the form 'X' or remove a letter by inputting '-X'.\n");
		
		while(true) {
				
			try {
				System.out.println(msg);
				System.out.println(resetMessage);
				System.out.println(cryptogram.getFreq());
				System.out.println(inputMap);
				// Read user input with scanner.	
				input = readInput();
				
				// Read first character from input.
				inputChar = Character.toUpperCase(input.charAt(0));
				
				// Allow user to exit with command.
				if(input.equals("exit")) {
					System.out.println("Would you like to save. Y/N");
					input = readInput();
					
					if(input.equals("y") || input.equals("Y")){
						saveGame(msg, inputMsg, cypher, hintCount);
						System.out.println("Saved Game");
						break;
					}
					else if(input.equals("n") || input.equals("N")){
						System.out.println("Game not saved, Exiting game");
						break;
					}
					else{
						System.out.println("Invalid input");
					}
				}
				
				//Allows the user to give up and show the solution.
				if(input.toLowerCase().equals("solution")) {
					System.out.println("\nHard luck! Here is the deciphered message:\n" + cryptogram.Decipher().toUpperCase());
					break;
				}
				
				// Allows the player to have the next character solved for them.
				if(input.toLowerCase().equals("hint")) {
					hint();
				}
				
				// Check if input is entry or removal command. Use corresponding method.
				else if(inputChar == '-') {
					
					inputChar = Character.toUpperCase(input.charAt(1));
					
					// Check if character is letter.
					if(Character.isLetter(inputChar)) {
						
						if(letterEntered(msg, inputChar)) {
							msg = removeLetter(inputChar); // Letter input revoked from phrase.
							inputChar = Character.toUpperCase(input.charAt(0));
						}
						else {
							System.out.println("Letter has not been mapped yet.");
						}
					}
					else {
						System.out.println("Invalid input. Please enter a letter.");
					}
				}
				
				// Check if character is letter.
				else if(Character.isLetter(inputChar)) {
					
					// Read character from phrase at index
					messageChar = msg.charAt(index);
						
					// Check if letter has been entered.
					if(letterEntered(msg, inputChar)) {
							
						System.out.println("Letter already entered. Would you like to remap this letter? Y/N");
							
						// Ask if user wants to remap entered letter.
						while(true) {
								
							// Read response from user.
							String remap = input = readInput();
							
							if(remap.equals("y") || remap.equals("yes")) {	
								msg = removeLetter(inputChar);
								break;
							}
							else {
								if(remap.equals("n") || remap.equals("no")) {
									System.out.println("Please enter another letter.");
									break;		
								}
								System.out.println("Invalid input. Enter 'Y' or 'N' for yes or no.");
							}
						}								
					}
					else {
						msg = enterLetter(inputChar);
					}	
				}
				else {
					System.out.println("Invalid input. Please enter a letter.");
				}
			}
			catch(Exception StringIndexOutOfBoundsException) {
				System.out.println("You have filled in all of the characters already.");
				System.out.println(StringIndexOutOfBoundsException);
			}
			
			// Check for win condition.
			if(msg.equals(cryptogram.Decipher().toUpperCase())) {
				System.out.println("\nYou won. Congratulations!");
				break;
			}
		}
	}


	public static void main(String[] args) {

		Game game = new Game();
		game.login();
		game.menu();

	}
}
