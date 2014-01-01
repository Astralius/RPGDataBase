package ats.rpg.db.dao;

import java.util.List;

import ats.rpg.db.Dao;
import ats.rpg.entities.*;


public interface ChampionDao extends Dao<Champion> {
	
	public List<Champion> getChampionsByAccountId(long id);
	public List<Champion> getChampionsByPlaceId(long id);
	
	public void setInventorySlots(Champion c, InventorySlotDao dao);
	public void setAccount(Champion c, AccountDao dao);
	public void setPlace(Champion c, PlaceDao dao);
}
