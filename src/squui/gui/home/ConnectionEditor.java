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
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import squui.gui.Style;
import squui.gui.connection.ConnSettings;

/**
 * Component used to edit information regarding a Connection.
 */
@SuppressWarnings("serial")
public class ConnectionEditor extends JPanel {

    JTextField nameField;
    JTextField hostField;
    JTextField passField;
    JTextField userField;
    JPanel labelPanel;
    JPanel editorPanel;
    ConnSettings conn;
    int lines = 0;
    boolean setConnFlag;
    
    ConnectionEditor(ConnSettings conn) {
        if (conn == null) 
            throw new NullPointerException("The conn parameter is null.");
        
        this.conn = conn;
        this.setBackground(Style.pnlBkColor);
        nameField = text(conn.name);
        hostField = text(conn.host);
        passField = text(conn.pass);
        userField = text(conn.user);
        
        labelPanel = new JPanel();
        editorPanel = new JPanel();
        
        line("Connection Name: ", nameField);
        line("Host: ", hostField);
        line("Username: ", userField);
        line("Password: ", passField); 
        labelPanel.setLayout(new GridLayout(lines, 1));    
        editorPanel.setLayout(new GridLayout(lines, 1));
        
        setLayout(new BorderLayout());
        add(labelPanel, BorderLayout.WEST);
        add(editorPanel, BorderLayout.CENTER);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setConnFlag = false;
    }
    
    public void setConn(ConnSettings conn) {
        setConnFlag = true;
        this.conn = conn;
        nameField.setText(conn.name);
        hostField.setText(conn.host);
        passField.setText(conn.pass);
        userField.setText(conn.user);
        setConnFlag = false;
    }
    
    public void saveConnection() {
        conn.host = hostField.getText();
        conn.name = nameField.getText();
        conn.pass = passField.getText();
        conn.user = userField.getText();
    }
    
    private void line(String text, JComponent editor) {
        lines++;
        labelPanel.add(label(text));
        editorPanel.add(editor);
    }
    
    private JTextField text(String value) {
        JTextField field = new JTextField(value);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        field.setPreferredSize(new Dimension(200, 10));
        field.setMinimumSize(new Dimension(200, 10));
        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!setConnFlag)
                    saveConnection();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!setConnFlag)
                    saveConnection();
            }
            
        });
        return field;
    }
    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Style.font);
        label.setBackground(Style.pnlBkColor);
        return label;
    }
}
