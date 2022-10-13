import java.util.ArrayList;

public class Player {
	
	private String name;
	private int cryptogramsCompleted;
	private int numGuesses;
	private int numCorrectGuesses;
	private double accuracy;
	private ArrayList<String> playerList;
	private ArrayList<String> stats;
	private ArrayList<String> scoreboard;
	
	public Player(String username, int completed, int guesses, int correct) {
		
		name = username;
		cryptogramsCompleted = completed;
		numGuesses = guesses;
		numCorrectGuesses = correct;
		accuracy = 0.0;
		playerList = new ArrayList<String>();
		stats = new ArrayList<String>();
		scoreboard = new ArrayList<String>();
	}
	
	
	// Getter methods for fields.
	
	public String getName() {
		return name;
	}
	
	public int getCryptogramsCompleted() {
		return cryptogramsCompleted;
	}
	
	public int getNumGuesses() {
		return numGuesses;
	}
	
	public int getNumCorrectGuesses() {
		return numCorrectGuesses;
	}
	
	public double getAccuracy() {
		return (double)numCorrectGuesses / (double)numGuesses;
	}
	
	public ArrayList<String> getPlayerList() {
		return playerList;
	}
	
	// Increment methods for fields.
	
	public int addCryptogramsCompleted(int inc) {
		cryptogramsCompleted += inc;
		return cryptogramsCompleted;
	}
	
	public int addNumGuesses(int inc) {
		numGuesses += inc;
		return numGuesses;
	}
	
	public int addNumCorrectGuesses(int inc) {
		numCorrectGuesses += inc;
		return numCorrectGuesses;
	}
	
	
	// Decrement methods for fields.
	
	public int removeCryptogramsCompleted(int dec) {
		cryptogramsCompleted -= dec;
		return cryptogramsCompleted;
	}
	
	public int removeNumGuesses(int dec) {
		numGuesses -= dec;
		return numGuesses;
	}
	
	public int removeNumCorrectGuesses(int dec) {
		numCorrectGuesses -= dec;
		return numCorrectGuesses;
	}
	
	
	// Setter methods for fields.
	
	public int setCryptogramsCompleted(int n) {
		cryptogramsCompleted = n;
		return cryptogramsCompleted;
	}
	
	public int setNumGuesses(int n) {
		numGuesses = n;
		return numGuesses;
	}
	
	public int setNumCorrectGuesses(int n) {
		numCorrectGuesses = n;
		return numCorrectGuesses;
	}

	// Update method for accuracy
	
	public void updateAccuracy() {
		if (numCorrectGuesses == 0) {
			accuracy = 0;
		} else {
			accuracy = numCorrectGuesses / numGuesses;
			accuracy = Math.round(accuracy * 100) / 100;
		}
	}
	
	// File IO methods
	
	public void saveStats() {
		try {
			ScannerWriter sw = new ScannerWriter("./src/player_stats.txt", "./src/player_stats.txt");
			playerList.add(name);
			playerList.add(String.valueOf(cryptogramsCompleted));
			playerList.add(String.valueOf(numGuesses));
			playerList.add(String.valueOf(numCorrectGuesses));
			String listString = "";
			for(int i = 0; i < playerList.size(); i++) {
				if(playerList.get(i) != null) {
					listString = listString + playerList.get(i) + "\n";
				}
			}
			sw.writeFile(listString);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadStats() {
		try {
			ScannerWriter sw = new ScannerWriter("./src/player_stats.txt", "./src/player_stats.txt");
			stats = sw.readLines();
			playerList = (ArrayList<String>)stats.clone();
			for(int i = 0; i < playerList.size()-2; i += 4) {
				if(playerList.get(i).equals(name)) {
					cryptogramsCompleted = Integer.parseInt(playerList.get(i+1));
					numGuesses = Integer.parseInt(playerList.get(i+2));
					numCorrectGuesses = Integer.parseInt(playerList.get(i+3));
					playerList.remove(i);
					playerList.remove(i);
					playerList.remove(i);
					playerList.remove(i);
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public ArrayList<String> getScoreboard() {
        
		if(stats.size() > 0) {
			scoreboard.add(stats.get(0));
			scoreboard.add(stats.get(1));
			
	        for(int i = 5; i < stats.size()-2; i += 4) {
	        	String score = stats.get(i);
	        	String scorer = stats.get(i-1);
	        	
	        	if(Integer.parseInt(score) > Integer.parseInt(scoreboard.get(1))) {
	        		scoreboard.add(0, score);
	        		scoreboard.add(0, scorer);
	        	}
	        	else {
	        		scoreboard.add(scorer);
	        		scoreboard.add(score);
	        	}
	        }
		}        
        return scoreboard;
    }

}
