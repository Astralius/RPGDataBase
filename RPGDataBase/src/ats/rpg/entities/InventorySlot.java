package ats.rpg.entities;

import ats.rpg.db.EntityBase;


public class InventorySlot extends EntityBase {
	
	// InventorySlot - Champion (:1)
	private Champion champion;
	// InventorySlot - Item (1:{0,1})
	private Item item;
	
	
	public boolean isEmpty() {
		if(item == null) {
			return true;
		}
		return false;
	}
	
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

}
