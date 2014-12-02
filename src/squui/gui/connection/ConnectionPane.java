package squui.gui.connection;

import java.awt.BorderLayout;

import squui.gui.ContentPane;

public class ConnectionPane extends ContentPane{
    
    private SchemaBrowser schemaBrowser;
    
    public ConnectionPane() {
        schemaBrowser = new SchemaBrowser();
        setLayout(new BorderLayout());
        add(schemaBrowser, BorderLayout.CENTER);
    }

}
