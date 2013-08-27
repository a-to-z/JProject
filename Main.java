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

			String[] recs = {"4", 
							"Michael", 
							"Paa",
							"The Heavens",
							"999",
							"0900-Paa-Come"};
			so.setRecord("Class1", recs);
			so.getAllRecords("Class1");

			//Checks if ID 1 exists in "Class1"
			System.out.println(so.checkRecord("Class1", 1));

			//Deletes that record and returns true or false
			System.out.println(so.delRecord("Class1", 1));

			//Show all records
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
