package com.kts.sigma.model;

public enum OrderState {
	NEW("New"),
	IN_PROGRESS("In progress"),
	DONE("Done"),
	CHARGED("Charged");
	
	String orderState;
	
	private OrderState(String orderState) { this.orderState = orderState; }
	
	@Override
	public String toString() {
		return this.orderState;
	}
	
	public static OrderState fromString(String text) {
        for (OrderState os : OrderState.values()) {
            if (os.toString().equalsIgnoreCase(text)) {
                return os;
            }
        }
        return null;
	}
}