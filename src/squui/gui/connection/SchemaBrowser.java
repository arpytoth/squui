package squui.gui.connection;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class SchemaBrowser extends JPanel{
    
    private JTree schemaTree;
    private ConnectionWrapper connection;
    
    public SchemaBrowser(ConnectionWrapper connection) {
        this.connection = connection;
        schemaTree = new JTree();
        JScrollPane scrollSchemaTree = new JScrollPane(schemaTree);
        
        setLayout(new BorderLayout());
        add(scrollSchemaTree, BorderLayout.CENTER);
        buildTree();
    }
    
    
    private void buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        ArrayList<Schema> schemas = connection.getSchemas();
        for (Schema s : schemas) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode();
            node.setUserObject(s);
            root.add(node);
        }
        schemaTree.setModel(new DefaultTreeModel(root));
    }

}
