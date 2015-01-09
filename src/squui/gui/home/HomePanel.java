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

import squui.gui.Settings;
import squui.gui.TabPanel;
import squui.gui.connection.ConnSettings;

/**
 * The home panel. Always the first tab and cannot be closed. There is only
 * one home panel so we will simplify our code by making it singleton. Why
 * bother with observers and references and so on..
 */
@SuppressWarnings("serial")
public class HomePanel extends TabPanel {

    private static HomePanel instance;
    
    private HomePanel() {
       refreshHomePanel();
    }

    /**
     * Used when the connection list changes. This method will cause the
     * HomePanel to update its connection list.
     */
    public void refreshHomePanel() {
        Settings settings = Settings.get();
        removeAll();
        for (int i = 0; i < settings.connections.size(); i++) {
            ConnSettings cs = settings.connections.get(i);
            ConnButton panel = new ConnButton(cs);
            add(panel);
        }
        add(new NewConnButton());
        setLayout(new FlowLayout(FlowLayout.LEFT));
        validateTree();
        repaint();
    }
    
    public static HomePanel get() {
        if (instance == null)
            instance = new HomePanel();
        return instance;
    }
}
