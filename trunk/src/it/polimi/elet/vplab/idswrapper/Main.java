package it.polimi.elet.vplab.idswrapper;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.IOException;

import it.polimi.elet.vplab.idswrapper.concrete.IDSWrapper;
import it.polimi.elet.vplab.idswrapper.config.Options;

/**
 * Main class.
 *
 * @author $Author$
 * @version $Id$
 * */
public class Main
{
	private static Logger logger = Logger.getLogger("it.polimi.elet.vplab.idswrapper");
	private static LogManager lm = LogManager.getLogManager();

	/**
	 * Launches the IDSWrapper GUI.
	 *
	 * @param args Command line arguments are ignored.
	 */
	public static void main(String[] args)
	{
		initLogManager(Options.getLoggingProperties());
		(new IDSWrapper()).launchGui();
	}

	/**
	 * Init logging stuff.
	 *
	 * @param loggingProperties Logging Properties.
	 */
	private static void initLogManager(final String loggingProperties)
	{
		try {
			lm.readConfiguration(Main.class.getResourceAsStream(loggingProperties));
		} catch (final IOException e) {
			System.err.println("ERROR: Logging could not be initialized!");
		}
	}

}
