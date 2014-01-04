package ats.rpg.db;

import ats.rpg.util.EntityOperation;


public abstract class EntityBase {
	
	protected EntityOperation operation = EntityOperation.NONE;
	
	protected long id;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isNull() {
		if((Long)id != null)
			return false;
		else 
			return true;
	}
	
	public void setOperation(EntityOperation operation) {
		this.operation = operation;
	}
	
	public EntityOperation getOperation() {
		return operation;
	}
	
	public boolean equals(EntityBase ent) {
		return (ent.id == this.id) && (ent.getClass().equals(this.getClass()));
	}

}
