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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import squui.gui.MainFrame;
import squui.gui.Settings;
import squui.gui.connection.ConnSettings;

/**
 * Dialog displayed when user clicks on the "plus" button from the home screen.
 * (NewConnButton class). This will create a new connection is user presses
 * the OK button if not it will simply hide.
 */
@SuppressWarnings("serial")
public class NewConnectionFrame extends JDialog {
 
    ConnectionEditor editor;
    ConnSettings conn;
    JButton okButton;
    JButton cancelButton;
    
    ActionListener buttonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                editor.saveConnection();
                Settings.get().connections.add(editor.conn);
                Settings.get().save();
                HomePanel.get().refreshHomePanel();
            } 
            
            setVisible(false);
        }  
    };
    
    public NewConnectionFrame() {
        super(MainFrame.get(), "New Connection", true);
        setTitle("New Connection");
        conn = new ConnSettings();
        editor = new ConnectionEditor(conn);
        okButton = new JButton("OK");
        okButton.addActionListener(buttonAction);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(buttonAction);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.add(editor, BorderLayout.CENTER);
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(MainFrame.get());
    }
    
    

}
