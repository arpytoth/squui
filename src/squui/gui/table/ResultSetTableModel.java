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
    private ArrayList<Integer> primaryKey;
    private ArrayList<String> columns;
    private ArrayList<Row> rows;
    private boolean readOnly;
    private String tableName;
    private String schemaName;
    
    public ResultSetTableModel(ConnectionWrapper conn, ResultSet rs) {
        this.rows = new ArrayList<Row>();
        this.columns = new ArrayList<String>();
        this.readOnly = false;
        this.primaryKey = new ArrayList<Integer>();

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
                    schemaName = s.name;
                    Table t = s.getTable(tableName);
                    if (t == null) {
                        readOnly = true;
                    } else {
                        for (Column c : t.columns) {
                            int index = getIndexForColumn(c.name);
                            if (c.isColumnKey ) {
                                if (index < 0) {
                                    readOnly = true;
                                } else {
                                    primaryKey.add(index);
                                }
                            } 
                        }
                    }
                }
            }

            while (rs.next()) {
                Row row = new Row();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    row.add(rs.getObject(i));
                }
                rows.add(row);
            }
           
        } catch (SQLException e) {
            Log.error(e);
        }
        
        if (!readOnly)
            rows.add(new Row(true));
    }
    
    public ArrayList<String> getUpdateQueries() {
        ArrayList<String> sqlList = new ArrayList<String>();
        
        
        for (Row u : rows) {
            if (!u.isNew && u.isEdited) {
                String sql = "UPDATE `" + schemaName + "`.`" + tableName +
                    " SET ";

                ArrayList<FieldSet> setList = u.getChanges();
                if (setList == null || setList.isEmpty())
                    continue;

                for (int i = 0; i < setList.size(); i++) {
                    FieldSet s = setList.get(i);

                    if (i > 0)
                        sql += ", ";
                    sql += "`" + s.col + "`=`" + s.value + "`";
                }
                sql += " WHERE ";
                for (int i = 0; i < primaryKey.size(); i++) {
                    int index = primaryKey.get(i);

                    if (i > 0)
                        sql += " and ";
                    sql += "`" + columns.get(index) + "`=`" +
                        u.values.get(i).oldVal + "`";
                }
                sql += ";";
                sqlList.add(sql);
            } else if (u.isNew && u.isEdited) {
                String sql = "INSERT INTO `";
                sql +=  schemaName + "`.`" + tableName + "` (";

                String values = " VALUES (";
             
                boolean first = true;
                for (int i = 0; i < u.values.size(); i++) {
                    Value val = u.get(i);
                    if (val.val != null) {
                        if (first == true) {
                            first = false;
                        } else {
                            sql += ", ";
                            values +=", ";
                        }
                        
                        String column = columns.get(i);
                        
                        sql += "`" + column + "`";
                        values += "'" + val.val + "'";
                    }
                }
                sql += ")" + values + ");";
                sqlList.add(sql);
            }
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
        Row row = rows.get(rowIndex);
        row.update(columnIndex, aValue);
        if (!readOnly && rowIndex == getRowCount() - 1) {
            rows.add(new Row(true));
            fireTableDataChanged();
        }
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
        Row row = rows.get(rowIndex);
        return row.get(columnIndex).val;
    }
    
    private int getIndexForColumn(String column) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(column)) 
                return i;
        }
        return -1;
    }
    
  
    class Value {
        Object oldVal;
        Object val;
    }
    
    class FieldSet {
        String col;
        Object value;
    }
    
    class Row {
        ArrayList<Value> values;
        boolean isNew;
        boolean isEdited;
        
        Row(boolean isNew) {
            this.isNew = isNew;
            isEdited = false;
            values = new ArrayList<ResultSetTableModel.Value>();
            if (isNew) {
                for (int i = 0; i < columns.size(); i++)
                    values.add(new Value());
            }
        }
        
        Row() {
           this(false);
        }
        
        void add(Object obj) {
            Value val = new Value();
            val.val = obj;
            val.oldVal = obj;
            values.add(val);
        }
        
        Value get(int column) {
            return values.get(column);
        }
        
        ArrayList<FieldSet> getChanges() {
            ArrayList<FieldSet> setList = new ArrayList<FieldSet>();
            for (int i = 0; i < values.size(); i++)  {
                Value v = values.get(i);
                if (v.val != v.oldVal) {
                    FieldSet set = new FieldSet();
                    set.col = columns.get(i);
                    set.value = v.val;
                    setList.add(set);
                }
            }
            return setList;
        }
        
        void update(int colIndex, Object newVal) {
            Value value = get(colIndex);
            value.val = newVal;
            isEdited = true;
        }
    }
}
