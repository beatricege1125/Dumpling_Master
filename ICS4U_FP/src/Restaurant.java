/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Restaurant class: stores information about the restaurant. The 
 * 				information is used in file reading and writing, and throughout the game.
 */

public class Restaurant {
	
	//instance variables
	private String restaurantName;
	private int currentDay;
	private double currentBalance;
	
	private double rent;
	private int numberOfOrders;
	private boolean isDay;
	
	private CookMethod[] cookMethods;
	private Dough[] doughs;
	private Sauce[] sauces;
	private Ingredient[] ingredients;
	
	private Ingredient[] lockedIngredients;
	
	//constructors
	public Restaurant() 
	{
		//general information
		restaurantName = "[NAME]";
		currentDay = 1;
		currentBalance = 50;
		
		rent = 10;
		numberOfOrders = 3;
		isDay = true;
		
		//cook methods
		cookMethods = new CookMethod[3];
		
		//doughs
		doughs = new Dough[1];
		
		//sauces
		sauces = new Sauce[2];
				
		//ingredients
		ingredients = new Ingredient[5];
		
		//locked ingredients
		lockedIngredients = new Ingredient[15];	
	}
	
	//accessor methods
	public String toString() {
		String info = "";
		
		info += "Restaurant Name: " + restaurantName;
		info += "\nCurrent Day: " + currentDay;
		info += "\nCurrent Balance: $" + currentBalance;
		
		info += "\n\nRent: $" + rent;
		info += "\nNumber of Orders Each Day: " + numberOfOrders;
		info += "\nIs Day: " + isDay;
		
		info += "\n\nNumber of Cook Methods: " + cookMethods.length;
		info += "\nNumber of Doughs: " + doughs.length;
		info += "\nNumber of Sauces: " + sauces.length;
		info += "\nNumber of Ingredients: " + ingredients.length;
		info += "\nNumber of Locked Ingredients: " + lockedIngredients.length;
		
		info += "\n\n\nCook Methods: ";
		for (int i = 0; i < cookMethods.length; i++) {
			info += "\n\nName: " + cookMethods[i].getName();
			info += "\nCost: $" + cookMethods[i].getPrice();
		}
		
		info += "\n\n\nDoughs: ";
		for (int i = 0; i < doughs.length; i++) {
			info += "\n\nName: " + doughs[i].getName();
			info += "\nCost: $" + doughs[i].getCost();
			info += "\nQuantity: " + doughs[i].getQuantity();
		}
		
		info += "\n\n\nSauces: ";
		for (int i = 0; i < sauces.length; i++) {
			info += "\n\nName: " + sauces[i].getName();
			info += "\nCost: $" + sauces[i].getCost();
			info += "\nQuantity: " + sauces[i].getQuantity();
		}
		
		info += "\n\n\nIngredients: ";
		for (int i = 0; i < ingredients.length; i++) {
			if (ingredients[i] instanceof Vegetable) {
				info += "\n\nVegetable";
			} else if (ingredients[i] instanceof Meat) {
				info += "\n\nMeat";
			} else {
				info += "\n\nSeafood";
			}
			info += "\nName: " + ingredients[i].getName();
			info += "\nCost: $" + ingredients[i].getCost();
			info += "\nQuantity: " + ingredients[i].getQuantity();
		}
		
		info += "\n\n\nLocked Ingredients: ";
		for (int i = 0; i < lockedIngredients.length; i++) {
			if (lockedIngredients[i] instanceof Vegetable) {
				info += "\n\nVegetable";
			} else if (lockedIngredients[i] instanceof Meat) {
				info += "\n\nMeat";
			} else if (lockedIngredients[i] instanceof Seafood) {
				info += "\n\nSeafood";
			} else if (lockedIngredients[i] instanceof Dough) {
				info += "\n\nDough";
			} else {
				info += "\n\nSauce";
			}
			info += "\nName: " + lockedIngredients[i].getName();
			info += "\nCost: $" + lockedIngredients[i].getCost();
			info += "\nQuantity: " + lockedIngredients[i].getQuantity();
		}
		
		return(info);
	}
	
	//general getters
	public String getRestaurantName() {
		return restaurantName;
	}
	public int getCurrentDay() {
		return currentDay;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public double getRent() {
		return rent;
	}
	public int getNumberOfOrders() {
		return numberOfOrders;
	}
	public boolean getIsDay() {
		return isDay;
	}
	public Ingredient[] getIngredients() {
		return ingredients;
	}
	public Dough[] getDoughs() {
		return doughs;
	}
	public CookMethod[] getCookMethods() {
		return cookMethods;
	}
	public Sauce[] getSauces() {
		return sauces;
	}
	public Ingredient[] getLockedIngredients() {
		return lockedIngredients;
	}

	//modifier methods
	/**Pre: Takes in 5 Integer lengths
	 * Post: The lengths of the cookMethods, doughs, sauces, ingredients, and lockedIngredients arrays
	 *       are set to the given lengths
	 * Description: Sets the lengths of the cookMethods, doughs, sauces, ingredients, and lockedIngredients arrays
	 * 				to the given lengths. Used when reading files.*/
	public void setLengths(int n1, int n2, int n3, int n4, int n5) {
		//cook methods
		cookMethods = new CookMethod[n1];
		for (int i = 0; i < n1; i++) {
			cookMethods[i] = new CookMethod();
		}
		
		//doughs
		doughs = new Dough[n2];
		for (int i = 0; i < n2; i++) {
			doughs[i] = new Dough();
		}
		
		//sauces
		sauces = new Sauce[n3];
		for (int i = 0; i < n3; i++) {
			sauces[i] = new Sauce();
		}
				
		//ingredients
		ingredients = new Ingredient[n4];
		for (int i = 0; i < n4; i++) {
			ingredients[i] = new Ingredient();
		}
		
		//locked ingredients
		lockedIngredients = new Ingredient[n5];	
		for (int i = 0; i < n5; i++) {
			lockedIngredients[i] = new Ingredient();
		}
	}
	
	//general setters
	public void setRestaurantName(String restaurantName1) {
		restaurantName = restaurantName1;
	}
	public void setCurrentDay(int currentDay1) {
		currentDay = currentDay1;
	}
	public void setCurrentBalance(double currentBalance1) {
		currentBalance = currentBalance1;
	}
	public void setRent(double rent1) {
		rent = rent1;
	}
	public void setNumberOfOrders(int numberOfOrders1) {
		numberOfOrders = numberOfOrders1;
	}
	public void setIsDay(boolean isDay1) {
		isDay = isDay1;
	}
	public void setCookMethods(CookMethod[] cookMethods1) {
		cookMethods = cookMethods1;
	}
	public void setDoughs(Dough[] doughs1) {
		doughs = doughs1;
	}
	public void setSauces(Sauce[] sauces1) {
		sauces = sauces1;
	}
	public void setIngredients(Ingredient[] ingredients1) {
		ingredients = ingredients1;
	}
	public void setLockedIngredients(Ingredient[] lockedIngredients1) {
		lockedIngredients = lockedIngredients1;
	}
	
	public void setNumberOfDoughs(int n) {
		doughs = new Dough[n];
	}
	
	
	//THE FOLLOWING METHODS ARE USED WHEN UNLOCKING INGREDIENTS
	//add ingredients
	/**Pre: None
	 * Post: The size of the doughs array is increased by one
	 * Description: Increases the size of the doughs array by one */
	public void addDough() {
		//declaration of variables
		Dough[] temp;
		int length = doughs.length;
		
		//copy the ingredients to a temporary array
		temp = new Dough[length];
		
		for (int i = 0; i < length; i++) { //copy array
			temp[i] = doughs[i];
		}
		
		//allocate more space to the original array
		doughs = new Dough[length + 1];
		
		for (int i = 0; i < length; i++) { //reassign values using the temporary array
			doughs[i] = temp[i];
		}
	}
	
	/**Pre: None
	 * Post: The size of the ingredients array is increased by one
	 * Description: Increases the size of the ingredients array by one */
	public void addIngredient() {
		//declaration of variables
		Ingredient[] temp;
		int length = ingredients.length;
		
		//copy the ingredients to a temporary array
		temp = new Ingredient[length];
		
		for (int i = 0; i < length; i++) { //copy array
			temp[i] = ingredients[i];
		}
		
		//allocate more space to the original array
		ingredients = new Ingredient[length + 1];
		
		for (int i = 0; i < length; i++) { //reassign values using the temporary array
			ingredients[i] = temp[i];
		}
	}
	
	/**Pre: None
	 * Post: The size of the sauces array is increased by one
	 * Description: Increases the size of the sauces array by one */
	public void addSauce() {
		//declaration of variables
		Sauce[] temp;
		int length = sauces.length;
		
		//copy the ingredients to a temporary array
		temp = new Sauce[length];
		
		for (int i = 0; i < length; i++) { //copy array
			temp[i] = sauces[i];
		}
		
		//allocate more space to the original array
		sauces = new Sauce[length + 1];
		
		for (int i = 0; i < length; i++) { //reassign values using the temporary array
			sauces[i] = temp[i];
		}
	}
	
	/**Pre: None
	 * Post: The size of the locked ingredients array is decreased by one
	 * Description: Decreases the size of the locked ingredients array by one. */
	public void removeIngredient(int n) {
		//declaration of variables
		Ingredient[] temp;
		int length, counter = 0;
		
		//copy the ingredients, except for the ingredient to be removed, to a temporary array
		length = lockedIngredients.length;
		temp = new Ingredient[length];
		
		for (int i = 0; i < length; i++) { //copy array
			if (i == n) {
				//do not copy
			} else {
				temp[counter] = lockedIngredients[i];
				counter++;
			}
		}
		//remove space from the locked ingredients array
		lockedIngredients = new Ingredient[length - 1];
		
		for (int i = 0; i < length - 1; i++) { //reassign values using the temporary array
			lockedIngredients[i] = temp[i];
		}
	}

}
