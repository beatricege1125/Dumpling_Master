/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The GraphicsSet class: stores the graphics (Strings) for the title, options, kitchen,
 * 				orders, shop, upgrades, and back option
 */

public class GraphicsSet {
	
	//class variables
	private static final String title = "________________________________________________________________________\r\n"
			+ " ____                _ _                        |             |\r\n"
			+ "|    \\ _ _ _____ ___| |_|___ ___              __|__          _|_\r\n"
			+ "|  |  | | |     | . | | |   | . |          .-'-----'-.      (===)\r\n"
			+ "|____/|___|_|_|_|  _|_|_|_|_|_  |          |:=======:|       '-'\r\n"
			+ "                |_|         |___|          |:=======:|                      \r\n"
			+ " _____         _                           |:=======:|\r\n"
			+ "|     |___ ___| |_ ___ ___                 |:=======:|\r\n"
			+ "| | | | .'|_ -|  _| -_|  _|                '-._____.-'\r\n"
			+ "|_|_|_|__,|___|_| |___|_|                       U\r\n"
			+ "\r\n"
			+ "BY: BEATRICE GE\r\n"
			+ "\r\n"
			+ " _____________________________     _____________________________\r\n"
			+ "| {1} START A NEW RESTAURANT  |   |    {2} LOAD A RESTAURANT    |\r\n"
			+ "'-----------------------------'   '-----------------------------'\r\n";
	
	                                                                                              
	private static final String options = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'-------------'------------'----------'--------------'----------------'\r\n";
	
	
	private static final String kitchen = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'             '------------'----------'--------------'----------------'\r\n"
			+ "            ____________                                               \r\n"
			+ "           [            ]           .-.                                \r\n"
			+ "           |  {MANUAL}  |          ( L )                               \r\n"
			+ "           |  ~~ ~~ ~~  |           '-'         ______________________             \r\n"
			+ "           |  ~~ ~~ ~~  |                      /                      |\r\n"
			+ "           |  ~~ ~~ ~~  |                     /______________________/| \r\n"
			+ "           [    ~~~~    ]                     |                      ||\r\n"
			+ "           '------------'                     |  /|                  ||\r\n"
			+ " _____________________________________________|  ||     {FRIDGE}     ||\r\n"
			+ "|         .-------------------------.         |  \\|                  ||\r\n"
			+ "|         |        [@]       [@]    |         |______________________||\r\n"
			+ "|         |     [@]       [@]       |         :----------------------:|\r\n"
			+ "|_________|_________________________|_________|                      ||\r\n"
			+ "|| .----. |                         | .----. ||                      ||\r\n"
			+ "|| | == | |         {COOK}          | | == | ||                      ||\r\n"
			+ "|| :----: |  _____________________  | :----: ||  /|                  ||\r\n"
			+ "|| | == | | [                     ] | | == | ||  ||                  ||\r\n"
			+ "|| :----: | |                     | | :----: ||  \\|                  ||\r\n"
			+ "|| | == | | |                     | | | == | ||                      ||\r\n"
			+ "|| '----' | [_____________________] | '----' ||                      ||\r\n"
			+ "||________|_________________________|________||______________________||";

	
	private static final String orderDisplay1 = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'-------------'            '----------'--------------'----------------'\r\n"
			+ " _________________                                                      \r\n"
			+ "|  CURRENT ORDER  |                                                     \r\n"
			+ "'-----------------'                   \r";
	
	private static final String orderDisplay2 = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'-------------'            '----------'--------------'----------------'\r\n"
			+ " ___________________                                                      \r\n"
			+ "|  {BEGIN} NEW DAY  |                                                     \r\n"
			+ "'-------------------'                                                     \r\n"
			+ "_______________________________________________________________________\r\n";

	
	public static final String shop1 = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'-------------'------------'          '--------------'----------------'\r\n"
			+ "\r\n"
			+ "                            .----.\r\n"
			+ "        ____________________|----|___\r\n"
			+ "      /                     '----'    \\\r\n"
			+ "     /   .\"\"\"\"\"\".   .\"\"\"\"\"\"\"\"\"\".       \\ \r\n"
			+ "    / .\"\"\".       .\"\"\".          .\"\"\".  \\                 o              \r\n"
			+ "   '.___________________________________.'             /   \\       \r\n"
			+ "    ||                    __ __ __     ||         ____/_____\\____              \r\n"
			+ "    ||     .-------.     |  |  |  |    ||        |               |\r\n"
			+ "    ||     |.--.--.|     |--|--|--|    ||        |     CLOSED    |\r\n"
			+ "    ||     ||  |  ||     |__|_-\"-_|    ||        |               |\r\n"
			+ "    ||     ||__|__||    .-\"-\" ()  '.   ||        |_______________|\r\n"
			+ "    ||     |  [-]  |   .' ()     () .  ||\r\n"
			+ "    ||     |       |  .'\"\"\"\"\"\"\"\"\"\"\"\".  ||\r\n"
			+ "    ||_____|_______|___\\___________/___||\r\n"
			+ "\r\n"
			+ "     ______________________________________________________________   \r\n"
			+ "    |           YOU CANNOT SHOP WHILE YOU ARE WORKING!             |   \r\n"
			+ "    '--------------------------------------------------------------'\r\n"
			+ "________________________________________________________________________\r\n";
	
	
	private static final String shop2 = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'-------------'------------'          '--------------'----------------'";
	
	
	private static final String upgrades1 = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'-------------'------------'----------'              '----------------'\r\n"
			+ "     ______________________________________________________________   \r\n"
			+ "    |     YOU CANNOT PURCHASE UPGRADES WHILE YOU ARE WORKING!      |   \r\n"
			+ "    '--------------------------------------------------------------'\r\n"
			+ "________________________________________________________________________\r\n";;
	
	
	private static final String upgrades2 = "\n .-----------. .----------. .--------. .------------. .--------------.\r\n"
			+ "|  {KITCHEN}  |  {ORDERS}  |  {SHOP}  |  {UPGRADES}  |     {EXIT}     |\r\n"
			+ "'-------------'------------'----------'              '----------------'";
	
	
	private static final String backOption = "\nTIP: Enter {BACK} to head back to the main menu";

	
	//accessor methods
	//general getters
	public static String getTitle() {
		return title;
	}

	public static String getOptions() {
		return options;
	}

	public static String getKitchen() {
		return kitchen;
	}

	public static String getOrderDisplay1() {
		return orderDisplay1;
	}
	
	public static String getOrderDisplay2() {
		return orderDisplay2;
	}
	
	public static String getShop1() {
		return shop1;
	}
	
	public static String getShop2() {
		return shop2;
	}
	
	public static String getUpgrades1() {
		return upgrades1;
	}
	
	public static String getUpgrades2() {
		return upgrades2;
	}
	
	public static String getBackOption() {
		return backOption;
	}

}