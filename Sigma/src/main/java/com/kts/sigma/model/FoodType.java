package com.kts.sigma.model;


public enum FoodType {
	APPETISER("Appitiser"),
	SALAD("Salad"),
	MAIN_COURSE("Main course"),
	DESERT("Desert");

	String foodType;
	
	private FoodType(String foodType) { this.foodType = foodType; }
	
	@Override
	public String toString() {
		return this.foodType;
	}
	
	public static FoodType fromString(String text) {
        for (FoodType ft : FoodType.values()) {
            if (ft.toString().equalsIgnoreCase(text)) {
                return ft;
            }
        }
        return null;
	}
}