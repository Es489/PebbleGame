import static org.junit.Assert.*;

import org.junit.Test;




import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;




public class PebbleGameTest {


	
	@Test
	public void testReadFile() throws NumberFormatException, FileNotFoundException, IOException, IllegalFormat  {
		System.out.println("Read file test");
		PebbleGame new_game = new PebbleGame();
		new_game.createPlayers(); // creates players to check if there is enough pebbles for created players
		System.out.println("Please enter the file location:");
	    new_game.readFile();
			
				
			
	}
		

	@Test
	public void testCreatePlayers() throws NumberFormatException, InputMismatchException, IOException {
		System.out.println("Create players test");
		  PebbleGame new_game = new PebbleGame();
		  new_game.createPlayers();
		  assertTrue(new_game.getPlayers().size()>0);
		  
			
		
	}

	@Test
	public void testCreateBags() throws NumberFormatException, FileNotFoundException, IOException, IllegalFormat {
		System.out.println("Create bags test");
		PebbleGame new_game = new PebbleGame();
		ArrayList<Integer> bag1 = new ArrayList<Integer>();
		ArrayList<Integer> bag2 = new ArrayList<Integer>();
		ArrayList<Integer> bag3 = new ArrayList<Integer>();
		System.out.println("Location of bag1 file");
		new_game.readFile();
		bag1 = new_game.getOutput();
		System.out.println("Location of bag2 file");
		new_game.readFile();
		bag2 = new_game.getOutput();
		System.out.println("Location of bag3 file");
		new_game.readFile();
		bag3 = new_game.getOutput();
		new_game.createBags(bag1, bag2, bag3);
		assertTrue(bag1 == new_game.getBags().get(0).getPebbles());
		assertTrue(bag2 ==new_game.getBags().get(1).getPebbles());
		assertTrue(bag3 == new_game.getBags().get(2).getPebbles());
		
	}

	@Test
	public void testGetPlayers() throws NumberFormatException, InputMismatchException, IOException {
		System.out.println("GetPlayers test");
		PebbleGame new_game = new PebbleGame();
		new_game.createPlayers();
		assertEquals(3, new_game.getPlayers().size()); // checks if 3 players created when user inputs 3
		assertTrue(new_game.getPlayers().size()>0);
	}

	@Test
	public void testGetOutput() throws NumberFormatException, FileNotFoundException, IOException, IllegalFormat {
		ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		System.out.println("Please enter file_with_not_enough_pebbles.txt :");
		PebbleGame new_game = new PebbleGame();
		new_game.readFile();
		assertTrue(expected.containsAll(new_game.getOutput())); // check if expected output is the same as recent file read
	}

}

