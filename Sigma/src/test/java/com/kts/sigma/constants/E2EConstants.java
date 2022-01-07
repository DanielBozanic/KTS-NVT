package com.kts.sigma.constants;

public class E2EConstants {
	
	public static final String CHROME_DRIVER_PATH = "src/test/resources/chromedriver.exe";

	public static final String FRONTEND_URL = "http://localhost:4200/";
	
	public static final String PEOPLE_URL = FRONTEND_URL + "people";
	
	public static final String TABLES_URL = FRONTEND_URL + "waiterTables";
	
	public static final String ORDER_URL = FRONTEND_URL + "waiterOrder";
	
	public static final String ADD_ITEMS_URL = FRONTEND_URL + "waiterAddItems";
	
	public static final String FOOD_DRINKS_URL = FRONTEND_URL + "foodDrinks";
	
	
	// Backend Error Messages
	public static final String INVALID_CODE_ERROR = "Access Forbidden, Invalid Code";
	
	public static String getItemInMenuExistsError(String item) {
		return "Item " + item + " is already part of this menu!";
	}
	
	public static String getDateNotValidOrder(String startDate, String expirationDate) {
		return "The given start date " + startDate + " can't be after the given end date " + expirationDate;
	}
}
