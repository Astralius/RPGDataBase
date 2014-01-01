package ats.rpg.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ats.rpg.db.hsql.*;
import ats.rpg.entities.*;
import ats.rpg.util.*;


public class DaoTestFull {

	static HsqlUnitOfWork testUOW;
	static HsqlAccountDao accDao;
	static HsqlPlaceDao plcDao;
	static HsqlItemDao itmDao;
	static HsqlChampionDao chmDao;
	static HsqlInventorySlotDao invDao;

	@BeforeClass
	public static void setupUOW() {
		testUOW = new HsqlUnitOfWork();
	}
	
	@Before
	public void insertData() {			// przy okazji "test" na insert
		
		// Tworzę tabele
		accDao = new HsqlAccountDao(testUOW);
		plcDao = new HsqlPlaceDao(testUOW);
		itmDao = new HsqlItemDao(testUOW);
		chmDao = new HsqlChampionDao(testUOW);
		invDao = new HsqlInventorySlotDao(testUOW);
		
		Account a = new Account();
			a.setLogin("Test");	
			a.addPassword("TestPassword");	
				// 'add' generuje hash'a, 'set' ustawia od razu
			a.addEmail("email@test.com");		
				// 'add' sprawdza przed wstawieniem, 'set' nie
			a.setCreationDate(Date.valueOf("2012-06-16"));
				/* niewprowadzenie daty skutkuje automatycznym 
				   wstawieniem dzisiejszej */
		
		Place p = new Place();
			p.setName("Castle");
			p.setHPModifier(2.0f);
			p.setMPModifier(2.0f);
			p.setReqLvl(10);
		
		Item i1 = new Item();
			i1.setName("GM Protection");
			i1.setType(ItemType.ARMOR);
			i1.setDamage(50);
			i1.setDefense(1000);
			i1.setPrice(9999);
			i1.setMpbonus(1500);
		
		Item i2 = new Item();
			i2.setName("GM Protection");
				// itemy mogą mieć te same nazwy
			i2.setType(ItemType.HELMET);
				// ...pod warunkiem że różnią się typem		->	UNIQUE(name, type)
		
		Champion c = new Champion();
			c.setName("Ragnarok");
			c.setProfession(Profession.GM);
			c.setHP(9999);
			c.setMP(9999);
			c.setLevel(100);
			c.setCoins(3155);
			
			c.setAccount(a);
			c.setPlace(p);
			try {
				c.giveItem(i1);
				c.giveItem(i2);
			}
			catch(InventoryFullException e) {
				e.printStackTrace();
			}
			
		a.addChampion(c);
		p.addChampion(c);
		
		accDao.save(a);
		plcDao.save(p);
		itmDao.save(i1);
		itmDao.save(i2);
		try {
			testUOW.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		chmDao.save(c);
		try {
			testUOW.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(InventorySlot islot : c.getInventorySlots()) {
			if(!islot.isEmpty())
				invDao.save(islot);
		}
		try {
			testUOW.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void resetDbData() {
		// Usuwam tabele
		invDao.drop();
		chmDao.drop();
		accDao.drop();
		plcDao.drop();
		itmDao.drop();
		
	}
		
	@Test
	public void getTest() {
		
		Account AccFromDb = accDao.get(0);
			assertThat("Zły Account.id!", 
					AccFromDb.getId(), is((long)0));
			assertThat("Zły Account.login!", 
					AccFromDb.getLogin(), is("Test"));
			assertNotNull("Brakujący Account.password", 
					AccFromDb.getPassword());
			assertFalse("Zły Account.password!",
					AccFromDb.getPassword().equals("TestPassword"));
			assertThat("Zły Account.creationDate!",
					AccFromDb.getCreationDate().toString(), is("2012-06-16"));
			
		Place plcFromDb = plcDao.get(0);
			assertThat("Zły Place.id!", 
					plcFromDb.getId(), is((long)0));
			assertThat("Zły Place.name!",
					plcFromDb.getName(), is("Castle"));
			assertThat("Zły Place.HPModifier!",
					plcFromDb.getHPModifier(), is(2.0f));
			assertThat("Zły Place.MPModifier!",
					plcFromDb.getMPModifier(), is(2.0f));
			assertThat("Zły Place.reqLvl!",
					plcFromDb.getReqLvl(), is(10));
			
		Champion ChmFromDb = chmDao.get(0);
			assertThat("Zły Champion.id!", 
					ChmFromDb.getId(), is((long)0));
			assertThat("Zły Champion.name!", 
					ChmFromDb.getName(), is("Ragnarok"));
			assertThat("Zły Champion.profession!", 
					ChmFromDb.getProfession(), is("GM"));
			assertThat("Zły Champion.HP!", 
					ChmFromDb.getHP(), is(9999));
			assertThat("Zły Champion.MP!", 
					ChmFromDb.getMP(), is(9999));
			assertThat("Zły Champion.coins!", 
					ChmFromDb.getCoins(), is(3155));
			assertThat("Zły Champion.HP!", 
					ChmFromDb.getLevel(), is(100));
			
		Item ItmFromDb = itmDao.get(0);
			assertThat("Zły Item.id", 
					ItmFromDb.getId(), is((long)0));
			assertThat("Zły Item.name",
					ItmFromDb.getName(), is("GM Protection"));
			assertThat("Zły Item.price",
					ItmFromDb.getPrice(), is(anyOf(equalTo(9999), equalTo(1))));
			assertThat("Zły Item.damage",
					ItmFromDb.getDamage(), is(anyOf(equalTo(50), equalTo(0))));
			assertThat("Zły Item.defense",
					ItmFromDb.getDefense(), is(anyOf(equalTo(1000), equalTo(0))));
			assertThat("Zły Item.mpBonus",
					ItmFromDb.getMpBonus(), is(anyOf(equalTo(1500), equalTo(0))));
			boolean found = false;
			for(ItemType type : ItemType.values()) {
				if(ItmFromDb.getType().equals(type.toString()))
					found = true;
			}
			assertTrue("Zły Item.type!", found);
			
		InventorySlot InvFromDb = invDao.get(0);
		assertThat("Zły Item.mpBonus",
				InvFromDb.getId(), is((long)0));
	}
	
	@Test
	public void getAllTest() {
		
		List<Champion> allChamps = chmDao.getAll();
		assertThat(allChamps.size(), is(1));
		
		List<Item> allItems = itmDao.getAll();
		assertThat(allItems.size(), is(2));
		
		List<Account> allAccounts = accDao.getAll();
		assertThat(allAccounts.size(), is(1));
		
		List<Place> allPlaces = plcDao.getAll();
		assertThat(allPlaces.size(), is(1));
		
		List<InventorySlot> allSlots = invDao.getAll();
		assertThat(allSlots.size(), is(2));
	}
	
	@Test
	public void updateTest() {
		
		// BEFORE:
		Account accToUpdate = accDao.get(0);
		accToUpdate.setLogin("Adrian");
		accToUpdate.setPassword("CośTam");	// używam 'set' (bez hashowania), by znać rezultat
		accToUpdate.setCreationDate(Date.valueOf("2013-12-29"));
		accToUpdate.setEmail("s10556@pjwstk.edu.pl");
		
		Item itmToUpdate = itmDao.get(0);
		itmToUpdate.setName("Berło Cudów");
		itmToUpdate.setType(ItemType.STAFF);
		itmToUpdate.setDamage(12);
		itmToUpdate.setDefense(34);
		itmToUpdate.setMpbonus(56);
		itmToUpdate.setPrice(78);
		
		Place plcToUpdate = plcDao.get(0);
		plcToUpdate.setName("PJWSTK");
		plcToUpdate.setReqLvl(0);
		plcToUpdate.setHPModifier(0.001f);
		plcToUpdate.setMPModifier(5.0f);
		
		Champion chmToUpdate = chmDao.get(0);
		chmToUpdate.setName("Astralius");
		chmToUpdate.setHP(200);
		chmToUpdate.setMP(100);
		chmToUpdate.setProfession(Profession.MAGE);
		chmToUpdate.setCoins(5250);
		chmToUpdate.setLevel(52);
		chmToUpdate.setAccount(accToUpdate);		// wymagane (NOT NULL)
		chmToUpdate.setPlace(plcToUpdate);			// wymagane (NOT NULL)
		
		
		// InventorySlot invToUpdate...	    Tabela slotów to tylko tabela pośrednicząca, 
		//									Nie update'uje się jej bezpośrednio!
		
		accDao.update(accToUpdate);
		chmDao.update(chmToUpdate);
		itmDao.update(itmToUpdate);
		plcDao.update(plcToUpdate);
		testUOW.commit();				// przy okazji testuję transakcje ;)
		
		// AFTER:
		Account accAfterUpdate = accDao.get(0);
			assertThat(accAfterUpdate.getLogin(), is("Adrian"));
			assertThat(accAfterUpdate.getPassword(), is("CośTam"));
			assertThat(accAfterUpdate.getCreationDate().toString(), is("2013-12-29"));
			assertThat(accAfterUpdate.getEmail(), is("s10556@pjwstk.edu.pl"));
		
		Champion chmAfterUpdate = chmDao.get(0);
			assertThat(chmAfterUpdate.getName(), is("Astralius"));
			assertThat(chmAfterUpdate.getHP(), is(200));
			assertThat(chmAfterUpdate.getMP(), is(100));
			assertThat(chmAfterUpdate.getProfession(), is("MAGE"));
			assertThat(chmAfterUpdate.getCoins(), is(5250));
			assertThat(chmAfterUpdate.getLevel(), is(52));
		
		Item itmAfterUpdate = itmDao.get(0);
			assertThat(itmAfterUpdate.getName(), is("Berło Cudów"));
			assertThat(itmAfterUpdate.getType(), is("STAFF"));
			assertThat(itmAfterUpdate.getDamage(), is(12));
			assertThat(itmAfterUpdate.getDefense(), is(34));
			assertThat(itmAfterUpdate.getMpBonus(), is(56));
			assertThat(itmAfterUpdate.getPrice(), is(78));
																	
		Place plcAfterUpdate = plcDao.get(0);
			assertThat(plcAfterUpdate.getName(), is("PJWSTK"));
			assertThat(plcAfterUpdate.getReqLvl(), is(0));
			assertThat(plcAfterUpdate.getHPModifier(), is(0.001f));
			assertThat(plcAfterUpdate.getMPModifier(), is(5.0f));
			
	}
	
	@Test
	public void deleteTest() {
		
		for(InventorySlot i : invDao.getAll()) {
			invDao.delete(i);
		}
		for(Champion c : chmDao.getAll()) {
			chmDao.delete(c);
		}
		for(Account a : accDao.getAll()) {
			accDao.delete(a);
		}
		for(Place p : plcDao.getAll()) {
			plcDao.delete(p);
		}
		testUOW.commit();
		
		assertTrue("Zarządano usunięcia całej zawartości tabeli InventorySlots, a jednak"
				+ "wciąż są tam rekordy", invDao.getAll().isEmpty());
		assertTrue("Zarządano usunięcia całej zawartości tabeli Champion, a jednak"
				+ "wciąż są tam rekordy", chmDao.getAll().isEmpty());
		assertTrue("Zarządano usunięcia całej zawartości tabeli Account, a jednak"
				+ "wciąż są tam rekordy", accDao.getAll().isEmpty());
		assertTrue("Zarządano usunięcia całej zawartości tabeli Place, a jednak"
				+ "wciąż są tam rekordy", plcDao.getAll().isEmpty());
		
		// Sprawdzanie usuwania krok po kroku (dla tabeli dowolnej wielkości):
		// 1. Usuwam rekord o najwyższym id i aktualizuję bazę
		// 2. Sprawdzam czy rekord został faktycznie usunięty
		int lastIndex = itmDao.getAll().size() - 1;
		for(int id=lastIndex; id>=0; id--) {
			itmDao.delete(itmDao.get(id));
			testUOW.commit();
			// po usunięciu ostatniego rekordu id powinno oznaczać liczbę pozostałych:
			assertThat(itmDao.getAll().size(), is(id));
		}											
	}
	
	@Test
	public void RelationTest() {	
		// Zaczynamy... będzie się działo:
		
		// 1. Wyciągam z bazy pełną informację na temat championa gracza:
		Champion player = chmDao.get(0);
		chmDao.setAccount(player, accDao);
		chmDao.setPlace(player, plcDao);
		chmDao.setInventorySlots(player, invDao);
		
		// 2. Biorę z tego championa obiekty reprezentujące jego konto, miejsce,
		//    w którym się znajduje oraz zajęte sloty w ekwipunku:
		Account hisAccount = player.getAccount();
		Place whereIsHe = player.getPlace();
		InventorySlot[] hisInventory = player.getInventorySlots();
		
		// 3. Uzupełniam konto o listę championów jakie ma gracz:
		accDao.setChampions(hisAccount, chmDao);
		
		// 4. Uzupełniam miejsce o listę championów którzy się w nim znajdują:
		plcDao.setChampions(whereIsHe, chmDao);
		
		// 5. Uzupełniam ekwipunek championa o posiadane przez niego itemy:
		for(InventorySlot slot : hisInventory) {
			try {
				invDao.setItem(slot, itmDao);
				invDao.setChampion(slot, chmDao);
			}
			catch (NullPointerException e){
				continue;
			}
		}
		
		// 6. Tworzę i wypełniam ogólną listę itemów (mógłbym to od razu zrobić wyżej)
		//    oraz uzupełniam każdy item o informację, w ilu slotach się znajduje 
		//    (tzn. ile egzemplarzy danego itema mają razem wszyscy gracze):
		List<Item> hisItems = new ArrayList<Item>();
		for(InventorySlot slot : hisInventory) {
			if(!(slot == null)) {
				Item item = slot.getItem();
				itmDao.setInventorySlots(item);
				hisItems.add(item);
			}
		}
		
		// Pobrałem w ten sposób z bazy pełną strukturę obiektów dla danej postaci gracza.
		// A wszystko na podstawie jednego obiektu klasy Champion - i to jest piękne ;)
		// Sprawdźmy to:
		assertTrue(accDao.get(0).equals(hisAccount));
		assertTrue(chmDao.get(0).equals(player));
		assertTrue(plcDao.get(0).equals(whereIsHe));
		assertTrue(invDao.get(0).equals(hisInventory[0]) || 
				invDao.get(0).equals(hisInventory[1]));
		assertTrue(invDao.get(1).equals(hisInventory[0]) || 
				invDao.get(1).equals(hisInventory[1]));
		for(Item item : itmDao.getAll()){
			assertThat(item.getName(), is((itmDao.get(0).getName())));
			assertTrue(item.equals(itmDao.get(0)) || item.equals(itmDao.get(1)));
		}
	}
	
}
