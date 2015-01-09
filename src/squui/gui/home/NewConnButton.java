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

import javax.swing.JLabel;

import squui.gui.Resources;
import squui.gui.Style;

/**
 * When clicked this button will show the {@link NewConnectionFrame} dialog.
 * This can be used to create a new connection.
 */
@SuppressWarnings("serial")
public class NewConnButton extends JLabel {

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            setBackground(Style.connHoverColor);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            setBackground(Style.connBkColor);
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            NewConnectionFrame frame = new NewConnectionFrame();
            frame.setVisible(true);
        }
    };
    
    public NewConnButton() {
        setMaximumSize(new Dimension(50, 50));
        setMinimumSize(new Dimension(50, 50));
        setPreferredSize(new Dimension(50, 50));
        setText("");
        setIcon(Resources.getIcon("plus.png"));
        setBackground(Style.connBkColor);
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
    }

}
