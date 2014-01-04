package ats.rpg.db.dao;

import ats.rpg.db.EntityBase;


public interface UnitOfWorkDao {

	public void persistAdd(EntityBase ent);
	public void persistDelete(EntityBase ent);
	public void persistUpdate(EntityBase ent);
}
