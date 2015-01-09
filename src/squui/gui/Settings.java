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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import squui.gui.connection.ConnSettings;
import squui.gui.xml.XmlNode;
import squui.gui.xml.XmlParser;
import squui.log.Log;

/**
 * Holds all settings.
 */
public class Settings {

    private static Settings instance;
    public ArrayList<ConnSettings> connections;

    public Settings() {
        connections = new ArrayList<ConnSettings>();
    }

    public static Settings get() {
        if (instance == null)
            instance = new Settings();

        return instance;
    }

    public void load() {
        try {
            FileInputStream in = new FileInputStream("settings");
            XmlParser parser = new XmlParser(in);
            XmlNode node = parser.parseNode();
            
            for (int i = 0; i < node.children.size(); i++) {
                XmlNode child = node.children.get(i);
                if (child.name.equals("connection")) {
                    ConnSettings c = new ConnSettings();
                    c.load(child);
                    connections.add(c);
                }
            }
        } catch (FileNotFoundException e) {
            Log.error(e);
        }
    }

    public void save() {
        XmlNode node = new XmlNode();
        node.name = "settings";
        for (ConnSettings s: connections) {
            node.children.add(s.toXmlNode());
        }
        
        String str = node.toString();
        
        try {
            FileOutputStream outStream = new FileOutputStream("settings");
            PrintStream out = new PrintStream(outStream);
            out.print(str);
            out.close();
        } catch (FileNotFoundException e) {
            Log.error(e);
        }

    }
}
