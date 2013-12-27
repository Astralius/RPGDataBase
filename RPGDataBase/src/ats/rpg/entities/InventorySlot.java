package ats.rpg.entities;

import ats.rpg.db.EntityBase;


public class InventorySlot extends EntityBase {

	private int amount = 1;
	
	// InventorySlot - Champion (:1)
	private Champion champion;
	// InventorySlot - Item (1:{0,1})
	private Item item;
	
	
	public Champion getChampion() {
		return champion;
	}
	
	public void setChampion(Champion champion) {
		this.champion = champion; 
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
