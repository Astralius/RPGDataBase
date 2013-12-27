package ats.rpg.db;

import java.util.List;


public abstract class DaoBase<E extends EntityBase> 
	implements Dao<E>, UnitOfWorkDao {

	UnitOfWork uow;
	
	protected DaoBase(UnitOfWork uow) {
		this.uow=uow;
	}
	
	public void save(E ent) {
		uow.markNew(ent, this);
	}
	
	public void update(E ent) {
		uow.markUpdated(ent, this);
	}
	
	public void delete(E ent) {
		uow.markDeleted(ent, this);
	}
	
	public abstract E get(int id);
	public abstract List<E> getAll();
	public abstract void persistAdd(EntityBase ent);
	public abstract void persistDelete(EntityBase ent);
	public abstract void persistUpdate(EntityBase ent);	
}
