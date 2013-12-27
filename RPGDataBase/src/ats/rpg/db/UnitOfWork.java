package ats.rpg.db;

import java.sql.Connection;

/**
 * Interface:
 * 1. Establish connection - getConnection();
 * 2. Transactions - markNew(), markDeleted(), markUpdated() oraz commit(); 
 * 3. Break the connection - close();
 */
public interface UnitOfWork {

	public Connection getConnection();
	public void markNew(EntityBase ent, UnitOfWorkDao dao);
	public void markDeleted(EntityBase ent, UnitOfWorkDao dao);
	public void markUpdated(EntityBase ent, UnitOfWorkDao dao);
	public void commit();
	public void close();
}
