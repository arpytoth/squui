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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import squui.gui.connection.ConnectionSettings;
import squui.gui.xml.XmlNode;
import squui.log.Log;

public class Settings {

    private static Settings instance;
    public ArrayList<ConnectionSettings> connections;

    public Settings() {
        connections = new ArrayList<ConnectionSettings>();
    }

    public static Settings get() {
        if (instance == null)
            instance = new Settings();

        return instance;
    }

    public void load() {

    }

    public void save() {
        XmlNode node = new XmlNode();
        node.name = "settings";
        for (ConnectionSettings s: connections) {
            node.children.add(s.toXmlNode());
        }
        
        String str = node.toString();
        
        try {
            FileOutputStream outStream = new FileOutputStream("settings.txt");
            PrintStream out = new PrintStream(outStream);
            out.print(str);
            out.close();
        } catch (FileNotFoundException e) {
            Log.error(e);
        }

    }

    
    public static void main(String[] args) {
        ConnectionSettings s1 = new ConnectionSettings();
        s1.name = "Connection1";
        s1.host = "192.162.200.169";
        s1.user = "root";
        s1.pass = "cvs1cvs2cvs3";
        
        Settings.get().connections.add(s1);
        Settings.get().save();
    }
}
