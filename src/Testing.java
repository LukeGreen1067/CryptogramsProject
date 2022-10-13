import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class Testing {
	
	private String Phrase;
	private Cryptogram algo;
	private ScannerWriter sw;
	private Game game;
	private String msg;
	private String cypher;
	private Player player;
	private Character messageChar;
	
	@BeforeEach
	public void setup() {
		game = new Game();
		player = new Player("player1", 0, 0, 0);
		game.player = player;
		Phrase = "a bore is someone who deprives you of solitude without providing you with company.";
        algo = new Cryptogram(Phrase);
        game.cryptogram = algo;
        msg = algo.getPhrase().toLowerCase();
        cypher = algo.getCypher();
        messageChar = msg.charAt(0);
        game.resetMessage = msg;
    	game.messageChar = messageChar;
    	game.msg = msg;
	}
	
    @Test
    public void checkAlgo() //Test to see if algorithm correctly ciphers phrases
    {   
        assertEquals(Phrase.toLowerCase(), algo.Decipher().toLowerCase());
    }

    @Test
    public void checkread() //Testing Reading
    {
    	sw = new ScannerWriter("./src/Phrases.txt", "./src/Phrases.txt");
        ArrayList<String> test = sw.readLines();
        assertEquals("We love life, not because we are used to living but because we are used to loving.", test.get(0));
        assertEquals("Nothing in life is to be feared, it is only to be understood. Now is the time to understand more, so that we may fear less.", test.get(1));
    }

    @Test
    public void checkwrite(){
    	sw = new ScannerWriter("./src/writetest.txt", "./src/writetest.txt");
        String test1 = "This is test 1";
        String test2 = "This is test 2";
        ArrayList<String> test;
        sw.writeFile(test1);
        test = sw.readLines();
        assertEquals("This is test 1", test.get(0));
        sw.writeFile(test2);
        test = sw.readLines();
        assertEquals("This is test 2", test.get(0));

    }
    
    @Test
    public void checkLetterEntry() {
    	game.enterLetter('E');
    	assertEquals(game.msg.charAt(0), 'E');
    }
    
    @Test
    public void checkLetterRemoval() {
    	game.enterLetter('T');
    	game.removeLetter('T');
    	assert(game.msg.charAt(0) != 'T');
    }
    
    @Test
    public void checkSave() {
    	game.saveGame(msg, Phrase, cypher, game.hintCount);
    }
    
    @Test
    public void checkLoad() {
    	sw = new ScannerWriter("./src/SavedGames.txt", "./src/SavedGames.txt");
    	ArrayList<String> test = sw.readLines();
    	assertNotEquals(test, null);
    }
    
    @Test
    public void checkNumCryptograms() {
    	assertEquals(player.getCryptogramsCompleted(), 0);
    }
    
    @Test
    public void checkNumGuesses() {
    	assertEquals(player.getNumGuesses(), 0);
    }
    
    @Test
    public void checkStatSave() {
    	//player.saveStats();
    }
    
    @Test
    public void checkStatLoad() {
    	sw = new ScannerWriter("./src/players.txt", "./src/players.txt");
    	ArrayList<String> test = sw.readLines();
    	assertNotEquals(test, null);
    }
    
    @Test
    public void checkSolution() {
    	assertEquals(algo.Decipher().toUpperCase(), "A BORE IS SOMEONE WHO DEPRIVES YOU OF SOLITUDE WITHOUT PROVIDING YOU WITH COMPANY.");
    }
    
    @Test
    public void checkFrequencies() {
    	char testChar = Character.toUpperCase(game.msg.charAt(0));
    	assert(algo.getFreq().get(testChar) >= 0);
    }

    @Test
    public void checkScoreboard() {
    	player.loadStats();


    	if(game.player.getScoreboard().size() > 0) {
    		assert(game.player.getScoreboard().get(0).getClass().equals(String.class));
	    	assert(Integer.parseInt(game.player.getScoreboard().get(1)) == (int)Integer.parseInt(game.player.getScoreboard().get(1)));
    	}
    	else {
    		assert(true);
    	}

    }
    
    @Test
    public void checkEmptyScoreboard() {
    	try {
    		game.displayScoreboard();
    	} catch(Exception E) {
    		assert(E != null);
    	}
    }
    
    @Test
    public void checkHint() {
    	game.hint();
    	assert(game.hintCount == 1);
    }
}
