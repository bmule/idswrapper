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
