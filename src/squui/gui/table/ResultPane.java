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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import squui.gui.connection.ConnectionWrapper;

public class ResultPane extends JPanel{

    /** Not used at all. Great isn't it? */
    private static final long serialVersionUID = 3561090126994194074L;
    
    private JTable table;
    private ResultSetTableModel model;
    private JToolBar toolBar;
    private ResultPaneListener listener;
    
    public ResultPane(ConnectionWrapper conn, ResultSet rs) {
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFillsViewportHeight(true);

        model = new ResultSetTableModel(conn, rs);
       
        toolBar = new JToolBar();
        toolBar.add(toolButton("Apply", new ApplyAction()));
        
        JScrollPane scrollTable = new JScrollPane(table);
        table.setModel(model);
        setLayout(new BorderLayout());
        add(scrollTable, BorderLayout.CENTER);
        add(toolBar, BorderLayout.NORTH);
    }
    
    public void setListener(ResultPaneListener l) {
        this.listener = l;
    }
    
    private JButton toolButton(String caption, Action action) {
        JButton button = new JButton(action);
        if (caption != null) 
            button.setText(caption);
        return button;
    }
    
    private void notifyApply()  {
        if (listener != null) {
            ArrayList<String> sqlList = model.getUpdateQueries();
            listener.apply(sqlList);
        }
    }

    private class ApplyAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
      
        ApplyAction() {
            setName("Apply");
            setToolTipText("Apply changes to record set");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            notifyApply();
        }
    }
}
