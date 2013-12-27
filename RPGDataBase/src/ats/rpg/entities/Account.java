package ats.rpg.entities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

import ats.rpg.db.EntityBase;

public class Account extends EntityBase {
	
	private String login,
				   password,
	               email;
	
	private Date creationDate = new Date(System.currentTimeMillis());
	
	// Account - Champion (1:n)
	private List<Champion> champions;

	
	public List<Champion> getChampions() {
		return champions;
	}
	public void setChampions(List<Champion> champions) {
		this.champions = champions;
	}
	public void addChampion(Champion c) {
		champions.add(c);
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		try {
			this.password = hashIt(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws IllegalArgumentException{
		if(validateEmail(email)) 
			this.email = email;
		else
			throw new IllegalArgumentException("Invalid e-mail!");
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	// Useful utility functions:
	
		/**
		 * @param A string with user specified password
		 * @returns A string with the md5-hashed password
		 * @throws NoSuchAlgorithmException
		 * @throws UnsupportedEncodingException
		 */
		private String hashIt(String password) 
				throws NoSuchAlgorithmException, UnsupportedEncodingException {
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytesOfMessage = password.getBytes("UTF-8");
			String md5Pass = md.digest(bytesOfMessage).toString();
			return md5Pass;
		}
		
		/**
		 * A simple e-mail validator.
		 * @param An email string to be checked.
		 * @returns true or false.
		 */
		public boolean validateEmail(String email){
			
			final String EMAIL_PATTERN = 
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			
			if(email.matches(EMAIL_PATTERN))
				return true;
			else
				return false;
		}
		
}
