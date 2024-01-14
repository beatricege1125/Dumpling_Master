/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Dumpling class: stores base price, price, cook method, dough, sauce, 
 *              number of ingredients, and ingredients for a dumpling
 */

import java.text.DecimalFormat;
import java.util.Random;

public class Dumpling {
	
	//class variable
	private static double basePrice = 4;
	
	//instance variables
	Random rand = new Random();
	DecimalFormat dollar = new DecimalFormat("$#,###0.00");
	
	private double price;
	private CookMethod cookMethod;
	private Dough dough;
	private Sauce sauce;
	private int numberOfIngredients;
	private Ingredient[] ingredients;

	//constructors
	public Dumpling() 
	{
		price = 0;
		cookMethod = new CookMethod();
		dough = new Dough();
		sauce = new Sauce();
		numberOfIngredients = 0;
		ingredients = new Ingredient[numberOfIngredients];
	}
	
	/**Pre: Takes in a CookMethod object, Dough object, Sauce object, and an array of Ingredients
	 * Post: A Dumpling Object is created using the given objects
	 * Description: Creates a user-created Dumpling */
	public Dumpling(CookMethod cookMethod1, Dough dough1, Sauce sauce1, Ingredient[] ingredients1)
	{
		//cook method
		cookMethod = cookMethod1;
		
		//dough 
		dough = dough1;
		
		//sauce 
		sauce = sauce1;
		
		//number of ingredients
		numberOfIngredients = ingredients1.length;

		//ingredients
		ingredients = new Ingredient[numberOfIngredients];
		for (int i = 0; i < numberOfIngredients; i++)
		{
			ingredients[i] = ingredients1[i];
		}
		
		//price
		price = calculatePrice();
	}
	
	/**Pre: Takes in an array of CookMethods, an array of Doughs, an array of Sauces, and an array of unlocked Ingredients
	 * Post: A Dumpling Object is randomly created with random objects from each array
	 * Description: Randomly creates a Dumpling, used when generating orders */
	public Dumpling(CookMethod[] cookMethods, Dough[] doughs, Sauce[] sauces, Ingredient[] unlockedIngredients)
	{
		//cook type
		cookMethod = cookMethods[rand.nextInt(cookMethods.length)];
				
		//dough type
		dough = doughs[rand.nextInt(doughs.length)];
		
		//sauce type
		sauce = sauces[rand.nextInt(sauces.length)];
		
		//number of ingredients
		numberOfIngredients = rand.nextInt(unlockedIngredients.length) + 1;
		
		//ingredients
		ingredients = generateIngredients(unlockedIngredients);
		
		//cost
		price = calculatePrice();
	}
	
	//accessor methods
	/**Pre: None
	 * Post: Returns a String containing information about a Dumpling
	 * Description: An overridden <.toString> method that returns a String with information about a Dumpling */
	public String toString() {
		String info = "";
		
		info += " Cook Method: " + cookMethod.getName(); //cook method
		info += "\n Dough: " + dough.getName(); //dough 
		info += "\n Ingredients: "; //ingredients
		info += ingredients[0].getName();
		for (int i = 1; i < numberOfIngredients; i++) {
			info += ", " + ingredients[i].getName();
		}
		info += "\n Sauce: " + sauce.getName(); //sauce
		info += "\n Price: " + dollar.format(price);
		return(info);
	}
	
	//general getters
	public static double getBasePrice() {
		return(basePrice);
	}

	public double getPrice() {
		return(price);
	}
	
	public CookMethod getCookMethod() {
		return(cookMethod);
	}

	public Ingredient getDough() {
		return(dough);
	}
	
	public Sauce getSauce() {
		return(sauce);
	}
	
	public int getNumberOfIngredients() {
		return(numberOfIngredients);
	}

	public Ingredient[] getIngredients() {
		return(ingredients);
	}

	//modifier methods
	//general setters
	public static void setBaseCost(double basePrice1) {
		basePrice = basePrice1;
	}

	public void setPrice(double price1) {
		price = price1;
	}
	
	public void setCookType(CookMethod cookMethod1) {
		cookMethod = cookMethod1;
	}

	public void setDough(Dough dough1) {
		dough = dough1;
	}

	public void setSauce(Sauce sauce1) {
		sauce = sauce1;
	}
	
	public void setNumberOfIngredients(int numberOfIngredients1) {
		numberOfIngredients = numberOfIngredients1;
	}

	public void setIngredients(Ingredient[] ingredients1) {
		ingredients = ingredients1;
	}
	
	//comparing methods
	/**Pre: Takes in an Object
	 * Post: Returns true if two Dumpling objects have the same Ingredients, Dough, Sauce, and CookMethod
	 * Description: An overridden <.equals> method that determines if two Dumplings are equal */
	public boolean equals(Object a) {
		Dumpling d = (Dumpling)a; //user's dumpling
		
		//check ingredients
		if (numberOfIngredients != d.getNumberOfIngredients()) {
			return(false);
		} else {
			for (int i = 0; i < numberOfIngredients; i++) {
				if (ingredients[i].equals(d.getIngredients()[i]) == false) {
					return(false);
				}
			}
		}
		//check cook type, dough, and sauce
		if (cookMethod.equals(d.getCookMethod()) && dough.equals(d.getDough()) && sauce.equals(d.getSauce()))
		{
			return(true);
		} 
		return(false);
	}

	
	//helper methods
	/**Pre: None
	 * Post: Returns the price of a Dumpling
	 * Description: Calculates the price of a Dumpling */
	private double calculatePrice() {
		price = basePrice;
		price += cookMethod.getPrice();
		price += dough.getCost();
		price += sauce.getCost();
		
		for (int i = 0; i < numberOfIngredients; i++) { //add cost of each ingredient
			price += ingredients[i].getCost();
		}
		return(price);
	}
	
	/**Pre: Takes in an array of Ingredients
	 * Post: Returns an array of randomly chosen Ingredients from the given array of 
	 *       unlocked Ingredients
	 * Description: Randomly generates an array of Ingredients from a given array of
	 *   			unlocked Ingredients */
	private Ingredient[] generateIngredients(Ingredient[] unlockedIngredients) {
		Ingredient[] randIngredients;
		int length = unlockedIngredients.length;
		
		//create boolean array to ensure there are no duplicates
		boolean[] used = new boolean[length];
		
		//generate random ingredients
		randIngredients = new Ingredient[numberOfIngredients];
		
		for (int i = 0; i < numberOfIngredients; i++) {
			int temp = rand.nextInt(length);	
			while (used[temp] == true) {
				temp = rand.nextInt(length);
			}
			randIngredients[i] = unlockedIngredients[temp];
			used[temp] = true;
		}
		
		return(randIngredients);
	}
	
}
