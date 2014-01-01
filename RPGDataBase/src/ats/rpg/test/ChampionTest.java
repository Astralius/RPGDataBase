package ats.rpg.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import ats.rpg.entities.Champion;
import ats.rpg.entities.Item;


public class ChampionTest {

	static Champion c1, c2, c3;
	static Item i;
	
	@BeforeClass
	public static void initialize() {
	
		c1 = new Champion();	// Zostaną ustawione domyślne wartości: level=1, HP=100, MP=100, coins=0
		c1.setId(2);
		
		c2 = new Champion();	// same class, same id	
		c2.setId(2);
		
		i = new Item();			// different class, same id
		i.setId(2);
		
		c3 = new Champion();	// same class, different id
		c3.setId(123);
	}
	
	@Test
	public void testAutoFields() {
		assertThat(c2.getLevel(), is(1));
		assertThat(c2.getHP(), is(100));
		assertThat(c2.getMP(), is(100));
		assertThat(c2.getCoins(), is(0));
	}
	
	@Test
	public void testEquals() {
		assertTrue("Zwrócono false, mimo tej samej klasy i id!", c1.equals(c2));
		assertFalse("Zwrócono true, mimo że obiekty mają różne id!", c1.equals(c3));
		assertFalse("Zwrócono true, mimo że obiekty są różnych klas!", c1.equals(i));
	}
	
}
