import java.sql.*;
import java.util.*;

class DBCon
{
	//Class for DB Connection on MySQL
	private String dbClassName = "com.mysql.jdbc.Driver";
	private String CONNECTION = "jdbc:mysql://127.0.0.1/one";
	Connection c;
	private Properties p;
	private Statement st;
	private ResultSet rs;

	public DBCon() throws ClassNotFoundException, SQLException
	{
		//This Constructor automatically connects to DB
		Class.forName(dbClassName);
		p = new Properties();
		p.put("user", "root");
		p.put("password", "root");
		c = DriverManager.getConnection(CONNECTION, p);
		st = c.createStatement();
	}
	public void getAllRecords() throws SQLException
	{
		//Method gets all the records but does not return the result set for GUI yet.
		String query = "SELECT * FROM trial;";
		rs = st.executeQuery(query);
		int rec = 0;
		while(rs.next())
		{
			System.out.println("-------------------------------------------");
			System.out.println("Record #" + (rec+1) + ":");
			System.out.println("ID:   " + rs.getString("id") + "\n" +
					"Name: " + rs.getString("name"));
			rec++;
		}
		System.out.println("-------------------------------------------");
		System.out.println("All Records Displayed Successfully!");
		System.out.println("-------------------------------------------");
	}
	public void closeConnection() throws SQLException
	{
		//Fancy way of closing the con lol
		c.close();
	}
	public void setRecord(int id, String name) throws SQLException
	{
		//Sets Records to DB
		String query = "INSERT INTO trial VALUES (" +id+ ", \"" +name+ "\");";
		st.executeUpdate(query);
	}
	public void delRecord(int id, String name) throws SQLException
	{
		//Deletes record from DB
		String query = "DELETE FROM trial WHERE id=" +id+ " AND name=\"" +name+ "\";";
		boolean success = st.execute("SELECT * FROM trial WHERE id=" + 
				id + " AND name =\"" + name + "\";");
		if(success)
		{
			st.execute(query);
			System.out.println("Record Deleted Successfully!");
		}
		else
		{
			System.out.println("No such record.");
		}
	}
}
/*
 * READ THIS!
 * the above is not suitable for the db we have, alot of configuration and designing needed
 * vintage?
 * :P
 */
