import java.sql.Date;

import ats.rpg.db.HsqlUnitOfWork;
import ats.rpg.db.dao.*;
import ats.rpg.entities.*;
import ats.rpg.util.InventoryFullException;
import ats.rpg.util.ItemType;
import ats.rpg.util.Profession;


public class Main {

	public static void main(String[] args) {
		HsqlUnitOfWork uow = new HsqlUnitOfWork();
		
		HsqlAccountDao accountDao = new HsqlAccountDao(uow);
		HsqlPlaceDao placeDao = new HsqlPlaceDao(uow);
		HsqlChampionDao championDao = new HsqlChampionDao(uow);
		HsqlItemDao itemDao = new HsqlItemDao(uow);
		HsqlInventorySlotDao inventoryDao = new HsqlInventorySlotDao(uow);
		
		Account a1 = new Account();
		a1.setLogin("Astralius");
		a1.setPassword("TakieSobieHasło");
		a1.setCreationDate(new Date(System.currentTimeMillis()));
		a1.setEmail("adi@o2.pl");
		
		Place noobland = new Place();
		noobland.setName("Noobland");
		noobland.setReqLvl(1);
		noobland.setHPModifier(3.0f);
		noobland.setMPModifier(3.0f);
		
		Champion c1 = new Champion();
		c1.setAccount(a1);
		c1.setPlace(noobland);
		c1.setCoins(1204);
		c1.setHP(100);
		c1.setLevel(1);
		c1.setMP(100);
		c1.setName("TestChampion");
		c1.setProfession(Profession.MAGE);
		
		Item i1 = new Item();
		i1.setName("Short Sword");
		i1.setType(ItemType.SWORD);
		i1.setDefense(0);
		i1.setMpbonus(0);
		i1.setPrice(100);
		i1.setDamage(0);
	
		try {
			c1.giveItem(i1);
		} catch (InventoryFullException e) {
			e.printStackTrace();
		}
		
		accountDao.persistAdd(a1);
		placeDao.persistAdd(noobland);
		itemDao.persistAdd(i1);
		championDao.persistAdd(c1);
		for(InventorySlot i : c1.getInventorySlots()) {
			if(!i.isEmpty())
				inventoryDao.persistAdd(i);
		}

		uow.commit();
		uow.close();
	}

}
