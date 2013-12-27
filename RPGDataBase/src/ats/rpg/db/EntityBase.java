package ats.rpg.db;

public class EntityBase {
	
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public boolean equals(EntityBase ent) {
		if(this.id == ent.id)
			return true;
		else
			return false;
	}
}
