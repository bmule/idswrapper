package it.polimi.elet.vplab.idswrapper.concrete;

import java.util.ArrayList;

@SuppressWarnings({"unchecked"})
public class TagListTraining 
{
	ArrayList commandLineTagList;
	ArrayList commandLineTagSubListOptions;
	//	commandLineTagSubListPackets è un ArrayList che è costituito da elementi di tipo packet
	//	gli elementi di tipo packet sono a loro volta ArrayList di 2 elementi. Un elemento è un oggetto di tipo
	//	packetInfo (ArrayList di stringhe di 2 posizioni), l'altro è una stringa semplice ed indica il path 
	//	dell'i-esimo file di pacchetti
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
		
		//	Inizializzo commandLineTagList: inserisco stringhe e vettori vuoti: poi li modificherò
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
		//	Struttura dati vuota...
		subListStruct1 = new ArrayList();
		subListStruct1.add("");
		subListStruct1.add("");
		subListStruct2 = new ArrayList();
		subListStruct2.add("");
		subListStruct2.add("");
		subListStruct3 = new ArrayList();
		subListStruct3.add("");
		subListStruct3.add("");
		
		if(position == 1)
		{
				if(data.equals("restart_training"))
					((ArrayList)(commandLineTagList.get(1))).set(0,data.toString());
				else if(data.equals("save_redundant_data"))
					((ArrayList)(commandLineTagList.get(1))).set(1,data.toString());
				else if(data.getClass().getName().equals("java.util.ArrayList"))
				{
					if(((ArrayList)data).get(0).equals("packet_buffer_dimension"))
						((ArrayList)(commandLineTagList.get(1))).set(2,data);
					else if(((ArrayList)data).get(0).equals("save_frequency"))
						((ArrayList)(commandLineTagList.get(1))).set(3,data);
					else if(((ArrayList)data).get(0).equals("random_seed"))
						((ArrayList)(commandLineTagList.get(1))).set(4,data);
					else if(((ArrayList)data).get(0).equals("clear_packet_buffer_dimension"))
						((ArrayList)(commandLineTagList.get(1))).set(2,subListStruct1);
					else if(((ArrayList)data).get(0).equals("clear_save_frequency"))
						((ArrayList)(commandLineTagList.get(1))).set(3,subListStruct2);
					else if(((ArrayList)data).get(0).equals("clear_random_seed"))
						((ArrayList)(commandLineTagList.get(1))).set(4,subListStruct3);
				}
				else if(data.equals("random_sequential"))
					((ArrayList)(commandLineTagList.get(1))).set(5,data.toString());
				else if(data.equals("random_pseudocasual"))
					((ArrayList)(commandLineTagList.get(1))).set(6,data.toString());
				else if(data.equals("clear_restart_training"))
					((ArrayList)(commandLineTagList.get(1))).set(0,"");
				else if(data.equals("clear_save_redundant_data"))
					((ArrayList)(commandLineTagList.get(1))).set(1,"");
				else if(data.equals("clear_random_sequential"))
					((ArrayList)(commandLineTagList.get(1))).set(5,"");
				else if(data.equals("clear_random_pseudocasual"))
					((ArrayList)(commandLineTagList.get(1))).set(6,"");
		}
		else
		{
			commandLineTagList.set(position, data);
		}
		
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
