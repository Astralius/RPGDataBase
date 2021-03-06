package ats.rpg.db.hsql;

import java.sql.*;
import ats.rpg.db.dao.AccountDao;
import ats.rpg.db.dao.ChampionDao;
import ats.rpg.entities.Account;


public class HsqlAccountDao extends HsqlDaoBase<Account> implements AccountDao {

	public HsqlAccountDao(HsqlUnitOfWork uow) {
		super(uow);
	}

	@Override
	public void setChampions(Account a, ChampionDao dao) {
		a.setChampions(dao.getChampionsByAccountId(a.getId()));
	}

	@Override
	protected String getTableName() {
		return "Account";
	}

	@Override
	protected String getCreateQuery() {
		return "CREATE TABLE Account("
				+ "id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY (START WITH 0),"
				+ "login VARCHAR(50) NOT NULL UNIQUE,"
				+ "password VARCHAR(50) NOT NULL,"
				+ "email VARCHAR(50) NOT NULL,"
				+ "creation_date DATE"
				+ ")";
	}

	@Override
	protected Account build(ResultSet rs) throws SQLException {
		Account a = new Account();
			a.setId(rs.getLong("id"));
			a.setLogin(rs.getString("login"));
			a.setPassword(rs.getString("password"));
			a.setEmail(rs.getString("email"));
			a.setCreationDate(rs.getDate("creation_date"));
		return a;
	}

	@Override
	protected String getInsertQuery() {
		return "insert into Account(login,password,email,creation_date) "
				+ "values (?,?,?,?)";
	}

	@Override
	protected void setInsertQuery(Account ent) throws SQLException {
		insert.setString(1, ent.getLogin());			// Not Null
		insert.setString(2, ent.getPassword());			// Not Null
		insert.setString(3, ent.getEmail());			// Not Null
		insert.setDate(4, ent.getCreationDate());		// auto	
	}

	@Override
	protected String getUpdateQuery() {
		return "update Account set"
				+ "(login,password,email,creation_date)=(?,?,?,?)"
				+ "where id=?";
	}

	@Override
	protected void setUpdateQuery(Account ent) throws SQLException {
		update.setString(1, ent.getLogin());
		update.setString(2, ent.getPassword());
		update.setString(3, ent.getEmail());
		update.setDate(4, ent.getCreationDate());
		update.setLong(5, ent.getId());
	}

}