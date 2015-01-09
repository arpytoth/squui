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

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import squui.gui.MainFrame;
import squui.gui.Style;
import squui.gui.EnhancedMouseAdapter;
import squui.gui.connection.ConnSettings;

/**
 * A button for a connection on the HomePanel. The connection thing from
 * the home panel. You can click on it to edit or double click to connect.
 */
@SuppressWarnings("serial")
public class ConnButton extends JPanel {
    
    private MouseAdapter mouseAdapter = new EnhancedMouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            setBackground(Style.connHoverColor);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            setBackground(Style.connBkColor);
        }
        
        @Override
        public void mouseSingleClicked(MouseEvent e) {
            ConnManager connManager = new ConnManager(settings);
            connManager.setVisible(true);
        }
        
        @Override
        public void mouseDoubleClicked(MouseEvent e) {
            MainFrame.get().openConnection(settings);
        }
    };
        
    ConnSettings settings;
    
	public ConnButton(ConnSettings settings) {
	    this.settings = settings;
		setMaximumSize(new Dimension(200, 100));
		setMinimumSize(new Dimension(200, 100));
		setPreferredSize(new Dimension(200, 100));
		setBackground(Style.connBkColor);
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
 
		buildGui();
	}
	
	private void buildGui() {
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    add(label("Name:" + settings.name));
	    add(label(settings.host));
	    add(label(settings.user));
	}
	
	private JLabel label(String text) {
	    JLabel label = new JLabel();
	    label.setBackground(Style.connBkColor);
	    label.setText(text);
	    return label;
	}

}
