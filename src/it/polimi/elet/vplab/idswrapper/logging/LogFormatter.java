/*
 * $Id$
 *
 * $Revision$
 *
 * $Date$
 * 
 * IDSWrapper - An extendable wrapping interface to manage, run your IDS and to
 * evaluate its performances.
 *
 * Copyright (C) 2009 Davide Polino, Paolo Rigoldi, Federico Maggi. 
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
