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
package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.IDS;
import it.polimi.elet.vplab.idswrapper.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Snort
	extends IDS 
{
	@SuppressWarnings("unchecked")
	public Snort()
	{
		path = "";
		type = "network";
		name = "Snort";
		inputFormat = new ArrayList();
			inputFormat.add("pcap");
		outputFormat = new ArrayList();
			outputFormat.add("IDMEF");
		version = "2.3.2";
		idsParadigm = "misuse";
		ruleManager = new SnortRM();
	}
	
	public void setPath(String IDSpath)
	{
		path = IDSpath;
	}

	public SnortRM RuleManager()
	{
		return (SnortRM)ruleManager;
	}

	public SnortCLAnalysis CommandLineAnalysis() 
	{
		return (SnortCLAnalysis)commandLineAnalysis;
	}

	public void newCommandLineAnalysis() 
	{
		this.commandLineAnalysis = new SnortCLAnalysis();
	}

	public void analizeTraffic()
	{
		//	Create a String[] from the command line attribute
		Main.logger.info("Command line: " + this.commandLineAnalysis.getTextualCL());
		String[] cmd;
		int token_number = 0;
		StringTokenizer strToken = new StringTokenizer(
			this.commandLineAnalysis.getTextualCL());

		while (strToken.hasMoreTokens()) {
			strToken.nextToken();
			token_number++;
		}

		cmd = new String[token_number];
		int k = 0;
		strToken = new StringTokenizer(this.commandLineAnalysis.getTextualCL());

		while (strToken.hasMoreTokens()) {
			cmd[k] = strToken.nextToken();
			k++;
		}

		try {            
			Process analisysProgess;
			analisysProgess = Runtime.getRuntime().exec(cmd);

			BufferedReader input = new BufferedReader(new InputStreamReader(
				analisysProgess.getInputStream()));
			String line = "";

			while ((line = input.readLine()) != null)
				System.out.println(line);

			input.close();

			Main.logger.info("Launched");
		} catch (Throwable t) {
			Main.logger.severe(t.toString());
		}
	}

}
