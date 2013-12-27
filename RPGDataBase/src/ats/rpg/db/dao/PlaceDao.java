package ats.rpg.db.dao;

import ats.rpg.db.Dao;
import ats.rpg.entities.*;

public interface PlaceDao extends Dao<Place> {
	public void setChampions(Place place);
}
