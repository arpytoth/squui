package squui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class WordSearcher {
    protected JTextPane comp;
    SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
    SimpleAttributeSet sas = new SimpleAttributeSet();
    DefaultStyledDocument doc;
    
    public WordSearcher(JTextPane comp) {
        this.comp = comp;
        doc = new SqlDocument();
        comp.setDocument(doc);
        StyleConstants.setForeground(sas, Color.YELLOW);
       
    }

    // Search for a word and return the offset of the
    // first occurrence. Highlights are added for all
    // occurrences found.
    public int search() {
     return 1;
    }

   
}