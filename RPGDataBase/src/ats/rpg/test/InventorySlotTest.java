package ats.rpg.test;

import static org.junit.Assert.*;
import org.junit.*;

import ats.rpg.entities.Champion;
import ats.rpg.entities.InventorySlot;


public class InventorySlotTest {

	static InventorySlot i1, i2, i3;
	static Champion c;
	
	@BeforeClass
	public static void initialize() {
		
		i1 = new InventorySlot();
		i1.setId(2);
	
	
		i2 = new InventorySlot();		// same class, same id
		i2.setId(2);
		
		c = new Champion();				// different class, same id
		c.setId(2);
		
		i3 = new InventorySlot();		// same class, different id
		i3.setId(123);
	}
	
	@Test
	public void testEquals() {
		assertTrue("Zwrócono false, mimo tej samej klasy i id!", i1.equals(i2));
		assertFalse("Zwrócono true, mimo że obiekty mają różne id!", i1.equals(i3));
		assertFalse("Zwrócono true, mimo że obiekty są różnych klas!", i1.equals(c));
	}
	
}
