package it.polimi.elet.vplab.idswrapper.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Logging formatter for IDSWrapper.
 *
 * @author $Author$
 * @version $Id$
 */
public class LogFormatter
	extends Formatter {

    private final String lineSeparator = System.getProperty("line.separator");

    /**
     * @see java.util.logging.Formatter#format(LogRecord)
     */
    public String format(final LogRecord record) {
        final StringBuffer sb = new StringBuffer();
        if (record.getLevel().intValue() < Level.INFO.intValue()) {
            sb.append(record.getLevel().getLocalizedName());
            sb.append(" ");
            if (record.getSourceClassName() != null) {
                String className = record.getSourceClassName();
                className = className.substring(7);
                sb.append(className);
            } else {
                sb.append(record.getLoggerName());
            }
            if (record.getSourceMethodName() != null) {
                sb.append(" ");
                sb.append(record.getSourceMethodName());
            }
            sb.append("(): ");
        }

        final String message = formatMessage(record);
        sb.append(message);
        sb.append(lineSeparator);
        
		if (record.getThrown() != null) {
            try {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (final Exception ex) {
                System.err.println("Error formatting logmessage! " +
					ex.toString());
            }
        }

        return sb.toString();
    }

}
