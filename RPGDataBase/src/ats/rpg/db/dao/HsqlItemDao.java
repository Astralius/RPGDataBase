package ats.rpg.db.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ats.rpg.db.DaoBase;
import ats.rpg.db.EntityBase;
import ats.rpg.db.HsqlUnitOfWork;
import ats.rpg.entities.Item;
import ats.rpg.util.ItemType;


public class HsqlItemDao extends DaoBase<Item> implements ItemDao {

	private Statement stmt;

	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement select;
	private PreparedStatement selectId;
	
	InventorySlotDao inventoryDao;
	
	
	public HsqlItemDao(HsqlUnitOfWork uow)
	{
		super(uow);
		try {
			Connection connection = uow.getConnection();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean exist = false;
			
			stmt = connection.createStatement();
			
			while(rs.next())
			{
				if(rs.getString("TABLE_NAME").equalsIgnoreCase("Item"))
				{
					exist = true;
					break;
				}
			}
			if(!exist)
			{
				stmt.executeUpdate("CREATE TABLE Item("
						+ "id bigint GENERATED BY DEFAULT AS IDENTITY,"
						+ "name VARCHAR(50),"
						+ "type VARCHAR(50),"
						+ "price INT,"
						+ "damage INT"
						+ "defense INT"
						+ "MPbonus INT"
						+ ")");
			}
			
			insert = connection.prepareStatement(""
					+ "insert into Item(name,type,price,damage,defense,MPbonus) "
					+ "values (?,?,?,?,?,?)");
			
			update = connection.prepareStatement(""
					+ "update Item set"
					+ "(name,type,price,damage,defense,MPbonus)=(?,?,?,?,?,?)"
					+ "where id=?");
			
			delete = connection.prepareStatement(""
					+ "delete from Item where id=?");
			
			selectId = connection.prepareStatement(""
					+ "select * from Item where id=?");
			
			select = connection.prepareStatement(""
					+ "select * from Item");
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
	}
	
	public void persistAdd(EntityBase entity) {
	
		Item ent = (Item) entity;
		try 
		{
			insert.setString(1, ent.getName());
			insert.setString(2, ent.getType());
			insert.setInt(3, ent.getPrice());
			insert.setInt(4, ent.getDamage());
			insert.setInt(5, ent.getDefense());
			insert.setInt(6, ent.getMpbonus());
			insert.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void persistDelete(EntityBase entity) {
		
		Item ent = (Item) entity;
		try 
		{
			delete.setLong(1, ent.getId());
			
			delete.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public List<Item> getAll() {
		
		List<Item> items = new ArrayList<Item>();
		
		try
		{
			ResultSet rs = select.executeQuery();
			
			while(rs.next()){
				Item i = new Item();
				i.setId(rs.getLong("id"));
				i.setName(rs.getString("name"));
				i.setType(ItemType.valueOf(rs.getString("type")));
				i.setPrice(rs.getInt("price"));
				i.setDamage(rs.getInt("damage"));
				i.setDefense(rs.getInt("defense"));
				i.setMpbonus(rs.getInt("MPbonus"));
				items.add(i);
			}
			return items;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public Item get(int id) {
		
		try {
			selectId.setInt(1, id);
			ResultSet rs = selectId.executeQuery();
			while(rs.next()) {
				Item i = new Item();
				i.setId(rs.getLong("id"));
				i.setName(rs.getString("name"));
				i.setType(ItemType.valueOf(rs.getString("type")));
				i.setPrice(rs.getInt("price"));
				i.setDamage(rs.getInt("damage"));
				i.setDefense(rs.getInt("defense"));
				i.setMpbonus(rs.getInt("MPbonus"));
				return i;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void persistUpdate(EntityBase entity) {

		Item ent = (Item) entity;
		try
		{
			update.setString(1, ent.getName());
			update.setString(2, ent.getType());
			update.setInt(3, ent.getPrice());
			update.setInt(4, ent.getDamage());
			update.setInt(5, ent.getDefense());
			update.setInt(6, ent.getMpbonus());
			update.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
	}

	@Override
	public void setInventorySlots(Item item) {
		item.setInventorySlots(inventoryDao.getInventorySlotsByItemId(item.getId()));
	}
	
}