package ats.rpg.db.hsql;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import ats.rpg.db.EntityBase;
import ats.rpg.db.UnitOfWork;
import ats.rpg.db.UnitOfWorkDao;


public class HsqlUnitOfWork implements UnitOfWork {

	private Map<EntityBase, UnitOfWorkDao> added;
	private Map<EntityBase, UnitOfWorkDao> deleted;
	private Map<EntityBase, UnitOfWorkDao> changed;
	
	Connection connection;
	
	public HsqlUnitOfWork() {
		added = new HashMap<EntityBase, UnitOfWorkDao>();
		deleted = new HashMap<EntityBase, UnitOfWorkDao>();
		changed = new HashMap<EntityBase, UnitOfWorkDao>();
		connection = getConnection();
	}
	
	public Connection getConnection() {
		try {
			if(connection==null||connection.isClosed())
				connection = DriverManager.getConnection
					("jdbc:hsqldb:hsql://localhost/");
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
		} 
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return connection;
	}
	
	public void markNew(EntityBase ent, UnitOfWorkDao dao) {
		added.put(ent, dao);	
	}

	public void markDeleted(EntityBase ent, UnitOfWorkDao dao) {
		deleted.put(ent, dao);	
	}

	public void markUpdated(EntityBase ent, UnitOfWorkDao dao) {
		changed.put(ent, dao);	
	}

	public void commit() {	
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
			
			for(EntityBase ent : added.keySet())
				added.get(ent).persistAdd(ent);
			for(EntityBase ent : changed.keySet())
				changed.get(ent).persistUpdate(ent);
			for(EntityBase ent : deleted.keySet())
				deleted.get(ent).persistDelete(ent);
			
			added.clear();
			changed.clear();
			deleted.clear();
			
			conn.commit();
			conn.setAutoCommit(true);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}	
	}

	public void close() {
		try{
			if(connection!=null && !connection.isClosed()) {
				connection.close();
				connection=null;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}	
	}

}
