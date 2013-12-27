import java.sql.Date;

import ats.rpg.db.HsqlUnitOfWork;
import ats.rpg.db.dao.*;
import ats.rpg.entities.*;
import ats.rpg.util.ItemType;
import ats.rpg.util.Profession;


public class Main {

	public static void main(String[] args) {
		HsqlUnitOfWork uow = new HsqlUnitOfWork();
		
		HsqlAccountDao accountDao = new HsqlAccountDao(uow);
		HsqlChampionDao championDao = new HsqlChampionDao(uow);
		HsqlPlaceDao placeDao = new HsqlPlaceDao(uow);
		HsqlInventorySlotDao inventoryDao = new HsqlInventorySlotDao(uow);
		HsqlItemDao itemDao = new HsqlItemDao(uow);
		
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
		
		InventorySlot[] inventory = new InventorySlot[6];
		c1.setInventorySlots(inventory);
		inventory[0].setItem(i1);
		inventory[0].setAmount(1);
		inventory[0].setChampion(c1);
	
		accountDao.persistAdd(a1);
		placeDao.persistAdd(noobland);
		itemDao.persistAdd(i1);
		for(int i=0;i<6;i++) {
			inventoryDao.persistAdd(inventory[i]);
		}
		championDao.persistAdd(c1);
	}

}
