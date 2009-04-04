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

import it.polimi.elet.vplab.idswrapper.ids.CommandLineAnalysis;

import java.util.ArrayList;

public class SnortCLAnalysis
	extends CommandLineAnalysis
{
	ArrayList<String> CLFeatures;

	public SnortCLAnalysis()
	{
		relatedIDS = "Snort";
		clTokens = new ArrayList<String>();
		initializeCLFeature();

	}

	//	This method translate the generic tags of the commandLineTagList into
	//	snort parameters 
	@SuppressWarnings("unchecked")
		public void updateCommandLine(ArrayList commandLineTagList)
		{
			clTokens.clear();
			clTokens.add("snort");

			//	Control of the options about modalities of report (position 1)
			ArrayList reportModeArray = (ArrayList)(commandLineTagList.get(1));
			if (reportModeArray.size() != 0)
				for (int i = 0; i < reportModeArray.size(); i++) {
					if(reportModeArray.get(i).equals("dump_app_layer"))
						clTokens.add(" -d");
					else if(reportModeArray.get(i).equals("show_second_layer"))
						clTokens.add(" -e");
					else if(reportModeArray.get(i).equals("verbose_info"))
						clTokens.add(" -v");

					//TO ADD HERE FURTHER VISUALIZATION MODES FOR SNORT
				}

			//	Add into CL the reference to logfile (if present)
			if (!commandLineTagList.get(2).equals(""))
				clTokens.add(" -l "+ commandLineTagList.get(2));

			//	Control of the options about modalities of alert (position 3)
			ArrayList alertList = (ArrayList)commandLineTagList.get(3);
			String alertModeArray = (String)alertList.get(0);
			if (!alertModeArray.equals("")) {
				if (alertModeArray.equals("full"))
					clTokens.add(" -A full");
				else if (alertModeArray.equals("fast"))
					clTokens.add(" -A fast");
				else if (alertModeArray.equals("unsock"))
					clTokens.add(" -A unsock");
				else if (alertModeArray.equals("console"))
					clTokens.add(" -A console");
				else if (alertModeArray.equals("cmg"))
					clTokens.add(" -A cmg");
				else if (alertModeArray.equals("no_alert"))
					clTokens.add(" -A none");

				//	TO ADD HERE FURTHER ALERT MODES FOR SNORT
			}

			if (((String)alertList.get(1)).equals("binary_format_log"))
				clTokens.add(" -b");
			if (((String)alertList.get(2)).equals("msg_to_syslog"))
				clTokens.add(" -s");
			if (((String)alertList.get(3)).equals("no_log"))
				clTokens.add(" -N");

			//	Insert into CL the specific of the class of packets to analyze
			if (!commandLineTagList.get(4).equals(""))
				clTokens.add(" -h "+ commandLineTagList.get(4));

			//	Insert into CL the path of the rules file
			if (!commandLineTagList.get(5).equals(""))
				clTokens.add(" -c "+ commandLineTagList.get(5));

			//	Insert into CL the path of the tcpdump file to analyze
			if(!commandLineTagList.get(6).equals(""))
				clTokens.add(" -r "+ commandLineTagList.get(6));

		}

	public String getTextualCL()
	{
		String strOut = "";

		for (int i = 0; i < clTokens.size(); i++)
			strOut = strOut+clTokens.get(i);

		return strOut;
	}

	public ArrayList<String> getCLFeatures()
	{
		return CLFeatures;
	}

	private void initializeCLFeature()
	{
		/*
		 *	This ArrayList<String> contains the tags linked with the snort's
		 *	features.  The GUI will check the elements of the array to enable
		 *	the checkbox that are used to create the CommandLine for the
		 *	specific IDS
		 */

		CLFeatures = new ArrayList<String>();

		CLFeatures.add("dump_app_layer");
		CLFeatures.add("show_second_layer");
		CLFeatures.add("verbose_info");
		CLFeatures.add("full");
		CLFeatures.add("fast");
		CLFeatures.add("unsock");
		CLFeatures.add("console");
		CLFeatures.add("cmg");
		CLFeatures.add("no_alert");
		CLFeatures.add("binary_format_log");
		CLFeatures.add("msg_to_syslog");
		CLFeatures.add("no_log");
	}

}
