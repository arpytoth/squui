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

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Style {
    

    public static Font font = new JLabel().getFont();
    public static Font terminalFont = new JTextArea().getFont();
    public static Font boldedFont = font.deriveFont(Font.BOLD);
    public static Font linkFont = font;
    static {
        Map<TextAttribute, Integer> fontAttr;
        fontAttr = new HashMap<TextAttribute, Integer>();
        fontAttr.put(TextAttribute.UNDERLINE, 
            TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
        linkFont = font.deriveFont(fontAttr);
    }
    
	public static Color pnlBkColor = new JPanel().getBackground();
	public static Color connBkColor = pnlBkColor.darker();
	public static Color connHoverColor = pnlBkColor.brighter();
	
}
