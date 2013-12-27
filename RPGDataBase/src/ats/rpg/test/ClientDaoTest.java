package ats.rpg.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.db.HsqlUnitOfWork;
import org.example.db.MockDb;
import org.example.shop.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientDaoTest {

	ClientDao dao;

	Connection connection = null;
	private Statement drop;
	
	@BeforeClass
	public static void initialize()
	{}
	
	@Before
	public void setUp()
	{

		Client c = new Client();
		c.setEmail("a@wp.pl");
		c.setName("Jan");
		c.setSurname("Nowak");
		c.setNumber("1234");
		try {

			HsqlUnitOfWork uow = new HsqlUnitOfWork();
			dao = new HsqlClientDao(uow);
			dao.save(c);
			uow.commit();
			drop = uow.getConnection().createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@After
	public void teardown()
	{
		try{
			if(connection!=null && !connection.isClosed())
			{	
				drop.executeUpdate("Drop table Client");
				connection.close();
				connection = null;
			}}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	}
	
	@Test
	public void testGet() {
		
		Client c1 = dao.get(0);
		Client c2 = dao.get(2);
		Client c3 = dao.get(0);
		
		assertNotNull("Zwrócono null mimo ze obiekt jest w bazie",c1);
		assertNull("zwrócono wartosc mimo, że nie ma takiego obiektu w bazie",c2);
		assertTrue(c1.getEmail().equals("a@wp.pl"));
		
		assertEquals(c1.getName(),"Jan");
		assertEquals(c1.getSurname(), "Nowak");
		assertEquals(c1.getNumber(),"1234");
		
		assertNotSame(c1,c3);
		
	}

}
