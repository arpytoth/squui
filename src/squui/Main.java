package squui;

import javax.swing.UnsupportedLookAndFeelException;

import squui.gui.MainFrame;
import squui.gui.connection.ConnectionPane;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        MainFrame.get().setVisible(true);
        MainFrame.get().addTab("Ceva", new ConnectionPane());
    }
}
