package squui.gui.connection;

import javax.swing.ImageIcon;

import squui.gui.ContentPane;
import squui.gui.Module;

public class ConnectionManagerModule implements Module{
    
    @Override
    public ContentPane constructGui() {
        return new ConnectionPane();
    }

    @Override
    public ImageIcon getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "Connection Manager";
    }

}
