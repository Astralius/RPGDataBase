package ats.rpg.db.dao;

import java.util.List;

import ats.rpg.db.Dao;
import ats.rpg.entities.*;

public interface InventorySlotDao extends Dao<InventorySlot> {
	public InventorySlot[] getInventorySlotsByChampionId(long id);
	public List<InventorySlot> getInventorySlotsByItemId(long id);
}
