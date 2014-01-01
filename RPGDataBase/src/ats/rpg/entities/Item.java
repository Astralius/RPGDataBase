package ats.rpg.entities;

import java.util.ArrayList;
import java.util.List;

import ats.rpg.db.EntityBase;
import ats.rpg.util.ItemType;


public class Item extends EntityBase {

	private String name,
				   type;
	private Integer price = 1,
	            	damage = 0,		// default values
	            	defense = 0,
	            	mpBonus = 0;
	
	// Item-InventorySlot (1:n)
	private List<InventorySlot> inventorySlots = new ArrayList<InventorySlot>();
	
	
	public List<InventorySlot> getInventorySlots() {
		return inventorySlots;
	}
	public void addSlotReference(InventorySlot i) {
		if(inventorySlots != null)
			inventorySlots.add(i);
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Integer getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Integer getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public Integer getMpBonus() {
		return mpBonus;
	}

	public void setMpbonus(int mpbonus) {
		this.mpBonus = mpbonus;
	}
	
}
