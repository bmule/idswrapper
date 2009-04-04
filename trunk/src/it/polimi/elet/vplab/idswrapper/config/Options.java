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
/* Some of this code has been taken from the StatCVS project */

package it.polimi.elet.vplab.idswrapper.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for storing all command line parameters.
 * @author $Author$
 * @version $Id$
 */
public class Options {

    private static final String LOGGING_CONFIG_DEFAULT = "logging.properties";
    private static final String LOGGING_CONFIG_VERBOSE = "logging-verbose.properties";
    private static final String LOGGING_CONFIG_DEBUG = "logging-debug.properties";

    private static String logFileName = null;
    private static String projectName = null;
    private static String loggingProperties = LOGGING_CONFIG_DEFAULT;

    private static String charSet = "ISO-8859-1";
	
	private static Properties properties = new Properties();

    public static String getCharSet()
	{
        return charSet;
    }

    public static void setCharSet(final String cs)
	{
        charSet = cs;
    }

    /**
     * Method getProjectName.
     * @return String name of the project
     */
    public static String getProjectName()
	{
        return projectName;
    }

    /**
     * Method getLogfilename.
     * @return String name of the logfile to be parsed
     */
    public static String getLogFileName()
	{
        return logFileName;
    }

    /**
     * Sets the logFileName.
     * @param logFileName The logFileName to set
     * @throws ConfigurationException if the file does not exist
     */
    public static void setLogFileName(final String logFileName)
		throws ConfigurationException
	{
        
		final File inputFile = new File(logFileName);
        if (!inputFile.exists())
            throw new ConfigurationException("Specified logfile not found: " +
				logFileName);
        Options.logFileName = logFileName;
    }

    /**
     * Sets a project title to be used in the reports
     * @param projectName The project title to be used in the reports
     */
    public static void setProjectName(final String projectName)
	{
        Options.projectName = projectName;
    }

    /**
     * Gets the name of the logging properties file
     * @return the name of the logging properties file
     */
    public static String getLoggingProperties()
	{
        return loggingProperties;
    }

    /**
     * Sets the logging level to verbose
     */
    public static void setVerboseLogging() 
	{
        Options.loggingProperties = LOGGING_CONFIG_VERBOSE;
    }

    /**
     * Sets the logging level to debug
     */
    public static void setDebugLogging()
	{
        Options.loggingProperties = LOGGING_CONFIG_DEBUG;
    }

    /**
     * Set the config file that may contain user details.
     * @param propertiesFilename
     */
    public static void setConfigFile(final String propertiesFilename)
		throws ConfigurationException
	{
        if (propertiesFilename != null) {
            InputStream is = null;
            try {
                is = new FileInputStream(propertiesFilename);
                properties.load(is);
            } catch (final FileNotFoundException e) {
                throw new ConfigurationException("Unable to find the configuration file " +
					propertiesFilename);
            } catch (final IOException e) {
                throw new ConfigurationException("Problem reading the configuration file " +
					propertiesFilename);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (final IOException e) {
                        throw new ConfigurationException("Problem closing stream to the" +
							" configuration file " + propertiesFilename);
                    }
                }
            }
        }
    }

    /**
     * The config properties.
     * @return
     */
    public static Properties getConfigProperties()
	{
        return properties;
    }

    /**
     * Return a String prop.
     * @param propName
     * @param defaultValue
     * @return
     */
    public static String getConfigStringProperty(final String propName,
		final String defaultValue)
	{
        if (properties != null)
            return properties.getProperty(propName, defaultValue);

        return defaultValue;
    }

    public static String getConfigStringProperty(final String propName,
		final String fallBackPropName, final String defaultValue)
	{
        if (properties != null) {
            final String val = properties.getProperty(propName);

            if (val != null)
                return val;
            else
                return properties.getProperty(fallBackPropName, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Return a Integer prop.
     * @param propName
     * @param defaultValue
     * @return
     */
    public static Integer getConfigIntegerProperty(final String propName,
		final Integer defaultValue)
	{
        if (properties != null) {
            final String val = properties.getProperty(propName);
            if (val != null) {
                try {
                    return Integer.valueOf(val);
                } catch (final NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /**
     * Return a Float prop.
     * @param propName
     * @param defaultValue
     * @return
     */
    public static Float getConfigIntegerProperty(final String propName,
		final Float defaultValue)
	{
        if (properties != null) {
            final String val = properties.getProperty(propName);
            if (val != null) {
                try {
                    return Float.valueOf(val);
                } catch (final NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /**
     * Return a Integer prop.
     * @param propName
     * @param defaultValue
     * @return
     */
    public static Integer getConfigIntegerProperty(final String propName,
		final String fallBackPropName, final Integer defaultValue)
	{
        if (properties != null) {
            final String val = properties.getProperty(propName);
            if (val != null) {
                try {
                    return Integer.valueOf(val);
                } catch (final NumberFormatException e) {
                    return defaultValue;
                }
            } else {
                return getConfigIntegerProperty(fallBackPropName, defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * Return a Float prop.
     * @param propName
     * @param defaultValue
     * @return
     */
    public static Float getConfigFloatProperty(final String propName,
		final String fallBackPropName, final Float defaultValue)
	{
        if (properties != null) {
            final String val = properties.getProperty(propName);
            if (val != null) {
                try {
                    return Float.valueOf(val);
                } catch (final NumberFormatException e) {
                    return defaultValue;
                }
            } else
                return getConfigIntegerProperty(fallBackPropName, defaultValue);
        }

        return defaultValue;
    }

    /**
     * Return a Boolean prop.
     * @param propName
     * @param defaultValue
     * @return
     */
    public static Boolean getConfigBooleanProperty(final String propName,
		final String fallBackPropName, final Boolean defaultValue)
	{
        if (properties != null) {
            String val = properties.getProperty(propName);
            if (val != null) {
                try {
                    return Boolean.valueOf(val);
                } catch (final NumberFormatException e) {
                    return defaultValue;
                }
            } else {
                val = properties.getProperty(fallBackPropName);
                if (val != null)
                    return Boolean.valueOf(val);
            }
        }
        return defaultValue;
    }
}
