package ats.rpg.db.dao;

import ats.rpg.db.Dao;
import ats.rpg.entities.*;


public interface ItemDao extends Dao<Item> {
	
	public void setInventorySlots(Item item);
}
