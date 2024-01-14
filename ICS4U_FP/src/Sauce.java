/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Sauce class: inherits from the Ingredient class
 */

public class Sauce extends Ingredient {
	
	//class variable
	private static String category = "SAUCE";
	
	//constructors
	public Sauce() {
		super();
	}
	
	public Sauce(String name1, double cost1, int quantity1) {
		super(name1, cost1, quantity1);
	}
	
	//accessor methods
	public static String getCategory() {
		return(category);
	}
	
	//modifier methods
	public static void setCategory(String category1) {
		category = category1;
	}

}
