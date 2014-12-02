package squui.gui;

import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconLoader {
    
    private static HashMap<String, ImageIcon> iconMap = 
        new HashMap<String, ImageIcon>();
    
    
    public static ImageIcon loadIconGif(String path) {
        try {
            
            URL url = ClassLoader.getSystemResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                return icon;
            } else {
                ImageIcon icon = new ImageIcon(path);
                return icon;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
    
    public static ImageIcon getIcon(String path) {
        if (iconMap.containsKey(path)) 
            return iconMap.get(path);
        ImageIcon icon = iconMap.get(path);
        if (icon == null) {
            try {
                icon = new ImageIcon(ImageIO.read(ClassLoader
                        .getSystemResource(path)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            iconMap.put(path, icon);
        }
        return icon;
    }
    
    public static ImageIcon loadIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(ClassLoader
                    .getSystemResource(path)));
            return icon;
        } catch (Exception e) {
        }
        return null;
    }

}
