package squui.gui.table;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import squui.log.Log;

public class ResultSetTable extends JPanel{

    /** Not used at all. Great isn't it? */
    private static final long serialVersionUID = 3561090126994194074L;
    
    private JTable table;
    private DefaultTableModel model;
    private DefaultTableColumnModel columnModel;
   
    public ResultSetTable(ResultSet rs) {
        table = new JTable();
        table.setFillsViewportHeight(true);

        model = new DefaultTableModel();
        columnModel = new DefaultTableColumnModel();
        
        try {
            ResultSetMetaData data = rs.getMetaData();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                String name = data.getColumnName(i);
                model.addColumn(name);
                TableColumn column = new TableColumn();
                columnModel.addColumn(column);
            }
            
            while (rs.next()) {
                Object[] row = new Object[data.getColumnCount()];
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    row[i-1] = rs.getObject(i);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            Log.error(e);
        }
        
        JScrollPane scrollTable = new JScrollPane(table);
        table.setModel(model);
        table.setColumnModel(columnModel);
        setLayout(new BorderLayout());
        add(scrollTable, BorderLayout.CENTER);
    }

}
