package it.polimi.elet.vplab.idswrapper;

import it.polimi.elet.vplab.idswrapper.concrete.IDSWrapper;

/**
 * Main class.
 *
 * @author Federico Maggi
 * */
public class Main
{
	/**
	 * Launches the IDSWrapper GUI.
	 *
	 * @param args Command line arguments are ignored.
	 */
	public static void main(String[] args)
	{
		(new IDSWrapper()).launchGui();
	}
}
