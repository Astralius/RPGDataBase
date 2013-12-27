package ats.rpg.db.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ats.rpg.db.DaoBase;
import ats.rpg.db.EntityBase;
import ats.rpg.db.HsqlUnitOfWork;
import ats.rpg.entities.InventorySlot;


public class HsqlInventorySlotDao extends DaoBase<InventorySlot> 
	implements InventorySlotDao {

	private Statement stmt;

	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement select;
	private PreparedStatement selectId;
	
	
	public HsqlInventorySlotDao(HsqlUnitOfWork uow)
	{
		super(uow);
		try {
			Connection connection = uow.getConnection();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean exist = false;
			
			stmt = connection.createStatement();
			
			while(rs.next())
			{
				if(rs.getString("TABLE_NAME").equalsIgnoreCase("InventorySlots"))
				{
					exist = true;
					break;
				}
			}
			if(!exist)
			{
				stmt.executeUpdate("CREATE TABLE InventorySlots("
						+ "id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,"
						+ "amount INT,"
						+ "champion_id BIGINT FOREIGN KEY "
						+ "REFERENCES Champion(id) ON UPDATE CASCADE ON DELETE CASCADE,"
						+ "item_id BIGINT FOREIGN KEY "
						+ "REFERENCES Item(id) ON UPDATE CASCADE ON DELETE CASCADE"
						+ ")");
			}
			
			insert = connection.prepareStatement(""
					+ "insert into InventorySlots(item_id,amount,champion_id) "
					+ "values (?,?,?)");
			
			update = connection.prepareStatement(""
					+ "update InventorySlots set"
					+ "(item_id,amount,champion_id)=(?,?,?)"
					+ "where id=?");
			
			delete = connection.prepareStatement(""
					+ "delete from InventorySlots where id=?");
			
			selectId = connection.prepareStatement(""
					+ "select * from InventorySlots where id=?");
			
			select = connection.prepareStatement(""
					+ "select * from InventorySlots");
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
	}
	
	public void persistAdd(EntityBase entity) {
	
		InventorySlot ent = (InventorySlot) entity;
		try 
		{
			insert.setLong(1, ent.getItem().getId());
			insert.setInt(2, ent.getAmount());
			insert.setLong(3, ent.getChampion().getId());	
			insert.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void persistDelete(EntityBase entity) {
		
		InventorySlot ent = (InventorySlot) entity;
		try 
		{
			delete.setLong(1, ent.getId());
			
			delete.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public List<InventorySlot> getAll() {
		
		List<InventorySlot> inventorySlots = new ArrayList<InventorySlot>();
		
		try
		{
			ResultSet rs = select.executeQuery();
			
			while(rs.next()){
				InventorySlot i = new InventorySlot();
				i.setId(rs.getLong("id"));
				i.setAmount(rs.getInt("amount"));
				//getChampion
				//getItem
				inventorySlots.add(i);
			}
			return inventorySlots;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public InventorySlot get(int id) {
		
		try {
			selectId.setInt(1, id);
			ResultSet rs = selectId.executeQuery();
			while(rs.next()) {
				InventorySlot i = new InventorySlot();
				i.setId(rs.getLong("id"));
				i.setAmount(rs.getInt("amount"));
				return i;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

//	public void setChampions(inventorySlots a) {
//		a.setChampions(championDao.getChampionsByAccountId(a.getId()));
//	}

	public void persistUpdate(EntityBase entity) {

		InventorySlot ent = (InventorySlot) entity;
		try
		{
			update.setLong(1, ent.getItem().getId());
			update.setInt(2, ent.getAmount());
			update.setLong(3, ent.getChampion().getId());
			update.setLong(4, ent.getId());
			update.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
	}

	@Override
	public InventorySlot[] getInventorySlotsByChampionId(long id) {

		InventorySlot[] inventorySlots = new InventorySlot[6];
		
		try {
			ResultSet rs = select.executeQuery();
			int index = 0;
			while(rs.next()) {
				if(rs.getLong("champion_id") == id) {
					InventorySlot i = new InventorySlot();
					i.setId(rs.getLong("id"));
					// Item
					i.setAmount(rs.getInt("amount"));
					// Champion
					inventorySlots[index++] = i;
				}
			}
			return inventorySlots;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<InventorySlot> getInventorySlotsByItemId(long id) {

		List<InventorySlot> inventorySlots = new ArrayList<InventorySlot>();
		
		try {
			ResultSet rs = select.executeQuery();
			while(rs.next()) {
				if(rs.getLong("item_id") == id) {
					InventorySlot i = new InventorySlot();
					i.setId(rs.getLong("id"));
					// Item
					i.setAmount(rs.getInt("amount"));
					// Champion
					inventorySlots.add(i);
				}		
			}
			return inventorySlots;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}