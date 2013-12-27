package ats.rpg.entities;

import java.util.List;

import ats.rpg.db.EntityBase;
import ats.rpg.util.ItemType;


public class Item extends EntityBase {

	private String name,
				   type;
	private int price,
	            damage,
	            defense,
	            mpbonus;
	
	// Item-InventorySlot (1:n)
	private List<InventorySlot> inventorySlots;
	
	
	public List<InventorySlot> getInventorySlots() {
		return inventorySlots;
	}

	public void setInventorySlots(List<InventorySlot> inventorySlots) {
		this.inventorySlots = inventorySlots;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type.toString();
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getMpbonus() {
		return mpbonus;
	}

	public void setMpbonus(int mpbonus) {
		this.mpbonus = mpbonus;
	}

}
