package ats.rpg.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import ats.rpg.db.hsql.HsqlPlaceDao;
import ats.rpg.db.hsql.HsqlUnitOfWork;
import ats.rpg.entities.Champion;
import ats.rpg.entities.Place;


public class PlaceTest {

	static HsqlUnitOfWork testUOW;
	static HsqlPlaceDao dao;
	
	static Place p1, p2, p3; 
	static Champion c;
	
	
	@BeforeClass
	public static void initialize() {
	
		p1 = new Place();		// Zostaną ustawione domyślne wartości: HPMod=1, MPMod=1, ReqLvl=1
		p1.setId(2);
		
		p2 = new Place();		// same class, same id
		p2.setId(2);			
		
		c = new Champion();		// different class, same id
		c.setId(2);
		
		p3 = new Place();		// same class, different id
		p3.setId(123);

	}
	
	@Test
	public void testAutoFields() {
		assertThat(p2.getHPModifier(), is(1.0f));
		assertThat(p2.getMPModifier(), is(1.0f));
		assertThat(p2.getReqLvl(), is(1));
	}
	
	@Test
	public void testEquals() {
		assertTrue("Zwrócono false, mimo tej samej klasy i id!", p1.equals(p2));
		assertFalse("Zwrócono true, mimo że obiekty mają różne id!", p1.equals(p3));
		assertFalse("Zwrócono true, mimo że obiekty są różnych klas!", p1.equals(c));
	}
	
}
