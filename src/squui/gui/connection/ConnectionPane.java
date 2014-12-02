package squui.gui.connection;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import squui.gui.ContentPane;
import squui.gui.editor.SyntaxEditor;
import squui.gui.table.ResultSetTable;

public class ConnectionPane extends ContentPane{
    
    private SchemaBrowser schemaBrowser;
    private SyntaxEditor editor;

    public ConnectionPane() {
        ConnectionWrapper connection = new ConnectionWrapper();
        connection.connect();
        schemaBrowser = new SchemaBrowser(connection);
        
        editor = new SyntaxEditor();
        
        setLayout(new BorderLayout());
        add(editor, BorderLayout.CENTER);
        add(schemaBrowser, BorderLayout.WEST);
        
        ResultSet rs;
        try {
            rs = connection.sql("SELECT * FROM translator_test.instrument");
            ResultSetTable view = new ResultSetTable(rs);
            add(view, BorderLayout.SOUTH);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
