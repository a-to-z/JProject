import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener
{
	DBCon con;
	JMenuBar mBar;
	JMenu mFile;
	JMenuItem miNone;
	JMenuItem miExit;
	JComboBox cbSelectTable;
	JButton bView, bInsert, bModify, bDelete;
	JTable jTable;

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
					public void windowClosed(WindowEvent e) 
					{
						try
						{
							con.closeConnection();
							System.out.println("DB Connection Closed!");
						}
						catch(SQLException sqle)
						{
							System.out.println(sqle.getMessage());
						}
					}
				});
		
		String[] table = {"Select a Table", "lulz", "lmao"};
		
		cbSelectTable = new JComboBox(table);
		cbSelectTable.setBounds(50, 50, 150, 20);
		c.add(cbSelectTable);		
		
		jTable = new JTable();
		jTable.setBounds(50, 100, 500, 300);
		c.add(jTable);
		
		bView = new JButton("View");
		bView.setBounds(225, 50, 100, 20);
		c.add(bView);
		
		bInsert = new JButton("Insert");
		bInsert.setBounds(600, 130, 100, 50);
		c.add(bInsert);
		
		bModify = new JButton("Modify");
		bModify.setBounds(600, 230, 100, 50);
		c.add(bModify);
		
		bDelete = new JButton("Delete");
		bDelete.setBounds(600, 330, 100, 50);
		c.add(bDelete);
		
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
}