package com.kts.sigma.model;

public enum TableState {
	FREE("Free"),
	RESERVED("Reserved"),
	IN_PROGRESS("In progress"),
	TO_DELIVER("To deliver"),
	DONE("Done");
	
	String tableState;
	
	private TableState(String tableState) { this.tableState = tableState; }
	
	@Override
	public String toString() {
		return this.tableState;
	}
	
	public static TableState fromString(String text) {
        for (TableState ts : TableState.values()) {
            if (ts.toString().equalsIgnoreCase(text)) {
                return ts;
            }
        }
        return null;
	}
}