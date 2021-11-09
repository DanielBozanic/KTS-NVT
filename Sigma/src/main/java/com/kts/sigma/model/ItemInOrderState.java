package com.kts.sigma.model;

public enum ItemInOrderState {
	NEW("New"),
	IN_PROGRESS("In progress"),
	DONE("Done"),
	TO_DELIVER("To deliver");
	
	String itemInOrderState;
	
	private ItemInOrderState(String itemInOrderState) { this.itemInOrderState = itemInOrderState; }
	
	@Override
	public String toString() {
		return this.itemInOrderState;
	}
	
	public static ItemInOrderState fromString(String text) {
        for (ItemInOrderState inos : ItemInOrderState.values()) {
            if (inos.toString().equalsIgnoreCase(text)) {
                return inos;
            }
        }
        return null;
	}

}