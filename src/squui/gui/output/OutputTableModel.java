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
package squui.gui.output;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class OutputTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<OutputEntry> rows;
    private String[] columns;

    public OutputTableModel() {
        rows = new ArrayList<OutputEntry>();
        columns = new String[] {"Action", "Message"};
    }

    public void add(OutputEntry e) {
        rows.add(e);
    }
    
    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OutputEntry e = rows.get(rowIndex);
        if (columnIndex == 0)
            return e.action;
        else
            return e.message;
    }

}
