package squui.gui;

import javax.swing.ImageIcon;

/**
 * All modules (tabs) must implement this interface. This will be used by
 * the main frame to create menu items and to handle on action executed 
 * event by calling the constructGui(). Basically the constructGui() method
 * will handle the creation of the GUI component for a specified module. All
 * these modules are stored inside the Modules class.
 */
public interface Module { 
    public String getName();
    public ImageIcon getIcon();
    public ContentPane constructGui();
}
