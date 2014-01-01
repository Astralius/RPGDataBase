package ats.rpg.db;

public abstract class EntityBase {
	
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
	
	public boolean equals(EntityBase ent) {
		return (ent.id == this.id) && (ent.getClass().equals(this.getClass()));
	}
}
