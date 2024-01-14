/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Order class: stores the total cost and a Dumpling object
 */

import java.util.Random;
import java.text.DecimalFormat;

public class Order {
	
	//instance variables
	Random rand = new Random();
	DecimalFormat dollar = new DecimalFormat("$#,###0.00");
	
	private double totalCost;
	private Dumpling dumpling;
	
	//constructors
	public Order() {
		totalCost = 0;
		dumpling = new Dumpling();
	}
	
	public Order(CookMethod[] cookMethods, Dough[] doughs, Sauce[] sauces, Ingredient[] ingredients) {
		//generate dumpling & calculate cost
		totalCost = 0;
		dumpling = new Dumpling(cookMethods, doughs, sauces, ingredients);
		totalCost = dumpling.getPrice() * 1.13;
	}
	
	//accessor methods
	/**Pre: None
	 * Post: Returns a String containing information about an Order
	 * Description: An overridden <.toString> method that returns a String with information about an Order */
	public String toString() {
		String info = "";
		info += dumpling.toString();  
		info += "\n----------------------------------------------------------------------";
		info += "\n TOTAL PRICE: " + dollar.format(totalCost) + " (tax included)";
		
		return(info);
	}
	
	//general getters
	public double getTotalCost() {
		return(totalCost);
	}
	
	public Dumpling getDumpling() {
		return(dumpling);
	}
	
	//modifier methods
	//general setters
	public void setTotalCost(double totalCost1) {
		totalCost = totalCost1;
	}
	
	public void setDumplings(Dumpling dumpling1) {
		dumpling = dumpling1;
	}

}
