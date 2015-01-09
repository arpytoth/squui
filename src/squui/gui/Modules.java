package squui.gui;

import java.util.ArrayList;

/**
 * A list of all modules in the GUI application. When you add a new module
 * you must make a reference here. The GUI engine will load the information
 * regarding the modules from here and will construct popup menus and so
 * on.
 */
public class Modules {
    
    private static ArrayList<Module> modules;
    private static Module defaultModule;
    
    
    public static ArrayList<Module> getModules() {
        if (modules == null) {
            modules = new ArrayList<Module>();
            modules.add(defaultModule);
        }
        return modules;
    }

    public static Module getDefault() {
        return defaultModule;
    }
    
    public static Module getModule(String name) {
        ArrayList<Module> modules = getModules();
        for (Module m : modules) {
            if (m.getName().equals(name)) 
                return m;
        }
        return null;
    }
}
