/* 
 * SQUUI by Toth Arpad
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

import java.awt.FlowLayout;

import javax.swing.JPanel;

import squui.gui.Settings;
import squui.gui.TabPanel;
import squui.gui.connection.ConnectionSettings;

@SuppressWarnings("serial")
public class HomePanel extends TabPanel {

    public HomePanel()   {
        Settings settings = Settings.get();
        for (int i = 0; i < settings.connections.size(); i++) {
            ConnectionSettings cs = settings.connections.get(i);
            ConnectionPanel panel = new ConnectionPanel();
            add(panel);
        }
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }


    
    private class AddConnectionPane extends JPanel {
        
    }
}
