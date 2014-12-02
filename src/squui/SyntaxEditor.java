package squui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Highlighter;

public class SyntaxEditor extends JTextPane{
    
    public static String word = "SELECT";
    public static Highlighter highlighter = new SyntaxHighlighter(null);
    
    public SyntaxEditor() {
        setHighlighter(highlighter);  
        final WordSearcher searcher = new WordSearcher(this);
        
        getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent evt) {
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                         searcher.search();
                    }    
                }); 
            }

            public void removeUpdate(DocumentEvent evt) {
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                         searcher.search();
                    }    
                }); 
            }

            public void changedUpdate(DocumentEvent evt) {
            }
        });
    }
 
}


