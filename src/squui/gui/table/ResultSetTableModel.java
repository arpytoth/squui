/* 
 * Copyright (C) 2014 Toth Arpad 
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License along
 *   with this program; if not, write to the Free Software Foundation, Inc.,
 *   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *    
 *   Toth Arpad (arpytoth@yahoo.com)
 */
package squui.gui.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import squui.gui.connection.Column;
import squui.gui.connection.ConnectionWrapper;
import squui.gui.connection.Schema;
import squui.gui.connection.Table;
import squui.log.Log;

/**
 * TableModel that uses a {@link ResultSet} instance to populate 
 * the table data.
 */
public class ResultSetTableModel extends AbstractTableModel{

    private static final long serialVersionUID = 1L;
    private ArrayList<String> primaryKey;
    private ArrayList<String> columns;
    private ArrayList<Object[]> rows;
    private ArrayList<Object[]> oldRows;
    private HashMap<String, UpdateDescriptor> updates;
    private boolean readOnly;
    private String tableName;
    private String schemaName;
    
    @SuppressWarnings("unchecked")
    public ResultSetTableModel(ConnectionWrapper conn, ResultSet rs) {
        this.rows = new ArrayList<Object[]>();
        this.columns = new ArrayList<String>();
        this.readOnly = false;
        this.primaryKey = new ArrayList<String>();
        this.updates = new HashMap<String, UpdateDescriptor>();
        
        try {
            ResultSetMetaData data = rs.getMetaData();

            
            
            
            for (int i = 1; i <= data.getColumnCount(); i++) {
                String name = data.getColumnName(i);
                columns.add(name);
                
                if (schemaName == null)
                    schemaName = data.getSchemaName(i);
                else if (!schemaName.equals(data.getSchemaName(i))) 
                    readOnly = true;
                
                if (tableName == null)
                    tableName = data.getTableName(i);
                else if (!tableName.equals(data.getTableName(i)))
                    readOnly = true;
            }
            
            if (!readOnly && schemaName != null && tableName != null) {
                Schema s = conn.getSchema(schemaName);
                if (s == null) {
                    readOnly = true;
                } else {
                    Table t = s.getTable(tableName);
                    if (t == null) {
                        readOnly = true;
                    } else {
                        for (Column c : t.columns) {
                            if (c.isColumnKey && !columns.contains(c.name)) {
                                readOnly = true;
                                break;
                            } else {
                                primaryKey.add(c.name);
                            }
                        }
                    }
                }
            }

            while (rs.next()) {
                Object[] row = new Object[data.getColumnCount()];
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    row[i - 1] = rs.getObject(i);
                }
                rows.add(row);
            }
           
        } catch (SQLException e) {
            Log.error(e);
        }
        oldRows = (ArrayList<Object[]>) rows.clone();
    }
    
    public ArrayList<String> getUpdateQueries() {
        ArrayList<String> sqlList = new ArrayList<String>();
        Collection<UpdateDescriptor> updCol = updates.values();
        
        for (UpdateDescriptor u : updCol) {
            String sql = "UPDATE `" + schemaName + "`.`" + tableName + " SET ";
            boolean first = true;
            for (FieldSet s : u.setList) { 
                if (first) 
                    first = false;
                else 
                    sql += ", ";
                sql += "`" +s.column+ "`=`"+ s.value +"`";              
            }
            sql += " WHERE ";
            for (int i = 0; i < u.keyValues.size(); i++) { 
                if (i > 0) 
                    sql += " and ";
                sql += "`"+primaryKey.get(i)+"`=`"+ u.keyValues.get(i) +"`";              
            }
            sql += ";";
            sqlList.add(sql);
        }
        return sqlList;
    }
    

    @Override
    public String getColumnName(int column) {
        return columns.get(column);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return !readOnly;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Object[] row = rows.get(rowIndex);
        row[columnIndex] = aValue;
       
        ArrayList<Object> keyVals = new ArrayList<Object>();
        String hashKey = getKey( rowIndex, columnIndex, keyVals);
        UpdateDescriptor update = updates.get(hashKey);
        if (update == null) {
            update = new UpdateDescriptor();
            updates.put(hashKey, update);
            update.keyValues =keyVals;
        }
        update.add(columnIndex, aValue);
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] row = rows.get(rowIndex);
        return row[columnIndex];
    }
    
    private int getIndexForColumn(String column) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(column)) 
                return i;
        }
        return -1;
    }
    
    private String getKey(int row, int col,  ArrayList<Object> keyValues) {
        String hashKey = "";
        Object[] rowVals = oldRows.get(row);
        for (String c : primaryKey) {
            int index = getIndexForColumn(c);
            Object val = rowVals[index];
            keyValues.add(val);
            hashKey += String.valueOf(val) + "_";
        }
        return hashKey;
    }
    
    class FieldSet {
        int column;
        Object value;
    }
    
    class UpdateDescriptor {
        ArrayList<Object> keyValues;
        ArrayList<FieldSet> setList;

        UpdateDescriptor() {
            setList = new ArrayList<FieldSet>();
        }
        
        public void add(int column, Object value) {
            FieldSet fs = new FieldSet();
            fs.column = column;
            fs.value = value;
            setList.add(fs);
        }
    }
    
}
