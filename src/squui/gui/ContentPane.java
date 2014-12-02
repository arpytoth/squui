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
package squui.gui;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


/**
 * Blueprint for a content panel that can be added to the main frame tabbed
 * pane.
 */
public class ContentPane extends JPanel implements ContainerListener {

	/** Uber crap. I don't want to surpass warnings, so I must add this.*/
    private static final long serialVersionUID = 1L;
    private JTabbedPane parentTabedPane;
    private int tabIndex;
	
	public ContentPane(){
	    parentTabedPane = MainFrame.get().tabsPane;
	    parentTabedPane.addContainerListener(this);
	}
	
	public void release() {
	    parentTabedPane.removeContainerListener(this);
    }
	
	public void setTabTitle(String title) {
	    if (parentTabedPane != null && tabIndex >= 0)
	        parentTabedPane.setTitleAt(tabIndex, title);
	}

    @Override
    public void componentAdded(ContainerEvent e) {
       if (e.getChild() == this) {
           tabIndex = parentTabedPane.getTabCount() - 1;
       }
    }

    @Override
    public void componentRemoved(ContainerEvent e) {
        if (e.getChild() == this){
            release();
            for (int i = tabIndex; i < parentTabedPane.getTabCount(); i++) {
                ContentPane panel;
                panel = (ContentPane) parentTabedPane.getComponentAt(i);
                if (panel != null) {
                    panel.tabIndex = panel.tabIndex - 1;
                }
            }
        }
    }
}
