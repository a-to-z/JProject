import java.sql.*;
import java.util.*;

class DBCon
{
	//Class for DB Connection on MySQL
	private String dbClassName = "com.mysql.jdbc.Driver";
	public String CONNECTION = "jdbc:mysql://127.0.0.1/Project";
	Connection c;
	private Properties p;
	private Statement st;
	private ResultSet rs;
	DatabaseMetaData md;

	public DBCon() throws ClassNotFoundException, SQLException
	{
		//This Constructor automatically connects to DB
		Class.forName(dbClassName);
		p = new Properties();
		p.put("user", "root");
		p.put("password", "root");
		c = DriverManager.getConnection(CONNECTION, p);
		st = c.createStatement();
		md = c.getMetaData();
	}
	public ResultSet getAllRecords(String tablename) throws SQLException
	{
		//Method gets all the records but does not return the result set for GUI yet.
		String query = "SELECT * FROM " + tablename + ";";
		rs = st.executeQuery(query);
		int rec = 0;
		while(rs.next())
		{
			System.out.println("-------------------------------------------");
			System.out.println("Record #" + (rec+1) + ":");
			System.out.println("ID:   " + rs.getString("ID") + "\n" +
					"First Name: " + rs.getString("FNAME") + "\n" + 
					"Last Name: " + rs.getString("LNAME") + "\n" +
					"Address: " + rs.getString("ADDRESS") + "\n" +
					"Marks: " + rs.getString("MARKS") + "\n" +
					"Contact Info: " + rs.getString("CONTACT"));
			rec++;
		}
		System.out.println("-------------------------------------------");
		System.out.println("All Records Displayed Successfully!");
		System.out.println("-------------------------------------------");
		return rs;
	}
	public void closeConnection() throws SQLException
	{
		//Fancy way of closing the con lol
		c.close();
	}
	public void setRecord(String table, String[] items) throws SQLException
	{
		String query = "";
		if (table == "Class1" ||
			table == "Class2" ||
			table == "Class 3" ||
			table == "Class 4" ||
			table == "Class 5")
		{
			query = String.format("INSERT INTO %s VALUES" +
									"(%d, '%s', '%s', '%s', %d, '%s');",
									String.valueOf(table),
									Integer.parseInt(items[0]),
									String.valueOf(items[1]),
									String.valueOf(items[2]),
									String.valueOf(items[3]),
									Integer.parseInt(items[4]),
									String.valueOf(items[5]));
		}
		st.executeUpdate(query);
	}
	public void delRecord(int id, String table) throws SQLException
	{
		//Deletes record from DB
		String query = "DELETE FROM " + table + " WHERE id=" + id + ";";
		boolean success = st.execute("SELECT * FROM " + table + 
				" WHERE id=" + id+ ";");
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
