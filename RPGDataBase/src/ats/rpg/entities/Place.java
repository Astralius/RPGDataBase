package ats.rpg.entities;

import java.util.List;

import ats.rpg.db.EntityBase;


public class Place extends EntityBase {
	
	private String name;
	private float HPModifier = 1.0f,
			      MPModifier = 1.0f;	// default values
	private int reqLvl = 1;
	
	// Place - Champion (1:n)
	private List<Champion> champions;
	
	
	public List<Champion> getChampions() {
		return champions;
	}
	
	public void setChampions(List<Champion> champions) {
		this.champions = champions;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getHPModifier() {
		return HPModifier;
	}

	public void setHPModifier(float hPModifier) {
		HPModifier = hPModifier;
	}

	public float getMPModifier() {
		return MPModifier;
	}

	public void setMPModifier(float mPModifier) {
		MPModifier = mPModifier;
	}

	public int getReqLvl() {
		return reqLvl;
	}

	public void setReqLvl(int reqLvl) {
		this.reqLvl = reqLvl;
	}

}
