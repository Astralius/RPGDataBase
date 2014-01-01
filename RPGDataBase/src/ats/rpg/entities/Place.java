package ats.rpg.entities;

import java.util.ArrayList;
import java.util.List;

import ats.rpg.db.EntityBase;


public class Place extends EntityBase {
	
	private String name;
	private Float HPModifier = 1.0f,
			      MPModifier = 1.0f;	// default values
	private Integer reqLvl = 1;
	
	// Place - Champion (1:n)
	private List<Champion> champions = new ArrayList<Champion>();
	
	
	public List<Champion> getChampions() {
		return champions;
	}
	public void setChampions(List<Champion> champions) {
		this.champions = champions;
	}
	public void addChampion(Champion c) {
		champions.add(c);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getHPModifier() {
		return HPModifier;
	}

	public void setHPModifier(float hPModifier) {
		HPModifier = hPModifier;
	}

	public Float getMPModifier() {
		return MPModifier;
	}

	public void setMPModifier(float mPModifier) {
		MPModifier = mPModifier;
	}

	public Integer getReqLvl() {
		return reqLvl;
	}

	public void setReqLvl(int reqLvl) {
		this.reqLvl = reqLvl;
	}
	
}
