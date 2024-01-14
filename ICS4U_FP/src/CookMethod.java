/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The CookMethod class: stores name and price of a cooking method
 */

public class CookMethod {
	
	//instance variables
	private String name;
	private double price;
	
	//constructors
	public CookMethod() 
	{
		name = "[NAME]";
		price = 0;
	}

	public CookMethod(String name1, double price1) 
	{
		name = name1;
		price = price1;
	}

	//accessor methods
	//general getters
	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	//modifier methods
	//general setters
	public void setName(String name1) {
		name = name1;
	}

	public void setPrice(double price1) {
		price = price1;
	}
	
	//comparing methods
	/**Pre: Takes in an Object
	 * Post: Returns true if two CookMethod objects have the same name and price
	 * Description: An overridden <.equals> method that determines if two CookMethods are equal */
	public boolean equals(Object a) {
		CookMethod c = (CookMethod)a;
		
		if (name.equals(c.getName()) && price == c.getPrice()) {
			return(true);
		}
		return(false);
	}
	
}
