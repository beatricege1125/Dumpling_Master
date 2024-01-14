/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Dough class: inherits from the Ingredient class
 */

public class Dough extends Ingredient {
	
	//class variable
	private static String category = "DOUGH";
	
	//constructors
	public Dough() {
		super();
	}
	
	public Dough(String name1, double cost1, int quantity1) {
		super(name1, cost1, quantity1);
	}
	
	//accessor methods
	//general getters
	public static String getCategory() {
		return(category);
	}
	
	//modifier methods
	//general setters
	public static void setCategory(String category1) {
		category = category1;
	}

}
