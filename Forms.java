import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Forms {

}


class BackgroundWorker extends SwingWorker<TableModel, TableModel> {
    private final JTable tabler;
    private DBCon  gui;
    private String tablename;
    public BackgroundWorker(JTable table,DBCon gui, String tablename){
        this.tabler = table;
        this.gui = gui; // to call the resultset!
        this.tablename = tablename;
    }

    @Override
    protected TableModel doInBackground() throws Exception {
       Vector data =  new Vector();
       Vector colum = new Vector();
       try {
           ResultSet rs; // = gui.getRs();
           ResultSetMetaData rsmd = gui.getRsmd();
           //Method gets all the records and returns result set.
           //String query = "SELECT * FROM " + tablename + ";";
           rs = gui.getAllRecords(tablename);
           rsmd = rs.getMetaData();
           int columCount = rsmd.getColumnCount();
           for (int i = 1; i < columCount; i++){
               colum.add(rsmd.getColumnName(i));
           }

           Vector row;
           while (rs.next()){
             row = new Vector(columCount);
             for (int y = 1; y < columCount; y++){
                row.add(rs.getString(y));
             }
             data.add(row);
           }


       }catch(SQLException e){
            e.printStackTrace();
       }

        DefaultTableModel model = new DefaultTableModel(data,colum);
        return model;
    }
   @Override
    protected void done(){
       try {
           TableModel model = get();
           this.tabler.setModel(model);
       } catch (InterruptedException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       } catch (ExecutionException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }


   }
}