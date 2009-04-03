package it.polimi.elet.vplab.idswrapper.config;

/**
 * Thrown on missing/illegal configuration arguments.
 * 
 * @author $Author$
 * @version $Id$
 */
public class ConfigurationException
	extends Exception {

    /**
     * Constructor
     * @param message Error message
     */
    public ConfigurationException(final String message)
	{
        super(message);
    }
}
