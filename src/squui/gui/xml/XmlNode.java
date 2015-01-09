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
package squui.gui.xml;

import java.util.ArrayList;

public class XmlNode {
    public static final String TAB = "\t";
    public static final String ENDLINE  = "\n";
    public String name;
    public String value;
    public String cdata;
    public ArrayList<XmlAttr> attributes;
    public ArrayList<XmlNode> children;

    public boolean isEndTagNode;
    public boolean isCDataNode;
    
    public XmlNode() {
        attributes = new ArrayList<XmlAttr>();
        children = new ArrayList<XmlNode>();
    }
    
    public void addAttr(String name, String value) {
        XmlAttr attr = new XmlAttr();
        attr.name = name;
        attr.value = value;
        attributes.add(attr);
    }

    public String getAttr(String name) {
        for (XmlAttr attr: attributes) {
            if (attr.name.equals(name))
                return attr.value;
        }
        return null;
    }
    
    public boolean hasValue() {
        return value != null && !value.isEmpty();
    }

    public boolean hasCData() {
        return cdata != null && !cdata.isEmpty();
    }

    public boolean hasChildren() {
        return children.size() != 0;
    }

    public String toString(String tab) {
        
        StringBuffer buffer = new StringBuffer();
        boolean noContent = false;

        noContent = !hasChildren() && !hasCData() && !hasValue();

        buffer.append(tab);
        buffer.append("<").append(name);

        for (XmlAttr a : attributes) {
            buffer.append(" ");
            buffer.append(a.name);
            buffer.append("=\"");
            buffer.append(a.value);
            buffer.append("\"");
        }

        if (noContent) {
            buffer.append("/>").append(ENDLINE);
        } else {
            buffer.append(">").append(ENDLINE);

            if (hasCData()) {
                buffer.append(tab).append(TAB);
                buffer.append("<![CDATA[");
                buffer.append(cdata);
                buffer.append("]]>").append(ENDLINE);
            }

            if (hasValue()) {
                buffer.append(tab).append(TAB);
                buffer.append(value);
                buffer.append(ENDLINE);
            }

            for (XmlNode c : children) {
                buffer.append(c.toString(tab + TAB));
            }
            buffer.append(tab);
            buffer.append("</");
            buffer.append(name);
            buffer.append(">");
            buffer.append(ENDLINE);
        }
        return buffer.toString();
    }
    
    @Override
    public String toString() {
        return toString("");
    }
}
