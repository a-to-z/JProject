import java.sql.*;
import java.util.*;
public class Main
{
	public static void main(String[] args)
	{
		GUI JProject = new GUI();
		try
		{
			DBCon so = new DBCon();
			so.getAllRecords("Class1");

			String[] recs = {"1", 
							"Umair", 
							"Chachar",
							"Clifton",
							"100",
							"0321-0333"};
			so.setRecord("Class1", recs);

			so.getAllRecords("Class1");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
