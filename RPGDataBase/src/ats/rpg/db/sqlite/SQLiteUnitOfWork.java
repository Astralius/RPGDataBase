package ats.rpg.db.sqlite;

import java.sql.*;

import ats.rpg.db.hsql.HsqlUnitOfWork;

/**
 * Dodatkowy UnitOfWork do łączenia z bazą SQLite. Składnia przy tworzeniu tabeli jest
 * trochę inna, więc trzeba by dopisać osobne statementy Create Table.
 * @author adrianstandowicz
 */
public class SQLiteUnitOfWork extends HsqlUnitOfWork {
	
	@Override
	public Connection getConnection() {
	    Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Test database opened successfully");
		return c;
	}

}
