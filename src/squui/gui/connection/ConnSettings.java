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
package squui.gui.connection;

import squui.gui.xml.XmlNode;

/**
 * Holds the settings related to a database connection. 
 */
public class ConnSettings {

    public String name;
    public String user;
    public String host;
    public String pass;
    
    public XmlNode toXmlNode() {
        XmlNode node = new XmlNode();
        node.name = "connection";
        node.addAttr("user", user);
        node.addAttr("host", host);
        node.addAttr("pass", pass);
        node.addAttr("name", name);
        return node;
    }
    
    public void load(XmlNode node) {
        user = node.getAttr("user");
        host = node.getAttr("host");
        pass = node.getAttr("pass");
        name = node.getAttr("name");
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
