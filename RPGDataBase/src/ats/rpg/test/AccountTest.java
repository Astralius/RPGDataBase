package ats.rpg.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ats.rpg.entities.Account;
import ats.rpg.entities.Champion;


public class AccountTest {

	static Account a1, a2, a3;
	static Champion c;
	
	@BeforeClass
	public static void initialize() {
		
		a1 = new Account();
		a1.setId(2);
		a1.setLogin("Test1");
		a1.addPassword("JakiesHaslo");
		a1.addEmail("test@test.com");
		a1.setCreationDate(Date.valueOf("2013-12-20"));
	
		a2 = new Account();		// same class, same id
		a2.setId(2);
		
		c = new Champion();		// different class, same id
		c.setId(2);
		
		a3 = new Account();		// same class, different id
		a3.setId(123);
	}
	
	@Test
	public void testEquals() {
		assertTrue("Zwrócono false, mimo tej samej klasy i id!", a1.equals(a2));
		assertFalse("Zwrócono true, mimo że obiekty mają różne id!", a1.equals(a3));
		assertFalse("Zwrócono true, mimo że obiekty są różnych klas!", a1.equals(c));
	}
	
	@Test
	public void testAutoCreationDate() {

		assertNotNull("zwrócono null mimo, że powinna być dzisiejsza data!", a2.getCreationDate());
		
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		
		boolean isDate = false;
		try {
			format.parse(a2.getCreationDate().toString());
			isDate = true;
		}
		catch(ParseException e){}
		
		assertTrue("zwrócono string, jednak nie jest on prawidłową datą!", isDate);
		assertThat("Zostały nadane różne daty dla 2 kont utworzonych dzisiaj",
				a2.getCreationDate().toString(), is(a3.getCreationDate().toString()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmailValidation() {
		a2.addEmail("Lorem Ipsum");		// To nie jest właściwy e-mail!
	}
	
	@Test
	public void testPasswordHashing() {
		Account a = new Account();
		String password = "TestPassword";
		a.addPassword(password);
		String md5hash = a.getPassword();
		String javahash = String.valueOf(password.hashCode());
		
		assertNotEquals("Hasło powinno być hashowane przy dodawaniu, a nie jest!", 
				password, md5hash);
		assertNotEquals("Mój hash powinien różnić się od hashowania javy, a tak nie jest!",
				md5hash, javahash);
	}
	
}
