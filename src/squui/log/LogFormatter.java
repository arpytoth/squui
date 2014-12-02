package squui.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

    /** log header format e.g. [2010-09-24 17:23:12, 31ms] */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "[yyyy-MM-dd HH:mm:ss, S'ms']");

    private final Date recordDate = new Date();

    /**
     * Format the log message This method is called for every log records
     * 
     * @return the formated message
     */
    public String format(LogRecord record) {
        StringBuffer buf = new StringBuffer(1000);
        String separator = System.getProperty("line.separator");

        // Date
        recordDate.setTime(record.getMillis());
        buf.append(dateFormat.format(recordDate));
        buf.append(separator);

        // Level: Source
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }

        buf.append(record.getLevel().getLocalizedName());
        buf.append(": ");
        buf.append(source);
        buf.append(separator);

        // Message
        buf.append(formatMessage(record));

        // Throw-able
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();

            buf.append(sw.toString());
        } else {
            buf.append(separator);
        }

        buf.append(separator);

        return buf.toString();
    }
}
