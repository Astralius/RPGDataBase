package ats.rpg.entities;

import ats.rpg.db.EntityBase;
import ats.rpg.util.Profession;

public class Champion extends EntityBase {

	private String name,
				   profession;
	
	private int level = 1,
			    HP = 100,
			    MP = 100,		// default values
			    coins = 0;
	
	// Champion - Place (:1)
	private Place place;
	// Champion - Account (:1)
	private Account account;
	// Champion - InventorySlot (1:n=6)
	private InventorySlot[] inventorySlots = new InventorySlot[6];

	
	public Place getPlace() {
		return place;
	}
	
	public void setPlace(Place place) {
		this.place = place;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public InventorySlot[] getInventorySlots() {
		return inventorySlots;
	}
	
	public void setInventorySlots(InventorySlot[] i) {
		if(i.length > 6) {
			throw new IndexOutOfBoundsException("Fatal Error! Illegal number of inventory slots!");
		}
		inventorySlots = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession.toString();
	}

	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int MP) {
		this.MP = MP;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

}
