package ats.rpg.db.dao;

import ats.rpg.db.Dao;
import ats.rpg.entities.*;

public interface AccountDao extends Dao<Account> {
	
	public void setChampions(Account a);
}
