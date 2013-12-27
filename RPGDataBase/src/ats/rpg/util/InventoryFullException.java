package ats.rpg.util;

@SuppressWarnings("serial")
public class InventoryFullException extends Exception {
	public InventoryFullException(String text) {
		super(text);
	}
}
