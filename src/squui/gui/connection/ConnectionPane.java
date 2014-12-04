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
package squui.gui.connection;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import squui.gui.ContentPane;
import squui.gui.editor.SqlEditor;
import squui.gui.editor.SqlEditorListener;
import squui.gui.output.OutputEntry;
import squui.gui.output.OutputPanel;
import squui.gui.table.ResultPane;
import squui.gui.table.ResultPaneListener;

public class ConnectionPane extends ContentPane implements SqlEditorListener,
ResultPaneListener {
    
    private static final long serialVersionUID = 1L;
    public SchemaBrowser schemaBrowser;
    
    public ConnectionWrapper conn;
    public OutputPanel outputPanel;
    
    private SqlEditor editor;
    private ResultPane resultPane;
    private JPanel editorAndResultPanel;
    
    private JSplitPane outputEditorSplit;
    private JSplitPane leftRightSplit;
   
    public ConnectionPane() {
        conn = new ConnectionWrapper();
        conn.connect();
        
        buildGui();
    }

    private void buildGui() {
        schemaBrowser = new SchemaBrowser(conn);
        
        outputPanel = new OutputPanel();
        editor = new SqlEditor(this);
        editor.addListener(this);
        
        editorAndResultPanel = new JPanel();
        
        outputEditorSplit = new JSplitPane();
        outputEditorSplit.setTopComponent(editorAndResultPanel);
        outputEditorSplit.setBottomComponent(outputPanel);
        outputEditorSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        
        leftRightSplit = new JSplitPane();
        leftRightSplit.setLeftComponent(schemaBrowser);
        leftRightSplit.setRightComponent(outputEditorSplit);

        
        rebuildEditorAndResultPanel();
        setLayout(new BorderLayout());
        add(leftRightSplit, BorderLayout.CENTER);

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                revalidate();
                leftRightSplit.setDividerLocation(300);
                outputEditorSplit.setDividerLocation(400);
                revalidate();
            }
        });
    }
    
    private void rebuildEditorAndResultPanel() {
        editorAndResultPanel.removeAll();
        if (resultPane == null) {
            editorAndResultPanel.setLayout(new BorderLayout());
            editorAndResultPanel.add(editor, BorderLayout.CENTER);
            revalidate();
        } else {
            final JSplitPane splitPane = new JSplitPane();
            splitPane.setTopComponent(editor);
            splitPane.setBottomComponent(resultPane);
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            editorAndResultPanel.setLayout(new BorderLayout());
            editorAndResultPanel.add(splitPane, BorderLayout.CENTER); 
            revalidate();
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                    splitPane.setDividerLocation(0.6);
                }
            });
        }
       
    }


    @Override
    public void executed(ArrayList<String> sqlList) {  
        for (String sql : sqlList) { 
            OutputEntry e = new OutputEntry();
            e.action = sql;
            try {
                ResultSet rs = conn.sql(sql);
                resultPane = new ResultPane(conn, rs);
                resultPane.setListener(this);
                rebuildEditorAndResultPanel();
                
                e.message = "x row(s) returned";
            } catch (SQLException ex) {
                e.message = ex.getMessage();
                e.isError = true;
            }
            outputPanel.addEntry(e);
        }
        schemaBrowser.buildTree();
    }

    @Override
    public void apply(ArrayList<String> sqlList) {
        for (String sql : sqlList) { 
            OutputEntry e = new OutputEntry();
            e.action = sql;
            try {
                int rs = conn.update(sql);
                e.message = rs +" row(s) updated";
            } catch (SQLException ex) {
                e.message = ex.getMessage();
                e.isError = true;
            }
            outputPanel.addEntry(e);
        }
        schemaBrowser.buildTree();
    }
}
