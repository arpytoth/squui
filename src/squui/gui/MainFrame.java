package squui.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import squui.gui.connection.ConnSettings;
import squui.gui.connection.ConnectionTab;

/**
 * The main window of the SquuI application. There is only one window so this
 * is a singleton, mostly to avoid passing as parameter crap.
 */
public class MainFrame extends JFrame implements  ActionListener{

    /** Not used at all, this is added because of annoying java warnings.*/
    private static final long serialVersionUID = 1L;


	public ClosableTabbedPane tabsPane;	
	
	/** The main frame instance*/
	public static MainFrame instance;
	
	/**
	 * Return the main frame instance.
	 * @return
	 */
	public static MainFrame get() {
	    if (instance == null)
	        instance = new MainFrame();
	    return instance;
	}
	
	/**
	 * Use get() to get the instance of the main frame.
	 */
	private MainFrame(){
		setTitle("SquuI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		tabsPane = new ClosableTabbedPane();
		add(tabsPane, BorderLayout.CENTER);
		
	
		setSize(800, 600);
		setLocationRelativeTo(null);
	}
	
	public void openConnection(ConnSettings settings) {
	    try {
	        ConnectionTab tab = new ConnectionTab(settings);  
	        addTab(settings.name, tab);
	    } catch (Throwable t) {
	        JOptionPane.showConfirmDialog(this, t.getMessage(), "Error",
	            JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
	    }
	  
	}
	
	/**
	 * Open panel in a new tab. 
	 */
	public void addTab(String title, JPanel panel) {
	    tabsPane.addTab(title, panel);
	    tabsPane.setSelectedIndex(tabsPane.getTabCount() - 1);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
