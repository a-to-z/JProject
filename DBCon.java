import java.sql.*;
import java.util.*;

public class DBCon
{
    //Class for DB Connection on MySQL
    private String dbClassName = "com.mysql.jdbc.Driver";
    public String CONNECTION = "jdbc:mysql://127.0.0.1/Project";
    Connection c;
    private Properties p;
    private Statement st;

    public Statement getSt()
	{
        return st;
    }

    public ResultSet getRs() 
	{
        return rs;
    }

    public ResultSetMetaData getRsmd() 
	{
        return rsmd;
    }

    public DatabaseMetaData getMd() 
	{
        return md;
    }

    public ResultSet rs;
    private ResultSetMetaData rsmd;
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
    public ResultSet getAllRecords(String table) throws SQLException
    {
		//Method gets all the records and returns result set.
		String query = "SELECT * FROM " + table + ";";
		rs = st.executeQuery(query);
		System.out.println("All Records Displayed Successfully!");
		System.out.println("-------------------------------------------");
		return rs;
    }
    public void closeConnection() throws SQLException
    {
        c.close();
    }
    public void setRecord(String table, String[] items) throws SQLException
    {
        String query = "";
        if (table == "Class1" ||
                table == "Class2" ||
                table == "Class3" ||
                table == "Class4" ||
                table == "Class5")
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
    public boolean delRecord(String table, int id) throws SQLException
    {
        //Deletes record from DB
        String query = "";
        if(checkRecord(table, id))
        {
            query = String.format("DELETE FROM %s WHERE ID=%d", table, id);
            st.executeUpdate(query);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkRecord(String table, int id) throws SQLException
    {
        String query = String.format("SELECT * FROM %s WHERE ID=%d", table, id);
        boolean available = st.execute(query);
        if(available)
        {
            System.out.printf("ID: %d exists in table: %s!\n", id, table);
        }
        else
        {
            System.out.printf("There is no such record as ID: %d in table %s",
                    id, table);
        }
        return available;
    }
}
