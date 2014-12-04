package squui.gui.table;

import java.util.ArrayList;

/**
 * Implement this if you want to listen for events in the {@link ResultPane}
 */
public interface ResultPaneListener {

    /**
     * Called when user presses the apply button on the {@link ResultPane}
     * 
     * @param sql
     *            list of SQL commands resulted following the apply action.
     */
    public void apply(ArrayList<String> sql);

}
