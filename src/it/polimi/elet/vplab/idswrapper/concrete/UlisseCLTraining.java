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
	
	//	traduce i tag generici della commandLineTagList nei parametri usabili da Snort
	@SuppressWarnings("unchecked")
	public void updateCommandLine(ArrayList commandLineTagList)
	{
		clTokens.clear();
		
		if(commandLineTagList.get(0).equals("first_train"))
			clTokens.add("som_training");
		else if(commandLineTagList.get(0).equals("second_train"))
			clTokens.add("ss_training");
		//	Controllo che vi siano delle opzioni sulle modalità di report dei risultati in posizione 1
		ArrayList optionsModeArray = (ArrayList)(commandLineTagList.get(1));
		if(optionsModeArray.size() != 0)
		{
			if(optionsModeArray.get(0).equals("restart_training"))
				clTokens.add(" -r");
			if(optionsModeArray.get(1).equals("save_redundant_data"))
					clTokens.add(" -s");
			if(((ArrayList)(optionsModeArray.get(2))).get(0).equals("packet_buffer_dimension"))
				clTokens.add(" -b"+((ArrayList)(optionsModeArray.get(2))).get(1));
			if(((ArrayList)(optionsModeArray.get(3))).get(0).equals("save_frequency"))
				clTokens.add(" -k"+((ArrayList)(optionsModeArray.get(3))).get(1));
			if(((ArrayList)(optionsModeArray.get(4))).get(0).equals("random_seed"))
				clTokens.add(" -d"+((ArrayList)(optionsModeArray.get(4))).get(1));
			if(optionsModeArray.get(5).equals("random_sequential"))
				clTokens.add(" --sequential");
			if(optionsModeArray.get(6).equals("random_pseudocasual"))
				clTokens.add(" --pseudocasual");	
			
			//	AGGIUNGERE QUI EVENTUALI ALTRE OPZIONI PER IL TRAINING DI ULISSE	
		}
		
		//	Percorso del file di log
		if(!commandLineTagList.get(2).equals(""))
			clTokens.add(" -l "+ commandLineTagList.get(2));
		
		//	Percorso del file XML di configurazione
		if(!commandLineTagList.get(3).equals(""))
			clTokens.add(" "+ commandLineTagList.get(3));
		
		ArrayList packetStruct = new ArrayList();
		ArrayList packet;
		ArrayList packetInfo;
		
		if(commandLineTagList.get(4)!=null)
		{
			packetStruct = (ArrayList) commandLineTagList.get(4);
			for(int i = 0; i < packetStruct.size(); i++)
			{
				packet = (ArrayList) packetStruct.get(i);
				packetInfo = (ArrayList) packet.get(0);
				if(!packetInfo.get(0).equals(""))
					clTokens.add(" -n"+ packetInfo.get(0));
				if(!packetInfo.get(1).equals(""))
					clTokens.add(" -o"+ packetInfo.get(1));
				clTokens.add(" "+ packet.get(1));
			}
		}
		
	}
	
	//	Restituisce la stringa testuale della command line
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
		//	Questo ArrayList di String contiene i tag corrispondenti alle funzionalità di addestramento di ulisse
		//	che verranno
		//	controllate dall'interfaccia grafica per abilitare o meno i checkbox per la formazione della CommandLine
		//	se un IDS non dovesse avere certe funzionalità, queste risultarebbero non selezionabili a livello di
		//	interfaccia
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
