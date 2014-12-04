package squui.gui.editor;

import javax.swing.JTextPane;

public class SqlTextPane extends JTextPane{
    

    public SqlTextPane() {
        setDocument(new SqlDocument());
        
    }
 
}