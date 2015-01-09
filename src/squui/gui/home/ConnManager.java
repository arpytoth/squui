/* 
 * SQUUI by Toth Arpad (Simple SQL GUI)
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
package squui.gui.home;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import squui.gui.MainFrame;
import squui.gui.Settings;
import squui.gui.connection.ConnSettings;

/**
 * The dialog used to edit the connection list. Displayed when you click on
 * a connection from the HomePanel. It is used to edit, add and delete 
 * connections.
 */
@SuppressWarnings("serial")
public class ConnManager extends JDialog {
    
    JList connList;
    ConnectionEditor editor;
    JButton newBtn;
    JButton delBtn;
    JButton closeBtn;
    
    ActionListener btnListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == delBtn) {
                ConnSettings conn = (ConnSettings) connList.getSelectedValue();
                Settings.get().connections.remove(conn);
                Settings.get().save();
                 
                refreshList();
                if (Settings.get().connections.isEmpty()) {
                    
                } else {
                    connList.setSelectedIndex(0);
                }
            } 
            
            if (e.getSource() == newBtn) {
                ConnSettings conn = new ConnSettings();
                conn.name = "New Connection";
                Settings.get().connections.add(conn);
                Settings.get().save();
                refreshList();
                connList.setSelectedValue(conn, true);
            }
            
            if (e.getSource() == closeBtn) {
                setVisible(false);
                HomePanel.get().refreshHomePanel();
            }
        } 
    };
    
    public ConnManager(ConnSettings selected) {
        super(MainFrame.get(), "Connection Manager", true);
        connList = new JList();
        connList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                ConnSettings conn = (ConnSettings) connList.getSelectedValue();
                if (conn != null)
                    editor.setConn(conn);                
            }
           
        });
            
        refreshList();
        JScrollPane connScroll = new JScrollPane(connList);
        
        editor = new ConnectionEditor(selected);
        
        newBtn = new JButton("New");
        newBtn.addActionListener(btnListener);
        delBtn = new JButton("Delete");
        delBtn.addActionListener(btnListener);
        closeBtn = new JButton("Close");
        closeBtn.addActionListener(btnListener);
        
        JPanel southPanel = new JPanel();
        southPanel.add(newBtn);
        southPanel.add(delBtn);
        southPanel.add(closeBtn);
        
        setLayout(new BorderLayout());
        add(editor, BorderLayout.CENTER);
        add(connScroll, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
        
        connList.setSelectedValue(selected, true);
        pack();
        setLocationRelativeTo(MainFrame.get());
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HomePanel.get().refreshHomePanel();
            }
                  
        });

    }
    
    void refreshList() {
        Settings s = Settings.get();
        DefaultListModel listModel = new DefaultListModel();
        for (ConnSettings c: s.connections) {
            listModel.addElement(c);
        }
        connList.setModel(listModel);
    }
    


}
