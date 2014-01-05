package ats.rpg.db.hsql;

import java.sql.*;
import ats.rpg.db.dao.ChampionDao;
import ats.rpg.db.dao.PlaceDao;
import ats.rpg.entities.Place;


public class HsqlPlaceDao extends HsqlDaoBase<Place> implements PlaceDao {
	
	
	public HsqlPlaceDao(HsqlUnitOfWork uow) {
		super(uow);
	}

	@Override
	public void setChampions(Place p, ChampionDao dao) {
		p.setChampions(dao.getChampionsByPlaceId(p.getId()));
	}

	@Override
	public void drop() {
		try {
			drop.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getTableName() {
		return "Place";
	}

	@Override
	protected String getCreateQuery() {
		return "CREATE TABLE Place("
				+ "id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 0),"
				+ "name VARCHAR(50) NOT NULL UNIQUE,"
				+ "HPmod REAL,"
				+ "MPmod REAL,"
				+ "reqLVL INT"
				+ ")";
	}

	@Override
	protected Place build(ResultSet rs) throws SQLException {
		Place p = new Place();
			p.setId(rs.getLong("id"));
			p.setName(rs.getString("name"));
			p.setHPModifier(rs.getFloat("HPmod"));
			p.setMPModifier(rs.getFloat("MPmod"));
			p.setReqLvl(rs.getInt("reqLVL"));
		return p;
	}

	@Override
	protected String getInsertQuery() {
		return "insert into Place(name,HPmod,MPmod,reqLVL) "
				+ "values (?,?,?,?)";
	}

	@Override
	protected void setInsertQuery(Place ent) throws SQLException {
		insert.setString(1, ent.getName());			// Not Null
		insert.setFloat(2, ent.getHPModifier());	
		insert.setFloat(3, ent.getMPModifier());
		insert.setInt(4, ent.getReqLvl());	
	}

	@Override
	protected String getUpdateQuery() {
		return "update Place set"
				+ "(name,HPmod,MPmod,reqLVL)=(?,?,?,?)"
				+ "where id=?";
	}

	@Override
	protected void setUpdateQuery(Place ent) throws SQLException {
		update.setString(1, ent.getName());
		update.setFloat(2, ent.getHPModifier());
		update.setFloat(3, ent.getMPModifier());
		update.setInt(4, ent.getReqLvl());
		update.setLong(5, ent.getId());
	}
}