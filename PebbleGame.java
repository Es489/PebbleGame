
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.concurrent.atomic.AtomicBoolean;


public class PebbleGame {
	
	
	private ArrayList<Player> players;
	private ArrayList<BlackBag> bags;
	
	private ArrayList<Integer> file_output = new ArrayList<Integer>();
	AtomicBoolean cond = new AtomicBoolean();
	
    public PebbleGame() {
    	PebbleGame.this.players = new ArrayList<Player>();
    	PebbleGame.this.bags =  new ArrayList<BlackBag>();
    }
    
    /**
     * This method reads the file,specified by user via input string and stores it in updatable variable file_output. 
     * It checks if the file exists, if it has illegal values and 
     * if there is enough values for all players.
     * It asks the user for input file until all prerequisites are satisfied.
     * @throws IOException
     * @throws IllegalFormat - if there is not enough pebbles for players.
     * @throws NumberFormatException - if file contains illegal values.
     */
    
	public void readFile() throws IOException, IllegalFormat, NumberFormatException, FileNotFoundException {
		ArrayList<Integer> file_values = new ArrayList<Integer>();
		Scanner user_input = new Scanner(System.in); // takes user input from command line
		
		try {
			
			String file = user_input.nextLine();
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			String[] integer_strings = br.readLine().split(",");
			br.close();
			
			if (!(integer_strings.length>11*PebbleGame.this.players.size())) { 
				throw new IllegalFormat();
				}
			
			for (String i: integer_strings) {
				Integer string_value = Integer.parseInt(i);
				if(string_value<0) {throw new NumberFormatException("File contains illegal values, please choose another file: ");}
				else {
			    file_values.add(string_value);
				}
			}
			
			PebbleGame.this.file_output = file_values;
			
		  }catch(FileNotFoundException e){
			  System.out.println("There is no such file, please choose another:");
			  PebbleGame.this.readFile();
			 
			   
		  }catch(IllegalFormat e ) {
			  System.out.println("There is not enough pebbles for players, please choose another file:");
			  
			  PebbleGame.this.readFile();
			  
		   }catch(NumberFormatException e) {
				  e.getMessage();
				  PebbleGame.this.readFile();
			  }
		
		 }
		  
		
		 /**
		  * This method creates specified by user input number of players and adds them to array of players in PebbleGame.
		  * It checks if number_of_players is legal integer value, otherwise user asked to enter another value.
		  * 
		  * number_of_players - Integer from user input
		  * @throws NumberFormatException
		  * @throws IOException
		  * @throws InputMismatchException
		  */
		public void createPlayers() throws NumberFormatException, IOException, InputMismatchException {
			System.out.println("Please eneter the number of players:");
			Scanner user_input = new Scanner(System.in);
		
			try {
			
				Integer number_of_players = Integer.parseInt(user_input.next());
				if(number_of_players<0) {throw new Exception();} // checks if number of players > 0
		    
				if(! (number_of_players instanceof Integer)) {throw new Exception();} // checks if number of players is Integer value
		    
				
				for (int i= 1; i<(number_of_players+1); i++) {
					Player y = new Player(i);
					PebbleGame.this.players.add(y);
				}
		 
			}catch (Exception e) {
			
				System.out.println("Illegal number format, please enter different number" );
				PebbleGame.this.createPlayers();
			}
		
		   }
			
			
			
		/**
		 * This method creates three black and white bags with specified pebbles values in each argument array of integers.
		 * It pairs corresponding white bag to each black bag.
		 * All created bags contained within array of bags in PebbleGame class.
		 * 
		 * @param one - array of integers for the first bag
		 * @param two - array of integers for the second bag
		 * @param three - array of integers for the third bag
		 * @throws FileNotFoundException
		 * @throws IOException
		 * @throws IllegalFormat
		 */
	
	public void createBags(ArrayList<Integer> one, ArrayList<Integer> two, ArrayList<Integer> three) throws FileNotFoundException, IOException, IllegalFormat {
		
		BlackBag X = new BlackBag(one, "X");
		
		BlackBag Y = new BlackBag(two , "Y");
		
		BlackBag Z = new BlackBag(three, "Z");
		
		
		BlackBag.WhiteBag A = X.new WhiteBag("A");
		BlackBag.WhiteBag B = Y.new WhiteBag("B");
		BlackBag.WhiteBag C = Z.new WhiteBag("C");

		X.setPair(A);
		Y.setPair(B);
		Z.setPair(C);
		
		PebbleGame.this.bags.add(X);
		PebbleGame.this.bags.add(Y);
		PebbleGame.this.bags.add(Z);
		
		
		
	}
	
	public ArrayList<Player> getPlayers(){
		return PebbleGame.this.players;
	}
	
	public ArrayList<BlackBag> getBags(){
		return PebbleGame.this.bags;
	}
	
	/**
	 * This method returns the array of integers from recently read file
	 * @return
	 */
	public ArrayList<Integer> getOutput(){
	       return PebbleGame.this.file_output;
    }
	

	
	 class Player implements Runnable {
		
		private ArrayList<Integer> hand = new ArrayList<Integer>();
		private BlackBag chosen_bag;
		private File player_file_output;
		private FileWriter writer = null;
		private int number;
		
	    
		public Player(int id) throws IOException {
			this.number = id;
			this.player_file_output = new File(this.playerName()+"_output.txt");
			writer = new FileWriter(player_file_output);
		}
		
		

		/**
		 * This method returns the sum of pebble values in players hand.
		 *  
		 */
		public int checkSumPebbles () {	
			int sum = 0;
			for (Integer i: this.getHand()) {
				sum+=i;
			}
			return sum;
		}

		/**
		 * This method assign a Black bag value to player for arrays of black bags in PebbleGame.
		 */
		public  void chooseBag() {
			this.chosen_bag = PebbleGame.this.bags.get(new Random().nextInt(PebbleGame.this.bags.size()));
		}
		
		/**
		 * This method closes player's file output stream.
		 * @throws IOException
		 */
		public void closeF() throws IOException {
			
			try {
			writer.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		/***
		 * This method takes specified number of pebble values from current Black bag and 
		 * place them into player's hand.
		 * Chosen values removed from the current Black bag.
		 * If current Black bag is empty, it will be filled with it's paired White bag pebbles, 
		 * another black bag will be chosen by player and another attempt to draw a pebble will be taken
		 * @param x - number of pebbles to draw from current Black bag.
		 * @throws IOException
		 */
		public void draw (int x) throws IOException {
			synchronized(this.chosen_bag) {
				try {
			
			     for (int i=0; i<x; i++) {
			    	 
				/*
				 * random index of pebble from current Black bag is chosen according to current bag size
				 */
			    	 
				int random_index = new Random().nextInt(this.chosen_bag.getPebbles().size()); 
				
			    this.writer.write(this.playerName() +" has drawn a " + this.chosen_bag.getPebbles().get(random_index) +" from bag"+ " " + this.chosen_bag.getName()+ " and hand sum is "+ this.checkSumPebbles() + '\n');
				hand.add(this.chosen_bag.getPebbles().get(random_index));
				this.writer.write(this.playerName()+ " hand is " + this.getHand()+'\n');
				this.chosen_bag.getPebbles().remove(random_index);	
		      } 
				}catch(Exception e) {
		    	        this.chosen_bag.getPair().fillBlackBag();
					this.chooseBag();
					this.draw(x);
		      }
			}
		
		}
		
		/***
		 * This method discard random pebble value from the player's hand and place it to current
		 * Black bag pair. 
		 * Result of the discard is written to players output file, including player's hand values after discard, 
		 * discarded value and name of the paired White bag.
		 * @throws IOException 
		 */
		public  void discard () throws IOException {
			synchronized(this.chosen_bag) {
			   int pebble = new Random().nextInt(this.hand.size());
			   this.writer.write(this.playerName() + " has discarded a " + this.hand.get(pebble) + " to bag "+ this.chosen_bag.getPair().getName()+ '\n');
			   this.chosen_bag.getPair().getPebbles().add(this.hand.get(pebble));
			   this.hand.remove(pebble);
			   writer.write(this.playerName() + " hand is " + this.getHand()+ " and hand sum is "+ this.checkSumPebbles() + '\n');
			}
			
		}
		
		
		public  ArrayList<Integer> getHand() {
			return this.hand;
		}
		
		public  String playerName() {
			return "Player"+this.number;
		}
		
		
		
		@Override
		public void run() {
			
			
				try {
				this.chooseBag();
				this.draw(10);
				while(this.checkSumPebbles()!=100 && !(PebbleGame.this.cond.get())) {
					System.out.println("yes");
					this.discard();
					this.chooseBag();
					
					this.draw(1);
					
					
				}
				
					
				
				PebbleGame.this.cond.set(true);
				this.closeF();
				if(this.checkSumPebbles()==100) {
				System.out.println(this.playerName()+" has won ");
				}
				}catch(Exception e) {
					
					e.printStackTrace();
				}
				
	
			
		}
	}
		
	
	 public static void main(String[] args) throws IOException, IllegalFormat{
			PebbleGame p = new PebbleGame();
			p.createPlayers();
			System.out.println("Please enter location of bag number 0 to load: ");
			p.readFile();
			ArrayList<Integer> w = p.getOutput();
			System.out.println("Please enter location of bag number 1 to load:");
			p.readFile();
			ArrayList<Integer> w1 = p.getOutput();
			System.out.println("Please enter location of bag number 2 to load:");
			p.readFile();
			ArrayList<Integer> w2 = p.getOutput();

			p.createBags(w, w1, w2);
		    
			for (PebbleGame.Player i : p.getPlayers()) {
				new Thread(i).start();
			}
		}	
}




	   
	    

		
		
	


	

