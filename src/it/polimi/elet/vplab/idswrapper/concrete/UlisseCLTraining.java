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

import it.polimi.elet.vplab.idswrapper.ids.CommandLineTraining;

import java.util.ArrayList;

public class UlisseCLTraining
	extends CommandLineTraining
{
	ArrayList<String> CLFeatures;
	
	public UlisseCLTraining()
	{
		relatedIDS = "Ulisse";
		clTokens = new ArrayList<String>();
		initializeCLFeature();
	}
	
	//	Translate generic commandLineTagList tags into snort parameters
	
	@SuppressWarnings("unchecked")
	public void updateCommandLine(ArrayList commandLineTagList)
	{
		clTokens.clear();
		
		if (commandLineTagList.get(0).equals("first_train"))
			clTokens.add("som_training");
		else if (commandLineTagList.get(0).equals("second_train"))
			clTokens.add("ss_training");
		
		//	Check the options about REPORT mode (position 1)
		
		ArrayList optionsModeArray = (ArrayList)(commandLineTagList.get(1));

		if (optionsModeArray.size() != 0) {
			if (optionsModeArray.get(0).equals("restart_training"))
				clTokens.add(" -r");
			if (optionsModeArray.get(1).equals("save_redundant_data"))
					clTokens.add(" -s");
			if (((ArrayList)(optionsModeArray.get(2))).get(0).equals("packet_buffer_dimension"))
				clTokens.add(" -b"+((ArrayList)(optionsModeArray.get(2))).get(1));
			if (((ArrayList)(optionsModeArray.get(3))).get(0).equals("save_frequency"))
				clTokens.add(" -k"+((ArrayList)(optionsModeArray.get(3))).get(1));
			if (((ArrayList)(optionsModeArray.get(4))).get(0).equals("random_seed"))
				clTokens.add(" -d"+((ArrayList)(optionsModeArray.get(4))).get(1));
			if (optionsModeArray.get(5).equals("random_sequential"))
				clTokens.add(" --sequential");
			if (optionsModeArray.get(6).equals("random_pseudocasual"))
				clTokens.add(" --pseudocasual");	
			
			//	Add here other options about Ulisse training activity
		}
		
		//	Log file path
		if (!commandLineTagList.get(2).equals(""))
			clTokens.add(" -l "+ commandLineTagList.get(2));
		
		//	XML configuration file path
		if (!commandLineTagList.get(3).equals(""))
			clTokens.add(" "+ commandLineTagList.get(3));
		
		ArrayList packetStruct = new ArrayList();
		ArrayList packet;
		ArrayList packetInfo;
		
		if (commandLineTagList.get(4)!=null)
		{
			packetStruct = (ArrayList) commandLineTagList.get(4);
			for(int i = 0; i < packetStruct.size(); i++)
			{
				packet = (ArrayList) packetStruct.get(i);
				packetInfo = (ArrayList) packet.get(0);
				if (!packetInfo.get(0).equals(""))
					clTokens.add(" -n"+ packetInfo.get(0));
				if (!packetInfo.get(1).equals(""))
					clTokens.add(" -o"+ packetInfo.get(1));
				clTokens.add(" "+ packet.get(1));
			}
		}
		
	}
	
	public String getTextualCL()
	{
		String strOut = "";
		for(int i=0; i<clTokens.size(); i++)
		{
			strOut = strOut+clTokens.get(i);
		}
		
		return strOut;
	}
	
	public ArrayList<String> getCLFeatures()
	{
		return CLFeatures;
	}
	
	private void initializeCLFeature()
	{
		/*
		 * This ArrayList<String> contains some tags that correspond to the Ulisse's training features
		 * that will be controlled by the GUI.
		 * If an IDS does not have a specific feature, the corresponding checkbox in the GUI
		 * will not be selectable.
		 */
		
		CLFeatures = new ArrayList<String>();

		CLFeatures.add("restart_training");
		CLFeatures.add("use_log_file");
		CLFeatures.add("save_redundant_data");
		CLFeatures.add("packet_buffer_dimension");
		CLFeatures.add("save_frequency");
		CLFeatures.add("random_seed");
		CLFeatures.add("random_sequential");
		CLFeatures.add("random_pseudocasual");
		CLFeatures.add("number_of_packets_to_use");
		CLFeatures.add("number_of_packets_to_skip");
		CLFeatures.add("xml_settings_file");
		CLFeatures.add("network_address");
		CLFeatures.add("network_netmask");
		CLFeatures.add("xml_basename");
		CLFeatures.add("testing_capability");
		
	}
	
}
