import java.sql.*;
import java.util.*;

class DBCon
{
	private String dbClassName = "com.mysql.jdbc.Driver";
	private String CONNECTION = "jdbc:mysql://127.0.0.1/one";
	private Connection c;
	private Properties p;
	private Statement st;
	private ResultSet rs;

	public DBCon() throws ClassNotFoundException, SQLException
	{
		Class.forName(dbClassName);
		p = new Properties();
		p.put("user", "root");
		p.put("password", "root");
		c = DriverManager.getConnection(CONNECTION, p);
		st = c.createStatement();
	}
	public void getAllRecords() throws SQLException
	{
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
		c.close();
	}
	public void setRecord(int id, String name) throws SQLException
	{
		String query = "INSERT INTO trial VALUES (" + id + ", \"" + name + "\");";
		st.executeUpdate(query);
	}
	public void delRecord(int id, String name) throws SQLException
	{
		String query = "DELETE FROM trial WHERE id=" + id + " AND name=\"" + name + "\";";
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

public class DBDemo
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		Scanner in = new Scanner(System.in);
		DBCon what = new DBCon();
	    int ans;

		do
		{
			System.out.println("Please Select An Option:");
			System.out.println("1). View All Records.");
			System.out.println("2). Insert New Record.");
			System.out.println("3). Delete A Record.");
			System.out.println("4). Exit.");
			ans = in.nextInt();

			switch(ans)
			{
				case 1:
					what.getAllRecords();
					break;

				case 2:
					String yn;
					while(true)
					{
						System.out.println("-------------------------------------------");
						System.out.print("Enter ID to insert: ");
						int id = in.nextInt();
						String useless = in.nextLine();
						System.out.print("Enter Name to insert: ");
						String name = in.nextLine();
						what.setRecord(id, name);
						System.out.println("Record Added!");
						System.out.println("Do you wish to enter another record? (Y/N)");
						yn = in.nextLine();
						if(yn.charAt(0) == 'n' || yn.charAt(0) == 'N')
						{
							System.out.println("-------------------------------------------");
							break;
						}
					} 
					break;

				case 3:
					System.out.println("-------------------------------------------");
					System.out.print("Enter ID to insert: ");
					int id = in.nextInt();
					String useless = in.nextLine();
					System.out.print("Enter Name to insert: ");
					String name = in.nextLine();
					what.delRecord(id, name);
					System.out.println("-------------------------------------------");
					break;

				case 4:
					System.out.println("BYE!");
					System.out.println("-------------------------------------------");
					break;

				default:
					System.out.println("Invalid Option, try again!");
					break;
			}
		}while(ans != 4);
	}
}
