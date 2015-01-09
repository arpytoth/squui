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

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Handle the image resources used by the SQUUI application.
 */
public class Resources {
    
    private static HashMap<String, ImageIcon> iconMap = 
        new HashMap<String, ImageIcon>();
    
    
    public static ImageIcon loadIconGif(String path) {
        try {
            
            URL url = ClassLoader.getSystemResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                return icon;
            } else {
                ImageIcon icon = new ImageIcon(path);
                return icon;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
    
    public static ImageIcon getIcon(String path) {
        if (iconMap.containsKey(path)) 
            return iconMap.get(path);
        ImageIcon icon = iconMap.get(path);
        if (icon == null) {
            icon = loadIcon(path);
            iconMap.put(path, icon);
        }
        return icon;
    }
    
    public static ImageIcon loadIcon(String path) {
        try {
            URL url = ClassLoader.getSystemResource(path);
            BufferedImage bufImg = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(bufImg);
            return icon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
