package ats.rpg.db.dao;

import java.util.List;

import ats.rpg.db.Dao;
import ats.rpg.entities.*;

public interface ChampionDao extends Dao<Champion> {
	
	public void setInventorySlots(Champion c);
	public List<Champion> getChampionsByAccountId(long id);
	public List<Champion> getChampionsByPlaceId(long id);
}
