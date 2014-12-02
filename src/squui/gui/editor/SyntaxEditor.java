package squui.gui.editor;

import javax.swing.JTextPane;


public class SyntaxEditor extends JTextPane{
    

    public SyntaxEditor() {
        setDocument(new SqlDocument());
        
    }
 
}


