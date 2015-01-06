package squui;

import javax.swing.UnsupportedLookAndFeelException;

import squui.gui.MainFrame;
import squui.gui.Settings;
import squui.gui.home.HomePanel;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        Settings.get().load();
        MainFrame.get().setVisible(true);
        MainFrame.get().addTab("Home", new HomePanel());
        Settings.get().save();
    }
}
