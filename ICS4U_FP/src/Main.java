/* Date: June 16, 2022
 * Author: Beatrice Ge
 * Course: ICS4U
 * Description: Dumpling Master Game
 */

import java.io.*;

import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

	
	//DECLARATION OF VARIABLES=======================================================================================
	static Restaurant savedGame;
	static boolean exit;
	static Scanner input = new Scanner(System.in);
	static DecimalFormat dollar = new DecimalFormat("$#,###0.00");
	
	
	//MAIN METHOD=======================================================================================================
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//DECLARATION OF VARIABLES---------------------------------------------------------------------------------------
		savedGame = new Restaurant();
		exit = false;
		
		String fileName = "";
		String userInput = "";
	
		DailyEarnings earnings = new DailyEarnings();
		
		
		//INTRODUCTION---------------------------------------------------------------------------------------------------
		System.out.println("Welcome to DUMPLING MASTER!");
		System.out.println("\n\n*Whenever you are asked to enter something, do not include curly brackets.");
		
		//storyline introduction
		System.out.println("\nSkip Intro?");
		System.out.println("You have the option of skipping the storyline introduction in this game.");
		System.out.print("\nEnter {SKIP} if yes and anything else if no: ");
		userInput = input.nextLine().trim();
		if (userInput.equalsIgnoreCase("SKIP") == false && userInput.equalsIgnoreCase("{SKIP}") == false) 
		{
			displayStoryline();
		}
		
		//tutorial
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("Skip Tutorial?");
		System.out.println("This tutorial is highly recommended for new players");
		System.out.print("\nEnter {SKIP} if yes and anything else if no: ");
		userInput = input.nextLine().trim();
		if (userInput.equalsIgnoreCase("SKIP") == false) 
		{
			displayTutorial();
		}
		
		System.out.println("\nYou will be able to revisit the tutorial by going in the KITCHEN \nand viewing the MANUAL.");
		System.out.print("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println(GraphicsSet.getTitle()); //title screen
		
		
		//START NEW OR LOAD GAME------------------------------------------------------------------------------------------
		System.out.print("Enter choice: ");
		userInput = input.next();
		input.nextLine(); //consume line
		
		//validates user input
		while (userInput.equals("1") == false && userInput.equals("2") == false) 
		{
			System.out.print("Enter a valid choice: ");
			userInput = input.next();
			input.nextLine(); //consume line
		}
				
		//start new game
		if (userInput.equals("1"))
		{
			String name;
			
			//create file name
			System.out.print("\nCreate a name for the file: ");
			fileName = input.nextLine().trim();
			createFile(fileName);
			
			readFile("C:\\Users\\runiu\\Desktop\\" + "Initial Game Data");
			
			//input restaurant name
			System.out.print("\nGive your amazing restaurant a name: ");
			name = input.nextLine().trim();
			savedGame.setRestaurantName(name);
			
			writeFile(fileName); //save game
		} 
		
		//load game
		else 
		{
			//input file name
			System.out.print("\nEnter the name of the file (without .txt) you would like to load: ");
			fileName = input.nextLine().trim();
			
			//validates if the file exists
			while (verifyFile(fileName) == false) 
			{
				System.out.print("Enter a valid file name: ");
				fileName = input.nextLine().trim();
			}

			readFile(fileName); //load game
			
			System.out.println("\nWelcome back to " + savedGame.getRestaurantName() + "!");
		
			//check if user is already bankrupt
			if (savedGame.getCurrentBalance() < 0) {
				//game over
				System.out.println("\n------------------------------------------------------------------------");
				System.out.println("\nYou are already bankrupt!");
				System.out.println("\n _____                  _____             \r\n"
								 + "|   __|___ _____ ___   |     |_ _ ___ ___ \r\n"
								 + "|  |  | .'|     | -_|  |  |  | | | -_|  _|\r\n"
								 + "|_____|__,|_|_|_|___|  |_____|\\_/|___|_|\n");
				exit = true;
			}
		}
		
		//GAME LOOP------------------------------------------------------------------------------------------------------
		while (exit == false)
		{
			
			if (savedGame.getIsDay()) {
				//run day processes (completing orders, cooking dumplings)
				runDay(userInput, earnings);
			}

			//check if bankrupt
			if (savedGame.getCurrentBalance() < 0) { 
				//game over
				System.out.println("\n------------------------------------------------------------------------");
				System.out.println("You are bankrupt!");
				System.out.print("\nContinue {ENTER}: ");
				input.nextLine();
				System.out.println("\n _____                  _____             \r\n"
								 + "|   __|___ _____ ___   |     |_ _ ___ ___ \r\n"
								 + "|  |  | .'|     | -_|  |  |  | | | -_|  _|\r\n"
								 + "|_____|__,|_|_|_|___|  |_____|\\_/|___|_|");
				exit = true;
			}
			
			if (exit == false)
			{
				//run preparation processes (stocking ingredients, unlocking new ingredients)
				runPreparation(userInput);
			}
		}
		
		
		//SAVE AND EXIT GAME----------------------------------------------------------------------------------------------
		writeFile(fileName); 		
		System.out.println("\n\nDumpling Master 2022");
	}
	
	
	
	//GAME EVENTS & PROCESSES=============================================================================================
	
	/**Pre: Takes in String for user input, and DailyEarnings object to store the earnings for the day
	 * Post: The program runs all the events and processes that occur during the day
	 * Description: Runs all the events and processes that occur during the day */
	public static void runDay(String userInput, DailyEarnings earnings) {
		Dumpling userDumpling;
		
		//BEGIN DAY
		//display current day
		System.out.println("\n________________________________________________________________________");
		System.out.println(" _________   \r\n"
			             + "|  DAY " + savedGame.getCurrentDay() + "  |   \r\n"
				         + "'---------' \r\n");
		System.out.print("Begin Day " + savedGame.getCurrentDay() + " {ENTER}: ");
		input.nextLine();
		
		//generate a queue of orders
		Queue orders = new Queue(savedGame.getNumberOfOrders()); 
		while (orders.isFull() == false) 
		{
			orders.enqueue(new Order(savedGame.getCookMethods(), savedGame.getDoughs(), savedGame.getSauces(), savedGame.getIngredients()));
		}
		
		//add the cost of the first order to the revenue
		earnings.setRevenue(orders.front().getTotalCost());
		
		
		//DAY LOOP
		//loops while it is still day (there are orders left) and the user does not choose to exit
		while (exit == false && savedGame.getIsDay()) { 
			//display main menu of options
			System.out.println(GraphicsSet.getOptions());
			System.out.print("Enter option: ");
			userInput = input.nextLine().trim().toUpperCase();
			
			//validates user input
			while (!userInput.equals("KITCHEN") && !userInput.equals("ORDERS") && !userInput.equals("SHOP") && !userInput.equals("UPGRADES") && !userInput.equals("EXIT")) 
			{
				System.out.print("Enter a valid option: ");
				userInput = input.nextLine().trim().toUpperCase();
			}
			
			
			//MAIN MENU
			switch(userInput) 
			{
			
			//KITCHEN 
			case "KITCHEN": 

				//KITCHEN LOOP (while user does not enter "BACK" and it is still day phase)
				while (userInput.equalsIgnoreCase("BACK") == false && savedGame.getIsDay()) { 
					//display kitchen-sub-menu of options
					System.out.println(GraphicsSet.getKitchen());
					System.out.println(GraphicsSet.getBackOption());
					System.out.print("\nEnter option: ");
					userInput = input.nextLine().trim().toUpperCase();
					
					//validates user input
					while (!userInput.equals("COOK") && !userInput.equals("INGREDIENTS") && !userInput.equals("FRIDGE") && !userInput.equals("MANUAL") && !userInput.equals("TUTORIAL") && !userInput.equals("BACK")) 
					{
						System.out.print("Enter a valid option: ");
						userInput = input.nextLine().trim().toUpperCase();
					}
					
					//kitchen sub-menu
					switch(userInput) 
					{
					
					case "COOK": //cook dumplings
						//user creates dumpling
						userDumpling = createDumpling(savedGame.getCookMethods(), savedGame.getDoughs(), savedGame.getSauces(), savedGame.getIngredients(), earnings);
						
						//sort ingredients of user's dumpling and order-generated dumpling
						insertionSort(userDumpling.getIngredients());
						insertionSort(orders.front().getDumpling().getIngredients());
						
						//serve dumplings
						System.out.println("------------------------------------------------------------------------");
						System.out.print("Serve dumplings {ENTER}: ");
						input.nextLine();
						
						//calculate and display earnings for the order
						calculateEarnings(orders.front(), orders.front().getDumpling(), userDumpling, earnings);
						
						//remove order from queue
						orders.dequeue();
						if (orders.isEmpty()) { //end of day
							runDailyEarnings(earnings); //calculate and display total daily earnings
							runLottery();
							savedGame.setIsDay(false); //not day anymore
						} else { //add to revenue
							earnings.setRevenue(earnings.getRevenue() + orders.front().getTotalCost());
						}
						
						break;
					
						
					case "FRIDGE": //view ingredients
					case "INGREDIENTS":
						//display ingredients
						displayAllIngredients(savedGame.getIngredients(), savedGame.getDoughs(), savedGame.getSauces());
						System.out.println("\n\nTIP: You can only view ingredients here. You must COOK to use ingredients.");
						System.out.println("\n------------------------------------------------------------------------");
						
						break;
						
						
					case "MANUAL": //view tutorial
					case "TUTORIAL": 
						
						displayTutorial();
						
						break;
					}
					
					//choose again
					if (userInput.equalsIgnoreCase("BACK") == false)
					{
						System.out.println("\nContinue {ENTER}: ");
						input.nextLine();
					}
					
				} 
				
				break;
			
				
			//ORDERS
			case "ORDERS":
				//display orders
				System.out.println(GraphicsSet.getOrderDisplay1());
				System.out.println(orders.front()); //display first order in queue
				System.out.println("\n ____________________________   \r\n"
					               + "|  NUMBER OF ORDERS LEFT: " + (orders.size() - 1) + "  |   \r\n"
						           + "'----------------------------'");
				System.out.println("\nTIP: Head to the KITCHEN and COOK the order.");
				System.out.println("_______________________________________________________________________");

				System.out.print("\nContinue {ENTER}: ");
				input.nextLine();
				
				break;
			
				
			//SHOP
			case "SHOP":
				//user cannot shop during the day
				System.out.println(GraphicsSet.getShop1());
				System.out.print("\nContinue {ENTER}: ");
				input.nextLine();
				
				break;
				
			
			//UPGRADES
			case "UPGRADES":
				//user cannot purchase upgrades during the day 
				System.out.println(GraphicsSet.getUpgrades1());
				System.out.print("\nContinue {ENTER}: ");
				input.nextLine();
				
				break;
			
				
			//EXIT
			case "EXIT":
				exit = true;
			}
			
		} 		
	}
	
	/**Pre: Takes in DailyEarnings object that stores the earnings for the day
	 * Post: The data in the DailyEarnings object is displayed
	 * Description: Displays the user's earnings for the day */
	public static void runDailyEarnings(DailyEarnings earnings) {
		//display end of current day
		System.out.println("\n________________________________________________________________________");
		System.out.println(" ________________   \r\n"
			             + "|  END OF DAY " + savedGame.getCurrentDay() + "  |   \r\n"
				         + "'----------------' \r\n");

		//calculate profit and current balance
		earnings.setProfit(savedGame); 
		savedGame.setCurrentBalance(savedGame.getCurrentBalance() + earnings.getProfit());
		//cost for supplies is not taken away from current balance because they are already paid for during the preparation phase
		
		System.out.println("Continue {ENTER}: ");
		input.nextLine();
	
		//display earnings
		System.out.println(earnings.toString(savedGame));
		
		System.out.println("\nContinue {ENTER}: ");
		input.nextLine();
	}
	
	/**Pre: None
	 * Post: The program runs the lottery system
	 * Description: Runs the lottery system, where the user can pick a random option with a 
	 * 				possibility of earning money. Uses a 2D array.
	 */
	public static void runLottery() { 
		//declaration of variables
		Random rand = new Random();
		
		String userInput;
		int rows = 3, cols = 3;
		int row, col;
		int choice;
		int[][] lottery = new int[rows][cols];
		boolean[][] used = new boolean[rows][cols];
		String[][] display = new String[rows][cols];
		
		//lottery system storyline
		System.out.println("\n________________________________________________________________________");
		System.out.println(" __________   \r\n"
			             + "|  CHANCE  |   \r\n"
				         + "'----------' \r\n");
		System.out.println("The nice old lady living next door loves making dumplings too.");
		System.out.println("\nShe always puts some money in a few dumpling fillings for luck and fortune.");
		System.out.println("[Chinese culture fact!]");
		System.out.println("\nWould you like to pay $10 to eat a dumpling?");
		System.out.print("\nContinue {ENTER} or {SKIP}: ");
		userInput = input.nextLine().trim().toUpperCase();
		
		if (!userInput.equals("SKIP")) {
			//take away $10 from current balance
			savedGame.setCurrentBalance(savedGame.getCurrentBalance() - 10);
			
			System.out.println("\nCHOOSE DUMPLING");
			//create and print display
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					display[i][j] = "[" + ((i * 3) + (j + 1)) + "]";
					System.out.print(display[i][j] + " ");
				}
				System.out.println();
			}
			
			//generate random lottery values
			for (int i = 0; i < 5; i++) {
				row = rand.nextInt(3);
				col = rand.nextInt(3);
				//make sure not used already
				while (used[row][col] == true) { 
					row = rand.nextInt(3);
					col = rand.nextInt(3);
				}
				if (i == 0) { //1/9 chance of $30
					lottery[row][col] = 30;
				} else if (i < 3){ //2/9 chance of $20
					lottery[row][col] = 20;
				} else { //2/9 chance of $10
					lottery[row][col] = 10;
				}			
				//set as used
				used[row][col] = true;
			}
			
			//choose option
			System.out.print("\nEnter number: ");
			choice = input.nextInt();
			input.nextLine();
			
			//validates user input
			while (choice < 1 || choice > (rows * cols)) {
				System.out.print("Enter a valid number: ");
				choice = input.nextInt();
				input.nextLine();
			}
			
			//reveal lottery
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (lottery[i][j] == 0) {
						System.out.print("$0  ");
					} else {
						System.out.print("$" + lottery[i][j] + " ");
					}
				}
				System.out.println();
			}
			
			System.out.println("\nContinue {ENTER}: ");
			input.nextLine();
			
			//display results
			System.out.println("\nYou earned $" + lottery[(choice - 1)/rows][(choice - 1)%cols]);
			savedGame.setCurrentBalance(savedGame.getCurrentBalance() + lottery[(choice - 1)/rows][(choice - 1)%cols]);
			System.out.println("Current balance: " + dollar.format(savedGame.getCurrentBalance()));
		}

	}
	
	/**Takes in String for user input
	 * Post: The program runs all the events and processes that occur during the preparation phase
	 * Description: Runs all the events and processes that occur during the preparation phase */
	public static void runPreparation(String userInput) {
		//PREPARATION PHASE
		System.out.println("\n________________________________________________________________________");
		System.out.println(" _____________________   \r\n"
			             + "|  PREPARATION PHASE  |   \r\n"
				         + "'---------------------'");
		
		//PREPARATION PHASE LOOP
		//loops while it is still preparation phase (user has not chosen to begin next day) and the user does not choose to exit
		while (exit == false && savedGame.getIsDay() == false) { 
			//display main menu of options
			System.out.println(GraphicsSet.getOptions());
			System.out.print("Enter option: ");
			userInput = input.nextLine().trim().toUpperCase();
			
			//validates user input
			while (!userInput.equals("KITCHEN") && !userInput.equals("ORDERS") && !userInput.equals("SHOP") && !userInput.equals("UPGRADES") && !userInput.equals("EXIT")) 
			{
				System.out.print("Enter a valid option: ");
				userInput = input.nextLine().trim().toUpperCase();
			}
			
			
			//MAIN MENU
			switch(userInput) 
			{
			
			//KITCHEN
			case "KITCHEN":

				//KITCHEN LOOP (while user does not enter "BACK")
				while (userInput.equalsIgnoreCase("BACK") == false) { 
					//display kitchen sub-menu of options
					System.out.println(GraphicsSet.getKitchen());
					System.out.println(GraphicsSet.getBackOption());
					System.out.print("\nEnter option: ");
					userInput = input.nextLine().trim().toUpperCase();
					
					//validates user input
					while (!userInput.equals("COOK") && !userInput.equals("INGREDIENTS") && !userInput.equals("FRIDGE") && !userInput.equals("MANUAL") && !userInput.equals("TUTORIAL") && !userInput.equals("BACK")) 
					{
						System.out.print("Enter a valid option: ");
						userInput = input.nextLine().trim().toUpperCase();
					}
					
					//kitchen sub-menu
					switch(userInput) 
					{
					case "COOK": //user cannot cook dumplings during the preparation phase
						System.out.println("\nYou cannot cook dumplings during the preparation phase!");
						System.out.println("\nTIP: Go to ORDERS to begin the next day.");
						
						break;
						
					case "INGREDIENTS": //display ingredients
					case "FRIDGE":
						displayAllIngredients(savedGame.getIngredients(), savedGame.getDoughs(), savedGame.getSauces());
						System.out.println("\n\nTIP: You can only view ingredients here.");
						System.out.println("\n------------------------------------------------------------------------");
						
						break;
						
						
					case "MANUAL": //view tutorial
					case "TUTORIAL": 
						
						displayTutorial();
						
						break;
	
					}
					
					
					//choose again
					if (userInput.equalsIgnoreCase("BACK") == false)
					{
						System.out.println("\nContinue {ENTER}: ");
						input.nextLine();
					}
					
				} 
				
				break;
			
				
			//ORDERS
			case "ORDERS":
				//display orders display
				System.out.println(GraphicsSet.getOrderDisplay2());
				
				System.out.print("Continue preparing {ENTER} or {BEGIN} next day: ");
				userInput = input.nextLine().trim();
				
				if (userInput.equalsIgnoreCase("BEGIN")) { //begin next day
					savedGame.setIsDay(true);
				} 
				
				break;
			
				
			//SHOP
			case "SHOP":
				
				//SHOP LOOP (while user does not enter "BACK")
				while (userInput.equalsIgnoreCase("BACK") == false) { 
					System.out.println(GraphicsSet.getShop2());
						
					//display all ingredients
					System.out.println("  _________________                          \r\n"
								     + " |   INGREDIENTS   |      CURRENT BALANCE: " + dollar.format(savedGame.getCurrentBalance()) + "\r\n"
								     + " '-----------------'");	
					displayAllIngredients(savedGame.getIngredients(), savedGame.getDoughs(), savedGame.getSauces());
					System.out.println(GraphicsSet.getBackOption());
					
					System.out.print("\nContinue {ENTER} or {BACK}: ");
					userInput = input.nextLine().trim();
					
					//stock ingredients
					if (userInput.equalsIgnoreCase("BACK") == false) {
						purchaseIngredients();
					
						System.out.println("\nContinue {ENTER}: ");
						input.nextLine();
					}
					
				} 
				
				break;
			
				
			//UPGRADES
			case "UPGRADES":
				System.out.println(GraphicsSet.getUpgrades2());

				//display current balance
				System.out.println("\nCURRENT BALANCE: " + dollar.format(savedGame.getCurrentBalance()));
				System.out.println("\nYou are able to unlock new ingredients here. \nUnlocking any ingredient costs $30.");
				
				System.out.println("\nContinue {ENTER}: ");
				input.nextLine();
				
				if (savedGame.getCurrentBalance() < 30) { //not enough money to unlock ingredients
					System.out.println("\nYou currently do not have enough money to unlock ingredients.");
				} else { //unlock ingredient
					unlockIngredient();
				}
				
				System.out.println("\nContinue {ENTER}: ");
				input.nextLine();
				
				break;
				
				
			//EXIT
			case "EXIT":
				exit = true;
			}
			
			
			//BEGIN DAY
			if (savedGame.getIsDay()) {
				savedGame.setCurrentDay(savedGame.getCurrentDay() + 1); //increase current day by one
			}
			
		}
	}
	
	
	
	//HELPING METHODS============================================================================================================
	/**Pre: None
	 * Post: The quantity of the purchased Ingredient is increased and the user's current balance is decreased depending on the Ingredient and number of servings bought
	 * Description: Purchases a number of servings of an Ingredient */
	public static void purchaseIngredients() {
		//declaration of variables
		int choice, servings;
		boolean purchased = false;
		String userInput;
		
		//choose category
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("\n[1] " + Dough.getCategory());
		System.out.println("[2] VEGETABLE, MEAT, SEAFOOD");
		System.out.println("[3] " + Sauce.getCategory());
		
		System.out.print("\nChoose category: ");
		userInput = input.nextLine().trim();
		
		//validates user input
		while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")) { 
			System.out.print("\nChoose a valid category: ");
			userInput = input.nextLine().trim();
		}
		
		System.out.println(); //white space
		
		//DOUGH
		if (userInput.equals("1")) { 
			displayIngredientInfo(savedGame.getDoughs());
			System.out.print("\nChoose dough to purchase: ");
			choice = input.nextInt() - 1;
			input.nextLine(); //consume line
			
			//validates user input
			while (choice < 0 || choice > savedGame.getDoughs().length - 1) { 
				System.out.print("\nChoose a valid dough: ");
				choice = input.nextInt() - 1;
				input.nextLine(); //consume line
			}
			
			System.out.print("\nEnter number of servings to purchase: ");
			servings = input.nextInt();
			input.nextLine(); //consume line
			
			//checks if user has enough money
			if (servings * savedGame.getDoughs()[choice].getCost() > savedGame.getCurrentBalance()) {
				System.out.println("\nYou do not have enough money for this purchase.");
			} else {
				purchased = true;
				//update current balance and quantity
				savedGame.setCurrentBalance(savedGame.getCurrentBalance() - (servings * savedGame.getDoughs()[choice].getCost()));
				savedGame.getDoughs()[choice].setQuantity(savedGame.getDoughs()[choice].getQuantity() + servings);
			}
			
		//INGREDIENTS (VEGETABLES, MEAT, SEAFOOD)
		} else if (userInput.equals("2")) { 
			displayIngredientInfo(savedGame.getIngredients());
			System.out.print("\nChoose ingredient to purchase: ");
			choice = input.nextInt() - 1;
			input.nextLine(); //consume line
			
			//validates user input
			while (choice < 0 || choice > savedGame.getIngredients().length - 1) { //validation
				System.out.print("\nChoose a valid ingredient: ");
				choice = input.nextInt() - 1;
				input.nextLine(); //consume line
			}
			
			System.out.print("\nEnter number of servings to purchase: ");
			servings = input.nextInt();
			input.nextLine(); //consume line
			
			//checks if user has enough money
			if (servings * savedGame.getIngredients()[choice].getCost() > savedGame.getCurrentBalance()) {
				System.out.println("\nYou do not have enough money for this purchase.");
			} else {
				purchased = true;
				//update current balance and quantity
				savedGame.setCurrentBalance(savedGame.getCurrentBalance() - (servings * savedGame.getIngredients()[choice].getCost()));
				savedGame.getIngredients()[choice].setQuantity(savedGame.getIngredients()[choice].getQuantity() + servings);
			}
			
		//SAUCE
		} else { 
			displayIngredientInfo(savedGame.getSauces());
			System.out.print("\nChoose sauce to purchase: ");
			choice = input.nextInt() - 1;
			input.nextLine();
			
			//validates user input
			while (choice < 0 || choice > savedGame.getSauces().length - 1) { //validation
				System.out.print("\nChoose a valid sauce: ");
				choice = input.nextInt() - 1;
				input.nextLine();
			}
			
			System.out.print("\nEnter number of servings to purchase: ");
			servings = input.nextInt();
			input.nextLine(); //consume line
			
			//checks if user has enough money
			if (servings * savedGame.getSauces()[choice].getCost() > savedGame.getCurrentBalance()) {
				System.out.println("\nYou do not have enough money for this purchase.");
			} else {
			purchased = true;
			//update current balance and quantity
			savedGame.setCurrentBalance(savedGame.getCurrentBalance() - (servings * savedGame.getSauces()[choice].getCost()));
			savedGame.getSauces()[choice].setQuantity(savedGame.getSauces()[choice].getQuantity() + servings);
			}
		}
		
		if (purchased == true) {
			System.out.println("\nPurchased!");
			System.out.println("\nCurrent Balance: " + dollar.format(savedGame.getCurrentBalance()));
		}
	}
	
	/**Pre: Takes in an array of CookMethods, an array of Doughs, an array of Sauces, an array of Ingredients, and a DailyEarnings object that stores the earnings for the day
	 * Post: Returns a Dumpling with values chosen by the user
	 * Description: Creates a Dumpling based on user input */
	public static Dumpling createDumpling(CookMethod[] cookMethods, Dough[] doughs, Sauce[] sauces, Ingredient[] ingredients, DailyEarnings earnings) {
		//declaration of variables
		boolean valid = false;
		int userInput;
		Dumpling d;
		
		Dough chosenDough = new Dough();
		CookMethod chosenCook = new CookMethod();
		Sauce chosenSauce = new Sauce();
		ArrayList<Ingredient> chosenIngredients = new ArrayList<Ingredient>(); //arraylists
		
		//CHOOSING DOUGH
		System.out.println("\nChoose dough: ");
		displayIngredientNames(doughs);
		System.out.print("\nChoose (Enter {0} to stop): ");
		userInput = input.nextInt() - 1;
		input.nextLine(); //consume next line
		
		valid = checkValid(doughs, userInput);
		
		//validates user input
		while (valid == false) { 
			System.out.print("Choose valid dough: ");
			userInput = input.nextInt() - 1;
			input.nextLine(); //consume next line
			valid = checkValid(doughs, userInput);
		}
		//set dough and add the cost to supplies used
		if (userInput != -1) {
			doughs[userInput].setQuantity(doughs[userInput].getQuantity() - 1); //decrease quantity
			chosenDough.setName(doughs[userInput].getName());
			chosenDough.setCost(doughs[userInput].getCost());
			chosenDough.setQuantity(doughs[userInput].getQuantity());
			earnings.setSuppliesUsed(earnings.getSuppliesUsed() + doughs[userInput].getCost());
		}
		
		//CHOOSING INGREDIENTS
		System.out.println("\nAdd ingredients (Enter {0} to stop):");
		displayIngredientNames(ingredients);
		System.out.print("\nAdd: ");
		userInput = input.nextInt() - 1;
		input.nextLine(); //consume next line
		while (userInput != -1) //continue adding ingredients
		{
			valid = checkValid(ingredients, userInput);
			
			//validates user input
			while (valid == false) {
				System.out.print("Add valid ingredient: ");
				userInput = input.nextInt() - 1;
				input.nextLine(); //consume next line
				valid = checkValid(ingredients, userInput);
			}
			if (userInput != -1) { //add ingredient
				chosenIngredients.add(ingredients[userInput]);
				ingredients[userInput].setQuantity(ingredients[userInput].getQuantity() - 1); //decrease quantity
				earnings.setSuppliesUsed(earnings.getSuppliesUsed() + ingredients[userInput].getCost());
				
				System.out.print("Add: ");
				userInput = input.nextInt() - 1;
				input.nextLine(); //consume next line
			}
		}
		
		//arrayList to array conversion
		Ingredient[] arrChosenIngredients = new Ingredient[chosenIngredients.size()];
		
        for (int i = 0; i < chosenIngredients.size(); i++) {
        	arrChosenIngredients[i] = chosenIngredients.get(i);
        }
        
        //COOK TYPE
        System.out.println("\nChoose the cook type:");
        for (int i = 0; i < cookMethods.length; i++) 
        {
        	System.out.println("[" + (i + 1) + "] " + cookMethods[i].getName());
        }
        System.out.print("\nChoose: ");
        userInput = input.nextInt() - 1;
		input.nextLine(); //consume next line
		//validates user input
		while (userInput >= cookMethods.length || userInput < 0) {
			System.out.print("Choose valid cook type: ");
	        userInput = input.nextInt() - 1;
			input.nextLine(); //consume next line
		}
        chosenCook = cookMethods[userInput];
        
        
        //SAUCE
        System.out.println("\nChoose sauce:");
        displayIngredientNames(sauces);
        System.out.print("\nChoose (Enter {0} to stop): ");
		userInput = input.nextInt() - 1;
		input.nextLine(); //consume next line
		
		valid = checkValid(sauces, userInput);
		
		//validates user input
		while (valid == false) { 
			System.out.print("Choose valid sauce: ");
			userInput = input.nextInt() - 1;
			input.nextLine(); //consume next line
			valid = checkValid(sauces, userInput);
		}
		//set sauce and add the cost to supplies used
		if (userInput != -1) {
			sauces[userInput].setQuantity(sauces[userInput].getQuantity() - 1);
			chosenSauce.setName(sauces[userInput].getName());
			chosenSauce.setCost(sauces[userInput].getCost());
			chosenSauce.setQuantity(sauces[userInput].getQuantity());
			earnings.setSuppliesUsed(earnings.getSuppliesUsed() + sauces[userInput].getCost());
		}
		
		//CREATE DUMPLING
        d = new Dumpling(chosenCook, chosenDough, chosenSauce, arrChosenIngredients);
        
        return(d);
	}
	
	/**Pre: Takes in array of Ingredients, Integer for user input
	 * Post: Returns true if the user chose a valid Ingredient
	 * Description: Checks if the user chose a valid Ingredient (in the array and not out of stock)*/
	public static boolean checkValid(Ingredient[] ing, int userInput) {
		if (userInput == -1) { //stop adding ingredients
			return(true);
		} else if (userInput < 0 || userInput >= ing.length) { //invalid input
			return(false);
		} else if (ing[userInput].getQuantity() == 0) { //out of stock
			System.out.println("[OUT OF STOCK]: ");
			return(false);
		} else {
			return(true);
		}
	}
	
	/**Pre: Takes in an Order, the ordered Dumpling, the user's Dumpling, and a DailyEarnings object that stores the earnings for the day
	 * Post: The daily earnings are calculated and displayed
	 * Description: Calculates and displays the earnings for a day based on the accuracy of the user's Dumpling to the ordered Dumpling */
	public static void calculateEarnings(Order order, Dumpling ordered, Dumpling user, DailyEarnings earnings) {
		//declaration of variables
		double tips = 0;
		double refunds = 0;
		Ingredient[] missingIngredients = determineMissing(ordered, user); //determine missing ingredients
		int numMissing = missingIngredients.length;
		String review = "";
		
		//determine earnings based on ingredient accuracy
		
		//PERFECT ORDER: 							Tips 30% of order
		//NO MISSING INGREDIENTS BUT IMPERFECT:		Tips 10% of order
		//ONE MISSING INGREDIENT: 					Refunds 50% of order
		//TWO OR MORE MISSING INGREDIENTS: 			Refunds 100% of order
		
		//for all that apply:
		//WRONG COOK METHOD							Refund additional $1
		//WRONG DOUGH								Refund additional $1
		//WRONG SAUCE								Refund additional $1
	
		if (ordered.equals(user)) //perfect order
		{ 
			tips = order.getTotalCost() * 0.3;
			review = "You are POGGING!";
		} 
		else //imperfect order
		{
			if (numMissing == 1) //only one missing ingredient
			{ 
				refunds = order.getTotalCost() * 0.5;
				review = "Not the worst, but I want a partial refund.";
			} 
			else if (numMissing > 1) //more than one missing ingredient
			{ 
				refunds = order.getTotalCost();
				review = "This isn't what I asked for!";
			} 
			else //no missing ingredients, but not completely accurate order
			{ 
				tips = order.getTotalCost() * 0.1;
				review = "A bit off, but that's alright.";
			}
			
			//mistakes
			review += "\n\nMistakes:";
			//missing ingredients
			for (int i = 0; i < numMissing; i++) {
				review += "\n- Missing " + missingIngredients[i].getName().toLowerCase();
			}
			//extra ingredients
			if (ordered.getNumberOfIngredients() < user.getNumberOfIngredients()) {
				review += "\n- You gave me ingredients I didn't ask for";
			}
			//check cook method
			if (!ordered.getCookMethod().equals(user.getCookMethod())) {
				review += "\n- I asked for " + ordered.getCookMethod().getName().toLowerCase() + " dumplings";
				refunds += 1;
			} 
			//check dough
			if (!ordered.getDough().equals(user.getDough())) {
				if (user.getDough().getName().equals("[NAME]")) {
					review += "\n- You used no dough!";
				} else {
					review += "\n- You used " + user.getDough().getName().toLowerCase() + " dough instead of " + ordered.getDough().getName().toLowerCase();
				}
				refunds += 1;
			}
			//check sauce
			if (!ordered.getSauce().equals(user.getSauce())) {
				if (user.getSauce().getName().equals("[NAME]")) {
					review += "\n- You gave me no sauce!";
				} else {
					review += "\n- You gave me " + user.getSauce().getName().toLowerCase() + " instead of " + ordered.getSauce().getName().toLowerCase();
				}
				refunds += 1;
			}
		}
			
		//calculate tips and refunds
		earnings.setTips(earnings.getTips() + tips);
		earnings.setRefunds(earnings.getRefunds() + refunds);
		
		//display daily earnings
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("~ REVIEW ~");	
		System.out.println(review);
		System.out.println("\nCost: " + dollar.format(order.getTotalCost()));
		System.out.println("Tips: " + dollar.format(tips));
		System.out.println("Refunds: " + dollar.format(refunds));
		System.out.println("------------------------------------------------------------------------");
	}
	
	/**Pre: Takes in an ordered Dumpling and the user's Dumpling
	 * Post: Returns an array containing the Ingredients that are missing from the user's Dumpling
	 * Description: Determines the Ingredients that are missing from the user's Dumpling */
	public static Ingredient[] determineMissing(Dumpling ordered, Dumpling user) {
		//declaration of variables
		int numberOfIngredients = ordered.getNumberOfIngredients();
		ArrayList<Ingredient> missingIngredients = new ArrayList<Ingredient>();
		
		//count missing ingredients
		for (int i = 0; i < numberOfIngredients; i++) {
			if (search(user.getIngredients(), ordered.getIngredients()[i].getName(), 0, user.getNumberOfIngredients() - 1) == false) {
				missingIngredients.add(ordered.getIngredients()[i]);
			}
		}
		
		//arrayList to array conversion
		Ingredient[] arrMissingIngredients = new Ingredient[missingIngredients.size()];
		
        for (int i = 0; i < missingIngredients.size(); i++) {
        	arrMissingIngredients[i] = missingIngredients.get(i);
        }
		        
		return(arrMissingIngredients);
	}
	
	/**Pre: None
	 * Post: The locked Ingredient chosen by the user is moved to the array of unlocked Ingredients, and the user's balance is decreased by 30
	   Description: Unlocks an Ingredient chosen by the user */
	public static void unlockIngredient() {
		//declaration of ingredients
		int choice;
		int length = savedGame.getLockedIngredients().length;

		displayIngredientNames(savedGame.getLockedIngredients());
		
		//choose ingredient
		System.out.print("\nChoose ingredient number, or enter {0} to go back: ");
		choice = input.nextInt();
		input.nextLine(); //consume line
		
		while (choice < 0 || choice > length) { //validation
			System.out.print("Choose a valid option: ");
			choice = input.nextInt();
			input.nextLine(); //consume line
		}
		
		//unlock ingredient
		if (choice != 0) { 
			choice--;
			if (savedGame.getLockedIngredients()[choice] instanceof Dough) { //dough
				length = savedGame.getDoughs().length;
				savedGame.addDough();
				savedGame.getDoughs()[length] = (Dough)savedGame.getLockedIngredients()[choice]; 
			} else if (savedGame.getLockedIngredients()[choice] instanceof Sauce) { //sauce
				length = savedGame.getSauces().length;
				savedGame.addSauce();
				savedGame.getSauces()[length] = (Sauce)savedGame.getLockedIngredients()[choice];
			} else { //ingredient (vegetable, meat, seafood)
				length = savedGame.getIngredients().length;
				savedGame.addIngredient();
				savedGame.getIngredients()[length] = savedGame.getLockedIngredients()[choice];
			}
			savedGame.setCurrentBalance(savedGame.getCurrentBalance() - 30);
			savedGame.removeIngredient(choice);
			
			System.out.println("\nUnlocked!");
			System.out.println("\nCurrent Balance: " + dollar.format(savedGame.getCurrentBalance()));
		}
	}
	
	
	
	//SORTING & SEARCHING=======================================================================================================
	/**Pre: Takes in an array of Ingredients
	 * Post: The array is sorted in alphabetical order by name
	 * Description: Uses insertion sort to sort an array of Ingredients */
	public static void insertionSort(Ingredient[] arr) {
		//declaration of ingredients
		int length = arr.length;
		Ingredient currentElement;
		int previousIndex;
		
		//sort array
		for (int i = 1; i < length; i++) 
		{
			currentElement = arr[i];
			previousIndex = i - 1;
			
			while (previousIndex >= 0 && (currentElement.getName()).compareToIgnoreCase(arr[previousIndex].getName()) < 0) 
			{
				arr[previousIndex + 1] = arr[previousIndex]; //shifts the element to the right
				previousIndex--; //moves the previous index back
			}
			
			//places the current element after arr[previousIndex], because arr[previousIndex] comes first alphabetically
			arr[previousIndex + 1] = currentElement; 
		}
	}

	/**Pre: Takes in an array of Ingredients, String name of Ingredient to search for, Integer start and end bounds
	 * Post: Returns true if an Ingredient of the name being searched for is found
	 * Description: Uses binary search and recursion to determine if an Ingredient is in an array of Ingredients */
	public static boolean search(Ingredient[] arr, String toSearch, int start, int end) {
		if (start > end) //base case
		{
			return(false); //not found
		}
		else //general case
		{ 
			int middle = (start + end) / 2; //moves the middle to be between the new start and end

			if ((arr[middle].getName()).equalsIgnoreCase(toSearch)) { //if found (<toSearch> is equal to the middle)
				return(true);
				
			} else if (toSearch.compareToIgnoreCase(arr[middle].getName()) < 0) { //if <toSearch> comes alphabetically before the middle
				return(search(arr, toSearch, start, middle - 1)); 
				
			} else { //if <toSearch> comes alphabetically after the middle
				return(search(arr, toSearch, middle + 1, end));
			}
		}
	}
	
	
	
	//DISPLAYING INFORMATION=====================================================================================================================
	/**Pre: Takes in an array of Ingredients, an array of Doughs, and an array of Sauces
	 * Post: All the elements in all arrays are displayed
	 * Description: Displays all the ingredients (with their costs and quantities) */
	public static void displayAllIngredients(Ingredient[] ingredients, Dough[] doughs, Sauce[] sauces) 
	{
		//declaration of variables
		int longestNameLength, longestCostLength;
		int length = ingredients.length;
		int n = 1;
		
		//calculate the length of the longest name and cost
		if (findLongestNameLength(ingredients) >= findLongestNameLength(doughs) && findLongestNameLength(ingredients) >= findLongestNameLength(sauces)) {
			longestNameLength = findLongestNameLength(ingredients);
		} else if (findLongestNameLength(doughs) >= findLongestNameLength(ingredients) && findLongestNameLength(doughs) >= findLongestNameLength(sauces)) {
			longestNameLength = findLongestNameLength(doughs);
		} else {
			longestNameLength = findLongestNameLength(sauces);
		}
		longestCostLength = findLongestCostLength(ingredients);
		
		//doughs
		System.out.println("\n" + Dough.getCategory());
		displayIngredientInfo(doughs, longestNameLength);
	
		//vegetables
		System.out.println("\n" + Vegetable.getCategory());
		for (int i = 0; i < length; i++) {
			if (ingredients[i] instanceof Vegetable) { 
				System.out.println("[" + n + "] " + ingredients[i].toString(longestNameLength, longestCostLength)); 
				n++;
			}
		}
		
		//meat
		n = 1;
		System.out.println("\n" + Meat.getCategory());
		for (int i = 0; i < length; i++) {
			if (ingredients[i] instanceof Meat) { 
				System.out.println("[" + n + "] " + ingredients[i].toString(longestNameLength, longestCostLength)); 
				n++;
			}
		}
		
		//seafoods
		n = 1;
		System.out.println("\n" + Seafood.getCategory());
		for (int i = 0; i < length; i++) {
			if (ingredients[i] instanceof Seafood) { 
				System.out.println("[" + n + "] " + ingredients[i].toString(longestNameLength, longestCostLength)); 
				n++;
			}
		}
		
		//sauces
		System.out.println("\n" + Sauce.getCategory());
		displayIngredientInfo(sauces, longestNameLength);
	}
	
	/**Pre: Takes in an array of Ingredients
	 * Post: The names of the Ingredients in the array are displayed
	 * Description: Displays the names of the Ingredients in an array of Ingredients */
	public static void displayIngredientNames(Ingredient[] ingredients)
	{
		//declaration of variables
		int length = ingredients.length;
		
		//display names of ingredients
		for (int i = 0; i < length; i++)
		{
			System.out.println("[" + (i + 1) + "] " + ingredients[i].getName());
		}
	}
	
	/**Pre: Takes in an array of Ingredients
	 * Post: The information of each Ingredient in the array is displayed
	 * Description: Displays the information (name, cost, quantity) of the Ingredients in an array of Ingredients */
	public static void displayIngredientInfo(Ingredient[] ingredients)
	{
		//declaration of variables
		int longestNameLength = findLongestNameLength(ingredients);
		int longestCostLength = findLongestCostLength(ingredients);
		int length = ingredients.length;
		
		//display ingredient information
		for (int i = 0; i < length; i++)
		{
			System.out.println("[" + (i + 1) + "] " + ingredients[i].toString(longestNameLength, longestCostLength));
		}
	}
	
	/**Pre: Takes in an array of Ingredients and the length of the longest name 
	 * Post: The information of each Ingredient in the array is displayed
	 * Description: An overloaded method that displays the information of the Ingredients in an array of Ingredients. 
	 * 				Used to ensure neat formatting when displaying all ingredients in all arrays (doughs, sauces, etc.) */
	public static void displayIngredientInfo(Ingredient[] ingredients, int longestNameLength)
	{
		//declaration of variables
		int longestCostLength = findLongestCostLength(ingredients);
		int length = ingredients.length;
		
		//display ingredient information
		for (int i = 0; i < length; i++)
		{
			System.out.println("[" + (i + 1) + "] " + ingredients[i].toString(longestNameLength, longestCostLength));
		}
	}
	
	/**Pre: Takes in an array of Ingredients
	 * Post: Returns the length of the longest Ingredient name in the array
	 * Description: Finds the length of the longest Ingredient name in an array of Ingredients */
	public static int findLongestNameLength(Ingredient[] ingredients)
	{
		//declaration of variables
		int longestLength = 0;
		int tempLength = 0;
		
		//find longest length
		for (int i = 0; i < ingredients.length; i++) 
		{
			tempLength = ingredients[i].getName().length();
			
			//determine if longer
			if (tempLength > longestLength) 
			{
				longestLength = tempLength;
			}
		}
		return(longestLength);
	}
	
	/**Pre: Takes in an array of Ingredients
	 * Post: Returns the length of the longest Ingredient cost in the array
	 * Description: Finds the length of the longest Ingredient cost (formatted using DecimalFormat) in an array of Ingredients */
	public static int findLongestCostLength(Ingredient[] ingredients) {
		//declaration of variables
		int longestLength = 0;
		int tempLength = 0;
		
		//find longest length
		for (int i = 0; i < ingredients.length; i++) 
		{
			tempLength = dollar.format(ingredients[i].getCost()).length();
			
			//determine if longer
			if (tempLength > longestLength) 
			{
				longestLength = tempLength;
			}		
		}
		return(longestLength);
	}

	
	//OTHER DISPLAYS====================================================================================================
	/**Pre: None
	 * Post: The storyline of the game is displayed
	 * Description: Displays the storyline of the game */
	public static void displayStoryline() {			
		System.out.println("------------------------------------------------------------------------");
		System.out.println("You are a computer programmer… who works too much, barely sleeps, and \ndoes not get paid enough.");
		System.out.println("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("Turning over a new leaf, you decide to pursue your lifelong dream of \nopening a Chinese dumpling restaurant.");
		System.out.println("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("Are your ready to embark on your culinary journey?");
		System.out.print("\nContinue {YES}: ");
		
		//validates user input
		while ((input.nextLine()).trim().equalsIgnoreCase("YES") == false) 
		{
			System.out.print("Take your time. Continue {YES}: ");
		}
	}
	
	/**Pre: None
	 * Post: The tutorial is displayed
	 * Description: Displays the tutorial of the game */
	public static void displayTutorial() {
		System.out.println("------------------------------------------------------------------------");
		System.out.println("You are the owner of a Chinese dumpling restaurant…");
		System.out.println("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("You must cook dumplings and manage resources to make your restaurant \nthrive.");
		System.out.println("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("There are two main phases of the game: the day phase and the \npreparation phase.");
		System.out.print("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("\nDuring the day phase, you cook dumplings with the ingredients you have. \nYou are not allowed to stock up on or unlock new ingredients.");
		System.out.print("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("\nDuring the preparation phase, you can stock up on and unlock new \ningredients. You cannot cook dumplings in this phase.");
		System.out.print("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("\nIn between these phases, there will be a short, optional phase where \nyou have the chance to earn extra money.");
		System.out.print("\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("\n\n[INSTRUCTIONS]");
		System.out.println("\nTO COOK DUMPLINGS: "
				+ "\n  1. View and memorize the order in the ORDERS section."
				+ "\n  2. Head to the KITCHEN and COOK the dumpling.");
		
		System.out.println("\nTO VIEW INVENTORY: "
				+ "Head to the KITCHEN and look in the FRIDGE to view \n                   the ingredients you have");
		
		System.out.println("\nTO STOCK UP ON INGREDIENTS: Enter the SHOP");
		
		System.out.println("\nTO UNLOCK INGREDIENTS: Go to UPGRADES");
		
		System.out.println("\nTO BEGIN NEXT DAY: Go to the ORDERS section and BEGIN");
		System.out.print("\n\nContinue {ENTER}: ");
		input.nextLine();
		
		System.out.println("\nHAVE FUN!");
	}
	
	
	//FILE READING & WRITING=========================================================================================
	/**Pre: Takes in the name of a file
	 * Post: The data stored in a Restaurant object is written to the file
	 * Description: Writes the data stored in a Restaurant object to a file
	 */
	public static void writeFile(String fileName) {
		try {
			//declaration of variables
			FileWriter writer = new FileWriter(fileName + ".txt");
			PrintWriter pWriter = new PrintWriter(fileName + ".txt");
			
			//erases the previous contents of the file
			pWriter.print("");
			pWriter.close();
			
			//writes data to the file
			writer.write(savedGame.toString());
			writer.close();
			System.out.println("\n---Game Saved---");
		} catch (Exception e) {
			System.out.println("Error! Cannot save data.\n");
			System.out.println(e.getClass() + ": " + e.getMessage());
		}
	}
	
	/**Pre: Takes in the name of a file
	 * Post: The data in the file is read and stored to a Restaurant object
	 * Description: Reads the data in a file and stores it in a Restaurant object
	 */
	public static void readFile(String fileName) {
		try {
			//declaration of variables
			BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"));
			String line = reader.readLine();
			int len1 = 0, len2 = 0, len3 = 0, len4 = 0, len5 = 0;

			//read line by line
			while (line != null) { 
				
				//takes information from each line
				if (line.startsWith("Restaurant Name:")) {
					savedGame.setRestaurantName(line.substring(line.indexOf(':') + 2));
				} else if (line.startsWith("Current Day:")) {
					savedGame.setCurrentDay(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
				} else if (line.startsWith("Current Balance:")) {
					savedGame.setCurrentBalance(Double.parseDouble(line.substring(line.indexOf('$') + 1)));
				
				} else if (line.startsWith("Rent:")) {
					savedGame.setRent(Double.parseDouble(line.substring(line.indexOf('$') + 1)));
				} else if (line.startsWith("Number of Orders Each Day:")) {
					savedGame.setNumberOfOrders(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
				} else if (line.startsWith("Is Day:")) {
					savedGame.setIsDay(Boolean.parseBoolean(line.substring(line.indexOf(':') + 2)));
				
				} else if (line.startsWith("Number of Cook Methods:")) {
					len1 = Integer.parseInt(line.substring(line.indexOf(':') + 2));
					line = reader.readLine();
					len2 = Integer.parseInt(line.substring(line.indexOf(':') + 2));
					line = reader.readLine();
					len3 = Integer.parseInt(line.substring(line.indexOf(':') + 2));
					line = reader.readLine();
					len4 = Integer.parseInt(line.substring(line.indexOf(':') + 2));
					line = reader.readLine();
					len5 = Integer.parseInt(line.substring(line.indexOf(':') + 2));
					
					savedGame.setLengths(len1, len2, len3, len4, len5);
				}
				
				else if (line.startsWith("Cook Methods:")) {
					line = reader.readLine();
					for (int i = 0; i < len1; i++) {
						line = reader.readLine();
						savedGame.getCookMethods()[i].setName(line.substring(line.indexOf(':') + 2));
						line = reader.readLine();
						savedGame.getCookMethods()[i].setPrice(Double.parseDouble(line.substring(line.indexOf('$') + 1)));
						line = reader.readLine();
					}
				} else if (line.startsWith("Doughs:")) {
					line = reader.readLine();
					for (int i = 0; i < len2; i++) {
						line = reader.readLine();
						savedGame.getDoughs()[i].setName(line.substring(line.indexOf(':') + 2));
						line = reader.readLine();
						savedGame.getDoughs()[i].setCost(Double.parseDouble(line.substring(line.indexOf('$') + 1)));
						line = reader.readLine();
						savedGame.getDoughs()[i].setQuantity(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
						line = reader.readLine();
					}
				} else if (line.startsWith("Sauces:")) {
					line = reader.readLine();
					for (int i = 0; i < len3; i++) {
						line = reader.readLine();
						savedGame.getSauces()[i].setName(line.substring(line.indexOf(':') + 2));
						line = reader.readLine();
						savedGame.getSauces()[i].setCost(Double.parseDouble(line.substring(line.indexOf('$') + 1)));
						line = reader.readLine();
						savedGame.getSauces()[i].setQuantity(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
						line = reader.readLine();
					}
				} else if (line.startsWith("Ingredients:")) {
					line = reader.readLine();
					for (int i = 0; i < len4; i++) {
						line = reader.readLine();
						if (line.startsWith("Vegetable")) {
							savedGame.getIngredients()[i] = new Vegetable();
						} else if (line.startsWith("Meat")) {
							savedGame.getIngredients()[i] = new Meat();
						} else {
							savedGame.getIngredients()[i] = new Seafood();
						}
						line = reader.readLine();
						savedGame.getIngredients()[i].setName(line.substring(line.indexOf(':') + 2));
						line = reader.readLine();
						savedGame.getIngredients()[i].setCost(Double.parseDouble(line.substring(line.indexOf('$') + 1)));
						line = reader.readLine();
						savedGame.getIngredients()[i].setQuantity(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
						line = reader.readLine();
					}
				} else if (line.startsWith("Locked Ingredients:")) {
					line = reader.readLine();
					for (int i = 0; i < len5; i++) {
						line = reader.readLine();
						if (line.startsWith("Vegetable")) {
							savedGame.getLockedIngredients()[i] = new Vegetable();
						} else if (line.startsWith("Meat")) {
							savedGame.getLockedIngredients()[i] = new Meat();
						} else if (line.startsWith("Seafood")) {
							savedGame.getLockedIngredients()[i] = new Seafood();
						} else if (line.startsWith("Dough")) {
							savedGame.getLockedIngredients()[i] = new Dough();
						} else {
							savedGame.getLockedIngredients()[i] = new Sauce();
						}
						line = reader.readLine();
						savedGame.getLockedIngredients()[i].setName(line.substring(line.indexOf(':') + 2));
						line = reader.readLine();
						savedGame.getLockedIngredients()[i].setCost(Double.parseDouble(line.substring(line.indexOf('$') + 1)));
						line = reader.readLine();
						savedGame.getLockedIngredients()[i].setQuantity(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
						line = reader.readLine();

					}
					
				}
				
				line = reader.readLine();
			}
			reader.close();
			System.out.println("\n---Game Loaded---");
		} catch (Exception e) {
			System.out.println("Error! Cannot load data.\n");
			System.out.println(e.getClass() + ": " + e.getMessage());
		}
	}
	
	
	/**Pre: Takes in the name of a file
	 * Post: A file is created with the name of the file
	 * Description: Creates and names a file */
	public static void createFile(String fileName) {
		try {
			File file = new File(fileName + ".txt"); //creates a new file
			
			//validates if file already exists
			while (file.createNewFile() == false)
			{
				System.out.println("File already exists.");
				System.out.print("Create a name: ");
				fileName = input.nextLine();
				file = new File(fileName + ".txt");
			}
			System.out.println("File created: " + file.getName());
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	/**Pre: Takes in the name of a file
	 * Post: Returns true if the file exists and is not a directory
	 * Description: Determines if a file exists and is not a directory*/
	public static boolean verifyFile(String fileName) {
		File file = new File(fileName + ".txt");
		
		//validates the file
		if(file.exists() && !file.isDirectory())
		{
	        return(true);
	    } 
		else
	    {
	        return(false);
	    }
	}
		
}
