package squui.log;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Logger utility. All logging operations from the must be done using this
 * utility.
 */
public class Log {

    /** Private custom logger instance used by the Log utility. */
    private static Logger logger = Logger.getLogger("ptools");

    static {
        LogManager.getLogManager().reset();

        try {
            FileHandler fh = null;
            fh = new FileHandler("ptool.log", true);
            fh.setFormatter(new LogFormatter());
            logger.addHandler(fh);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Convenient method to log an exception the the default logger. Exception
     * will be logged with {@link Level#SEVERE} logging level.
     * 
     * @param message
     *            - description of the error
     * @param throwable
     *            - the actual exception to log.
     */
    public static final void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * 
     * Convenient method to log an exception the the default logger. Exception
     * will be logged with {@link Level#SEVERE} logging level.
     * 
     * @param throwable
     *            - the actual exception to log.
     */
    public static final void error(Throwable throwable) {
        error("Exception:", throwable);
    }

    public static final void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    public static final void warning(String message) {
        logger.log(Level.WARNING, message);
    }
}
