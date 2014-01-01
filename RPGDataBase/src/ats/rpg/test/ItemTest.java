package ats.rpg.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import ats.rpg.db.hsql.HsqlItemDao;
import ats.rpg.db.hsql.HsqlUnitOfWork;
import ats.rpg.entities.Champion;
import ats.rpg.entities.Item;


public class ItemTest {

	static HsqlUnitOfWork testUOW;
	static HsqlItemDao dao;
	
	static Item i1, i2, i3;
	static Champion c;
	
	@BeforeClass
	public static void initialize() {
	
		i1 = new Item();		// Zostaną ustawione domyślne wartości: Damage=0, Defense=0, MpBonus=0, Price=1
		i1.setId(2);
		
		i2 = new Item();		// same class, same id
		i2.setId(2);
		
		c = new Champion();		// different class, same id
		c.setId(2);

		i3 = new Item();		// same class, different id
		i3.setId(123);	
		
	}
	

	@Test
	public void testAutoFields() {
		assertThat(i1.getDamage(), is(0));
		assertThat(i1.getDefense(), is(0));
		assertThat(i1.getMpBonus(), is(0));
		assertThat(i1.getPrice(), is(1));
	}
	
	@Test
	public void testEquals() {
		assertTrue("Zwrócono false, mimo tej samej klasy i id!", i1.equals(i2));
		assertFalse("Zwrócono true, mimo że obiekty mają różne id!", i1.equals(i3));
		assertFalse("Zwrócono true, mimo że obiekty są różnych klas!", i1.equals(c));
	}
	
}
