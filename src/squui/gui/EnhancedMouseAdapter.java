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
package squui.gui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.Timer;



/**
 * Used to properly detect between single and double click scenarios. Java
 * default implementation is well, OK, but you won't be able to distinguish 
 * between single and double click. The thing is that every time double click
 * occurs a single click event will be triggered too. If you want separate
 * actions for click and double click you're out of luck.
 */
public class EnhancedMouseAdapter extends MouseAdapter {
    
    private boolean wasDoubleClick;
    
    public EnhancedMouseAdapter() {
        wasDoubleClick = false;
    }
    
    @Override
    public final void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() == 2) {
            wasDoubleClick = true;
        } else {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Integer interval = (Integer) tk.getDesktopProperty(
                "awt.multiClickInterval");

            Timer timer = new Timer(interval, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (wasDoubleClick)
                                mouseDoubleClicked(e);
                            else
                                mouseSingleClicked(e);
                            wasDoubleClick = false;
                        }
                    });
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void mouseSingleClicked(MouseEvent e) {
        
    }
    
    public void mouseDoubleClicked(MouseEvent e) {
        
    }
}
