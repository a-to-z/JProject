import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import com.mysql.jdbc.*;
import com.mysql.jdbc.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.util.concurrent.ExecutionException;

public class GUI extends JFrame implements ActionListener
{
    DBCon con;
    JMenuBar mBar;
    JMenu mFile;
    JMenuItem miNone;
    JMenuItem miExit;
    JComboBox cbSelectTable;
    JButton bInsert, bModify, bDelete;
    JTable jTable;
    JScrollPane jScrollPane;
    ResultSet rs;

    JMenu mHelp;
    JMenuItem miContents;
    JMenuItem miAbout;
    public GUI()
    {
        //GUI for our app, fires the DB Con on start up
        super("Database Connector");
        Container c = getContentPane();
        c.setLayout(null);
        try
        {
            con = new DBCon();
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("DB Connection Established!");

        //JMenubar stuff
        mBar = new JMenuBar();

        mFile = new JMenu("File");
        miNone = new JMenuItem("None");
        miExit = new JMenuItem("Exit");
        mFile.add(miNone);
        mFile.addSeparator();
        mFile.add(miExit);

        mBar.add(mFile);

        mHelp = new JMenu("Help");
        miContents = new JMenuItem("Contents");
        miAbout = new JMenuItem("About");
        mHelp.add(miContents);
        mHelp.add(miAbout);
        mBar.add(mHelp);

        //Adding Action Listeners
        //and adding a WindowListener to
        //check on the window status.
        //we don't want the con to be open constantly
        //if you get what i mean
        miAbout.addActionListener(this);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try
                {
                    if(con !=null) con.closeConnection();
                    System.out.println("DB Connection Closed!");
                    dispose();
                    System.exit(0);
                }
                catch(SQLException sqle)
                {
                    System.out.println(sqle.getMessage());
                }
            }
        });

        int tableNo=0;
        try{
            rs = con.md.getTables(null, null, "%", null);
            tableNo = rs.getMetaData().getColumnCount();
        }catch(SQLException sqle){
            System.out.println(sqle);
        }
        final String table[] = new String[tableNo + 1];
        table[0] = "Select a Table";
        int i=0;
        try{
            rs = con.md.getTables(null, null, "%", null);
            while(rs.next()){

                table[i+1] = rs.getString(3);
                i++;
            }
        }catch(SQLException sqle){
            System.out.println(sqle);
        }

        cbSelectTable = new JComboBox(table);
        cbSelectTable.setBounds(50, 50, 150, 20);
        c.add(cbSelectTable);

        jTable = new JTable();
        jTable.setBounds(50, 100, 500, 300);
        jTable.setShowGrid(true);
        jTable.setVisible(true);
        JTableHeader header = jTable.getTableHeader();
        header.setBounds(50, 80, 500, 20);
        c.add(header);
        c.add(jTable);

        bInsert = new JButton("Insert");
        bInsert.setBounds(600, 130, 100, 50);
        c.add(bInsert);

        bModify = new JButton("Modify");
        bModify.setBounds(600, 230, 100, 50);
        c.add(bModify);

        bDelete = new JButton("Delete");
        bDelete.setBounds(600, 330, 100, 50);
        c.add(bDelete);

        cbSelectTable.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                if(e.getStateChange() == ItemEvent.SELECTED){

                        System.out.println("Testing 1 2 3 ");
                        BackgroundWorker worker = new BackgroundWorker(jTable,con,table[cbSelectTable.getSelectedIndex()]);
                        worker.execute();
                }
            }
        });


		
		/*bInsert.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				}
				
		);*/


        setJMenuBar(mBar);
        setDefaultCloseOperation(3);
        setSize(750, 500);
        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == miAbout)
        {
            JOptionPane.showMessageDialog(null,
                    "Software underconstruction by Umair" +
                            " and Ammar. Might have more team members lol");
        }
    }

    
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        columnNames.ensureCapacity(columnCount);
        // data of the table
       // Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> vector = new Vector<Object>();
        int i = 1;
        while(rs.next()) {
             for (int columnIndex = 1; columnIndex <= 3; columnIndex++) {
                System.out.println(rs.getObject(columnIndex));

            }

          System.out.println(vector.add(rs.getObject(i++)));
        }

        return new DefaultTableModel(vector, columnNames);

    }
}
