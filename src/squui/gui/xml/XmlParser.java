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
package squui.gui.xml;

import java.io.IOException;
import java.io.InputStream;

/**
 * A very simple XML parser. It is used only for settings and does not support
 * validation or things like that.
 */
public class XmlParser {
    
    public InputStream in;
    public int ch;
    
    public XmlParser(InputStream in) {
        this.in = in;
    }
    
    public int readChar()
    {
        try {
            ch = in.read();
        } catch (IOException e) {
            ch = -1; 
        } 
        return ch;
    }


    int nextChar()
    {
        ch = readChar();
        while (ch < 32 || ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t')
            ch = readChar();
        return ch;
    }


    /**
     * Parse an identifier: tag name, attribute name. A word basically. This
     * will also skip the whitespace.
     */
    String parseWord() {
        String readed_word = "";

        while ( (ch >= 'a' && ch <= 'z') ||
                (ch >= 'A' && ch <= 'Z') ||
                (ch >= '0' && ch <= '0') ||
                (ch == '_') ||
                (ch == '-') ) {
            if (ch == -1 )
                throw new XmlException("End of file reached");

            readed_word += (char)ch;
            readChar();
        }
        if (readed_word.length() == 0)
            throw new XmlException("Word expected.");

        if (ch == ' ')
            nextChar();
        
        return readed_word;
    }

    /*
     * Parse a quoted string from the stream. 
     */
    String parseQuotedString() {
        String readed_value = "";

        while (ch != '"') {
            readed_value += (char)ch;
            readChar();
        }
        nextChar();
        if (ch == ' ')
            nextChar();

        return readed_value;
    }

    /*
     * Parse xml string value. The input must be located right after the >
     * character, and the last readed character will be the first character in
     * the string value. This method will consume the input until the < 
     * character is readed. This method will leave the cursor at the < position,
     * so the parser can safely read the next node. In this case it is expected
     * an end tag, a child node or a cdata node, thats why we read until <.
     */
    String parseStringValue()
    {
        String readed_value = "";

        while (ch != '<')
        {
            if (ch == -1)
                throw new XmlException("End of file.");

            readed_value += (char)ch;
            readChar();
        }
        return readed_value;
    }


    XmlAttr parseAttribute()
    {
        XmlAttr attr = new XmlAttr();
        
        attr.name = parseWord();
        if (ch != '=')
            throw new XmlException("= expected.");

        nextChar();
        if (ch != '"')
           throw new XmlException("\" expected.");

        nextChar();
        
        attr.value = parseQuotedString();
        return attr;
    }

    /*
     * Parse the next XML node. To call this method you should alway make sure 
     * that the next readed char is <. So don't call this with last char set to
     * < and assume it will work. This is necessary because we want to make sure
     * once a node is readed it is returned and we don't read for another 
     * character. Reading from stream for an extra char will cause the current
     * node to be parsed only if a second node is sent on the stream too. That
     * would be wrong in a blocking stream situation. Also note that this method
     * will return after the last > is readed. This is trivial.
     *
     * EXCEPTION: When reading a sequence of string value, children and cdata
     *            nodes, it is permitted for the last char to be <. But only in
     *            this scenario and note that this method MUST return after the
     *            last > character was readed.
     */
     public XmlNode parseNode() {
        XmlNode node = new XmlNode();
        if (ch != '<')
            nextChar();

        if (ch != '<')
            throw new XmlException("< expected.");

        nextChar();

    
        // CDATA <![CDATA[ ]]>
        if (ch == '!') {
            node.isCDataNode = true;
            node.isEndTagNode = false;
            nextChar();
            if (ch != '[')
                throw new XmlException("Invalid CDATA tag");
            
            nextChar();
            if (ch != 'C')
                throw new XmlException("Invalid CDATA tag");
           
            nextChar();
            if (ch != 'D')
                throw new XmlException("Invalid CDATA tag");
           
            nextChar();
            if (ch != 'A')
                throw new XmlException("Invalid CDATA tag");
            
            nextChar();
            if (ch != 'T')
                throw new XmlException("Invalid CDATA tag");
            
            nextChar();
            if (ch != 'A')
                throw new XmlException("Invalid CDATA tag");
            
            nextChar();
            if (ch != '[')
                throw new XmlException("Invalid CDATA tag");

            nextChar();
            while (true) {
                
                if (ch == -1)
                    throw new XmlException("End of file.");
                
                node.cdata += (char)ch;
                readChar();
                
                if (node.cdata.length() > 3) {
                    int size = node.cdata.length();
                    if (node.cdata.charAt(size-1) == '>' &&
                        node.cdata.charAt(size-2) == ']' &&
                        node.cdata.charAt(size-3) == ']') {
                        
                        node.cdata = node.cdata.substring(0, size - 3);
                        break;
                    }
                }
            }
            return node;
        }

        node.isCDataNode = false;

        if (ch == '/') {
            node.isEndTagNode = true;
            nextChar();
            node.name = parseWord();
            if (ch != '>')
                throw new XmlException("> expected");
            return node;
        } else {
            node.isEndTagNode = false;
            
            node.name = parseWord();
   
            while (ch != '>' && ch != '/') {
                XmlAttr attr = parseAttribute();
                node.attributes.add(attr);
            }

            if (ch == '>') {
                nextChar();
                boolean end_tag_readed = false;
                do {
                    if (ch == '<') {

                        XmlNode child;
                        child = parseNode();
    
                        if (child.isCDataNode) {
                            node.cdata = child.cdata;
                            nextChar();
                            return node;
                        } else if (child.isEndTagNode) {
                            if (!child.name.equals(node.name))
                                throw new XmlException("> expected.");
                            end_tag_readed = true;
                        } else {
                            node.children.add(child);
                            nextChar();
                            return node;
                        }
                    } else {
                        node.value = parseStringValue();
                        return node;
                    }
                } while (end_tag_readed == false);
            }
            else
            {
                nextChar();
                if (ch != '>')
                    throw new XmlException("> expected.");
            }
            return node;
        }
    }
}
