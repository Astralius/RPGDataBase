package ats.rpg.db;

/**
 * Interface:
 * 
 */
public interface UnitOfWorkDao {

	public void persistAdd(EntityBase ent);
	public void persistDelete(EntityBase ent);
	public void persistUpdate(EntityBase ent);
}
