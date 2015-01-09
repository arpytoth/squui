package squui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import squui.gui.MainFrame;
import squui.gui.Settings;
import squui.gui.home.HomePanel;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {  
               
                Settings.get().load();
                MainFrame.get().setVisible(true);
                MainFrame.get().addTab("Home", HomePanel.get());
            }
        });

    }
}
