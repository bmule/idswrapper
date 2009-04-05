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

import java.util.ArrayList;

@SuppressWarnings({"unchecked"})
public class TagListTraining 
{
	ArrayList commandLineTagList;
	ArrayList commandLineTagSubListOptions;
	
	/*	commandLineTagSubListPackets is an ArrayList object. It contains elements of type "packet".
	 * 	packet-type objects are ArrayList of two elements. A packet contains: 
	 * 	1) one element of packetInfo type. packetInfo are ArrayList<String> objects with two elements.
	 * 	2) a String element that contains the path of the #i file of packets.
	*/
	ArrayList commandLineTagSubListPackets;
	ArrayList packet;
	ArrayList<String> packetInfo;
	ArrayList subListStruct1;
	ArrayList subListStruct2;
	ArrayList subListStruct3;

	public ArrayList inizializeCommandLineTagList()
	{
		subListStruct1 = new ArrayList();
		subListStruct1.add("");
		subListStruct1.add("");
		subListStruct2 = new ArrayList();
		subListStruct2.add("");
		subListStruct2.add("");
		subListStruct3 = new ArrayList();
		subListStruct3.add("");
		subListStruct3.add("");

		//	commandLineTagList initialization
		commandLineTagSubListOptions = new ArrayList();
		commandLineTagSubListOptions.add("");
		commandLineTagSubListOptions.add("");
		commandLineTagSubListOptions.add(subListStruct1);
		commandLineTagSubListOptions.add(subListStruct2);
		commandLineTagSubListOptions.add(subListStruct3);
		commandLineTagSubListOptions.add("");
		commandLineTagSubListOptions.add("");

		commandLineTagSubListPackets = new ArrayList();

		commandLineTagList = new ArrayList();
		commandLineTagList.add("");
		commandLineTagList.add(commandLineTagSubListOptions);
		commandLineTagList.add("");
		commandLineTagList.add("");
		commandLineTagList.add(commandLineTagSubListPackets);

		return commandLineTagList;
	}

	public ArrayList updteCommandLineTagList(int position, Object data)
	{
		//	An empty data structure
		subListStruct1 = new ArrayList();
		subListStruct1.add("");
		subListStruct1.add("");
		subListStruct2 = new ArrayList();
		subListStruct2.add("");
		subListStruct2.add("");
		subListStruct3 = new ArrayList();
		subListStruct3.add("");
		subListStruct3.add("");

		if (position == 1) {
			if (data.equals("restart_training"))
				((ArrayList)(commandLineTagList.get(1))).set(0,data.toString());
			else if (data.equals("save_redundant_data"))
				((ArrayList)(commandLineTagList.get(1))).set(1,data.toString());
			else if (data.getClass().getName().equals("java.util.ArrayList")) {
				if (((ArrayList)data).get(0).equals("packet_buffer_dimension"))
					((ArrayList)(commandLineTagList.get(1))).set(2,data);
				else if (((ArrayList)data).get(0).equals("save_frequency"))
					((ArrayList)(commandLineTagList.get(1))).set(3,data);
				else if (((ArrayList)data).get(0).equals("random_seed"))
					((ArrayList)(commandLineTagList.get(1))).set(4,data);
				else if (((ArrayList)data).get(0).equals("clear_packet_buffer_dimension"))
					((ArrayList)(commandLineTagList.get(1))).set(2,subListStruct1);
				else if (((ArrayList)data).get(0).equals("clear_save_frequency"))
					((ArrayList)(commandLineTagList.get(1))).set(3,subListStruct2);
				else if (((ArrayList)data).get(0).equals("clear_random_seed"))
					((ArrayList)(commandLineTagList.get(1))).set(4,subListStruct3);
			}
			else if (data.equals("random_sequential"))
				((ArrayList)(commandLineTagList.get(1))).set(5,data.toString());
			else if (data.equals("random_pseudocasual"))
				((ArrayList)(commandLineTagList.get(1))).set(6,data.toString());
			else if (data.equals("clear_restart_training"))
				((ArrayList)(commandLineTagList.get(1))).set(0,"");
			else if (data.equals("clear_save_redundant_data"))
				((ArrayList)(commandLineTagList.get(1))).set(1,"");
			else if (data.equals("clear_random_sequential"))
				((ArrayList)(commandLineTagList.get(1))).set(5,"");
			else if (data.equals("clear_random_pseudocasual"))
				((ArrayList)(commandLineTagList.get(1))).set(6,"");
		} else
			commandLineTagList.set(position, data);

		return commandLineTagList;
	}

	public ArrayList clearAllPacketsData()
	{
		commandLineTagSubListPackets = new ArrayList();
		commandLineTagList.set(3, commandLineTagSubListPackets);

		return commandLineTagList;
	}

	public ArrayList getTagList()
	{
		return commandLineTagList;
	}
}
