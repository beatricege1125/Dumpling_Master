/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Ingredient class: stores name, cost, and quantity of an ingredient
 */

import java.text.DecimalFormat;

public class Ingredient {

	//instance variables
	DecimalFormat dollar = new DecimalFormat("$#,###0.00");
	
	private String name;
	private double cost;
	private int quantity;
	
	//constructors
	public Ingredient() 
	{
		name = "[NAME]";
		cost = 0;
		quantity = 0;
	}
	
	public Ingredient(String name1, double cost1, int quantity1) 
	{
		name = name1;
		cost = cost1;
		quantity = quantity1;
	}
	
	//accessor methods
	/**Pre: Takes in the length of the longest Ingredient name and the length of the longest Ingredient cost (used for formatting purposes)
	 * Post: Returns a String containing information about an Ingredient
	 * Description: An overridden <.toString> method that returns a String with information about an Ingredient */
	public String toString(int longestNameLength, int longestCostLength) {
		String info = "";
		
		int spaces1 = longestNameLength - name.length() + 8; //number of spaces to pad after <name>
		int spaces2 = longestCostLength - dollar.format(cost).length() + 6; //number of spaces to pad after <cost>
		
		//name
		info += name;
		for (int i = 0; i < spaces1; i++) { //pad with spaces1
			info += " ";
		}
		
		//cost
		info += dollar.format(cost);
		for (int i = 0; i < spaces2; i++) { //pad with spaces2
			info += " ";
		}
		
		//quantity
		info += "You have " + quantity + " servings";
		
		return(info);
	}
	
	//general getters
	public String getName() {
		return name;
	}

	public double getCost() {
		return cost;
	}

	public int getQuantity() {
		return quantity;
	}

	//modifier methods
	//general setters
	public void setName(String name1) {
		name = name1;
	}

	public void setCost(double cost1) {
		cost = cost1;
	}

	public void setQuantity(int quantity1) {
		quantity = quantity1;
	}
	
	//comparing methods
	/**Pre: Takes in an Object
	 * Post: Returns true if the two Objects have the same name, cost, and quantity
	 * Description: An overridden <.equals> method that determines if two Objects are equal
	 */
	public boolean equals(Object a) {
		Ingredient ing = (Ingredient)a;
		
		if (name.equals(ing.getName()) && cost == ing.getCost() && quantity == ing.getQuantity()) 
		{
			return(true);
		}
		return(false);
	}

}
