package squui.gui.connection;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class SchemaBrowser extends JPanel{
    
    private JTree schemaTree;
    
    public SchemaBrowser() {
        schemaTree = new JTree();
        JScrollPane scrollSchemaTree = new JScrollPane(schemaTree);
        
        setLayout(new BorderLayout());
        add(scrollSchemaTree, BorderLayout.CENTER);
    }

}
