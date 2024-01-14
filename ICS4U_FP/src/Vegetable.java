/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Vegetable class: inherits from the Ingredient class
 */

public class Vegetable extends Ingredient {

	//class variable
	private static String category = "VEGETABLE";
	
	//constructors
	public Vegetable() {
		super();
	}
	
	public Vegetable(String name1, double cost1, int quantity1) {
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
