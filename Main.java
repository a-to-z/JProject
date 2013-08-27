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
			String[] recs = { "Okay" };
			so.setRecord(12, recs);
			so.getAllRecords("trial");
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
