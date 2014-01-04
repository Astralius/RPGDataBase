package ats.rpg.db.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ats.rpg.db.DaoBase;
import ats.rpg.db.EntityBase;


public abstract class HsqlDaoBase<E extends EntityBase> 
	extends DaoBase<E>{
	
	protected Statement stmt;
	protected PreparedStatement insert;
	protected PreparedStatement delete;
	protected PreparedStatement update;
	protected PreparedStatement select;
	protected PreparedStatement selectId;
	protected PreparedStatement drop;
	
	protected HsqlDaoBase(HsqlUnitOfWork uow) {
		super(uow);
		try {
			Connection connection = uow.getConnection();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean exist = false;
			
			stmt = connection.createStatement();
			
			while(rs.next())
			{
				if(rs.getString("TABLE_NAME").equalsIgnoreCase(getTableName()))
				{
					exist = true;
					break;
				}
			}
			if(!exist)
				stmt.executeUpdate(getCreateQuery());
			
			insert = connection.prepareStatement(getInsertQuery());
			update = connection.prepareStatement(getUpdateQuery());
			
			delete = connection.prepareStatement(""
					+ "delete from " + getTableName() + " where id=?");
			
			selectId = connection.prepareStatement(""
					+ "select * from " + getTableName() + " where id=?");
			
			select = connection.prepareStatement(""
					+ "select * from " + getTableName());
			
			drop = connection.prepareStatement(""
					+ "drop table " + getTableName());
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
	}
	
	public void persistAdd(EntityBase entity) {
		
		@SuppressWarnings("unchecked")
		E ent = (E) entity;
		try 
		{
			setInsertQuery(ent);
			insert.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void persistDelete(EntityBase entity) {
		
		@SuppressWarnings("unchecked")
		E ent = (E) entity;
		try 
		{
			delete.setLong(1, ent.getId());		
			delete.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public E get(long id) {
		
		try {
			selectId.setLong(1, id);
			ResultSet rs = selectId.executeQuery();
			while(rs.next()){
				return build(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<E> getAll() {
		
		List<E> results = new ArrayList<E>();
		
		try
		{
			ResultSet rs = select.executeQuery();
			while(rs.next()){
				E result = get(rs.getLong("id"));
				results.add(result);
			}
			return results;
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void persistUpdate(EntityBase entity) {

		@SuppressWarnings("unchecked")
		E ent = (E) entity;
		try
		{
			setUpdateQuery(ent);
			update.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
	}
	
	public void drop() {
		try {
			drop.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract String getTableName();
	protected abstract String getCreateQuery();
	protected abstract E build(ResultSet rs) throws SQLException;
	
	protected abstract String getInsertQuery();
	protected abstract void setInsertQuery(E ent) throws SQLException;
	protected abstract String getUpdateQuery();
	protected abstract void setUpdateQuery(E ent) throws SQLException;
	
}
