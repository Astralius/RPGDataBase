package ats.rpg.db.hsql;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import ats.rpg.db.EntityBase;
import ats.rpg.db.UnitOfWork;
import ats.rpg.db.dao.UnitOfWorkDao;
import ats.rpg.util.EntityOperation;


public class HsqlUnitOfWork implements UnitOfWork {

	private Map<EntityBase, UnitOfWorkDao> items;
	
	Connection connection;
	
	public HsqlUnitOfWork() {
		items = new HashMap<EntityBase, UnitOfWorkDao>();
		connection = getConnection();
	}
	
	public Connection getConnection() {
		try {
			if(connection==null || connection.isClosed())
				connection = DriverManager.getConnection
					("jdbc:hsqldb:hsql://localhost/");
			connection.setAutoCommit(false);
		} 
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return connection;
	}
	
	public void markNew(EntityBase ent, UnitOfWorkDao dao) {
		ent.setOperation(EntityOperation.INSERT);
		items.put(ent, dao);	
	}

	public void markDeleted(EntityBase ent, UnitOfWorkDao dao) {
		ent.setOperation(EntityOperation.DELETE);
		items.put(ent, dao);
	}

	public void markUpdated(EntityBase ent, UnitOfWorkDao dao) {
		ent.setOperation(EntityOperation.UPDATE);
		items.put(ent, dao);
	}

	public void commit() {
		
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
			
			for (EntityBase ent : items.keySet()) {
				switch(ent.getOperation()) {
				case INSERT:
					items.get(ent).persistAdd(ent);
					break;
				case UPDATE:
					items.get(ent).persistUpdate(ent);
					break;
				case DELETE:
					items.get(ent).persistDelete(ent);
					break;
				default:
					break;
				}
			}
			items.clear();
			
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
