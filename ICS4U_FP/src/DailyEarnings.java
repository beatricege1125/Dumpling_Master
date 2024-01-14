/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The DailyEarnings class: stores revenue, tips, refunds, supplies used, and profit for a day
 */

import java.text.DecimalFormat;

public class DailyEarnings {
	
	//instance variables
	DecimalFormat dollar = new DecimalFormat("$#,###0.00");
	
	private double revenue;
	private double tips;
	
	private double refunds;
	private double suppliesUsed;
	
	private double profit;
	
	//constructors
	public DailyEarnings() 
	{
		revenue = 0;
		tips = 0;
		refunds = 0;
		suppliesUsed = 0;
		profit = 0;
	}
	
	public DailyEarnings(double n1, double n2, double m1, double m2, double p, Restaurant restaurant)
	{
		revenue = n1;
		tips = n2;
		refunds = m1;
		suppliesUsed = m2;
		profit = (revenue + tips) - (restaurant.getRent() + refunds + suppliesUsed);
	}

	//accessor methods
	/**Pre: Takes in a Restaurant object (to access the rent fee)
	 * Post: Returns a String containing information about the daily earnings
	 * Description: An overridden <.toString> method that returns a String with information about the daily earnings */
	public String toString(Restaurant restaurant) {
		String info = "";
		
		info += "[DAY " + restaurant.getCurrentDay() + "]";
		info += "\n------------------------------------------------------------------------";
		info += "\nRevenue:\t\t+" + dollar.format(revenue);
		info += "\nTips:\t\t\t+" + dollar.format(tips);
		
		info += "\n\nRent:\t\t\t-" + dollar.format(restaurant.getRent());
		info += "\nRefunds:\t\t-" + dollar.format(refunds);
		
		info += "\n\nSupplies Used:\t\t " + dollar.format(suppliesUsed) + " (already purchased)";
		info += "\n------------------------------------------------------------------------";
		if (profit < 0) { //negative profit
			info += "\nPROFIT:\t\t\t" + dollar.format(profit) + " (excluding supplies used)";
		} else { //positive profit
			info += "\nPROFIT:\t\t\t " + dollar.format(profit) + " (excluding supplies used)";
		}
		
		info += "\nCURRENT BALANCE:\t " + dollar.format(restaurant.getCurrentBalance());
		
		return(info);
	}
	
	//general getters
	public double getRevenue() {
		return revenue;
	}

	public double getTips() {
		return tips;
	}

	public double getRefunds() {
		return refunds;
	}

	public double getSuppliesUsed() {
		return suppliesUsed;
	}

	public double getProfit() {
		return profit;
	}
	
	//modifier methods
	//general setters
	public void setRevenue(double revenue1) {
		revenue = revenue1;
	}

	public void setTips(double tips1) {
		tips = tips1;
	}

	public void setRefunds(double refunds1) {
		refunds = refunds1;
	}

	public void setSuppliesUsed(double suppliesUsed1) {
		suppliesUsed = suppliesUsed1;
	}

	public void setProfit(double profit1) {
		profit = profit1;
	}
	
	public void setProfit(Restaurant restaurant) {
		profit = calculateProfit(restaurant);
	}

	//helper methods
	/**Pre: Takes in a Restaurant object (to access the rent fee)
	 * Post: Returns the amount of profit made
	 * Description: Calculates the amount of profit made in a day */
	private double calculateProfit(Restaurant restaurant) {
		return ((revenue + tips) - (restaurant.getRent() + refunds));
	}

}
