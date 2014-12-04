package squui.gui.connection;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
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
    
    
    public void buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        ArrayList<Schema> schemas = connection.getSchemas();
        for (Schema s : schemas) {
            DefaultMutableTreeNode sNode = new DefaultMutableTreeNode();
            sNode.setUserObject(s);
            for (Table t : s.tables) {
                DefaultMutableTreeNode tNode = new DefaultMutableTreeNode();
                tNode.setUserObject(t);
                sNode.add(tNode);
                for (Column c : t.columns) {
                    DefaultMutableTreeNode cNode = new DefaultMutableTreeNode();
                    cNode.setUserObject(c);
                    tNode.add(cNode);
                }
            }
            root.add(sNode);
        }
        schemaTree.setModel(new DefaultTreeModel(root));  
        schemaTree.setCellRenderer(new SchemaTreeCellRenderer());
    }

    private class SchemaTreeCellRenderer extends DefaultTreeCellRenderer {
          /** Added to avoid warnings. */
        private static final long serialVersionUID = 1L;
        
        private Font normalFont;
        private Font boldedFont;
        
        SchemaTreeCellRenderer() {
            normalFont = null;
            boldedFont = null;
        }
      

        @Override
        public Component getTreeCellRendererComponent(JTree tree,
                Object value, boolean selected, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            JComponent c = (JComponent) super.getTreeCellRendererComponent(
                    tree, value, selected, expanded, leaf, row, hasFocus);
            
            if (normalFont == null) {
                normalFont = c.getFont();
                boldedFont = normalFont.deriveFont(Font.BOLD);
            }
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            c.setFont(normalFont);
            if (node.getUserObject() instanceof Schema) {
                Schema s = (Schema)node.getUserObject();
                if (s.isSelected)
                    c.setFont(boldedFont);
            } 
            return c;
        }
    }
}
