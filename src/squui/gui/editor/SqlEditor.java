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
package squui.gui.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import squui.gui.connection.ConnectionPane;

/**
 * SQL text editor along with table
 */
public class SqlEditor extends JPanel {

    private static final long serialVersionUID = 1L;
    private Action runAction;
    private SqlTextPane textPane;
    private JToolBar toolBar;
    private JPanel editorPane;

    private ArrayList<SqlEditorListener> listeners;

    public SqlEditor(ConnectionPane connPane) {
        listeners = new ArrayList<SqlEditorListener>();
        setupActions();
        textPane = new SqlTextPane();

        toolBar = new JToolBar();
        toolBar.add(runAction);

        editorPane = new JPanel();
        editorPane.setLayout(new BorderLayout());
        editorPane.add(textPane, BorderLayout.CENTER);
        editorPane.add(toolBar, BorderLayout.NORTH);

        setLayout(new BorderLayout());
        add(editorPane, BorderLayout.CENTER);
    }

    public void addListener(SqlEditorListener l) {
        listeners.add(l);
    }

    public void removeListener(SqlEditorListener l) {
        listeners.remove(l);
    }

    private void notifySqlExecuted() {
        ArrayList<String> sqlList = new ArrayList<String>();
        sqlList.add(textPane.getText());
        for (SqlEditorListener l : listeners) {
            l.executed(sqlList);
        }
    }

    private void setupActions() {
        runAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                notifySqlExecuted();
            }
        };
    }
}
