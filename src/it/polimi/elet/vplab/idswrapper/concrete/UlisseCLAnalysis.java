package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.CommandLineAnalysis;

import java.util.ArrayList;

public class UlisseCLAnalysis
	extends CommandLineAnalysis
{
	ArrayList<String> CLFeatures;
	
	public UlisseCLAnalysis()
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
		
		clTokens.add("snort");
		
		//	Controllo che vi siano delle opzioni sulle modalità di report dei risultati in posizione 1
		ArrayList reportModeArray = (ArrayList)(commandLineTagList.get(1));
		if( reportModeArray.size() != 0)
		{
			for(int i=0; i<reportModeArray.size(); i++)
			{
				if(reportModeArray.get(i).equals("dump_app_layer"))
					clTokens.add(" -d");
				else if(reportModeArray.get(i).equals("show_second_layer"))
					clTokens.add(" -e");
				else if(reportModeArray.get(i).equals("verbose_info"))
					clTokens.add(" -v");
				
				//	AGGIUNGERE QUI EVENTUALI ALTRE MODALITÀ DI VISUALIZZAZIONE PER SNORT	
			}
		}
		
		//	Inserisco nella CL il riferimento al logfile (se presente)
		if(!commandLineTagList.get(2).equals(""))
			clTokens.add(" -l "+ commandLineTagList.get(2));
		
		//	Controllo che vi siano delle opzioni sulle modalità di alert in posizione 3
		ArrayList alertList = (ArrayList)commandLineTagList.get(3);
		String alertModeArray = (String)alertList.get(0);
		if(!alertModeArray.equals(""))
		{
			if(alertModeArray.equals("full"))
				clTokens.add(" -A full");
			else if(alertModeArray.equals("fast"))
				clTokens.add(" -A fast");
			else if(alertModeArray.equals("unsock"))
				clTokens.add(" -A unsock");
			else if(alertModeArray.equals("console"))
				clTokens.add(" -A console");
			else if(alertModeArray.equals("cmg"))
				clTokens.add(" -A cmg");
			else if(alertModeArray.equals("no_alert"))
				clTokens.add(" -A none");
				
			//	AGGIUNGERE QUI EVENTUALI ALTRE MODALITÀ DI ALERT PER SNORT	
		}
		if(((String)alertList.get(1)).equals("binary_format_log"))
			clTokens.add(" -b");
		if(((String)alertList.get(2)).equals("msg_to_syslog"))
			clTokens.add(" -s");
		if(((String)alertList.get(3)).equals("no_log"))
			clTokens.add(" -N");
		
		//	Inserisco nella CL la specifica della classe di pacchetti da analizzare (se specificata)
		if(!commandLineTagList.get(4).equals(""))
			clTokens.add(" -h "+ commandLineTagList.get(4));
		
		//	Inserisco nella CL la posizione del file delle regole
		if(!commandLineTagList.get(5).equals(""))
			clTokens.add(" -c "+ commandLineTagList.get(5));
			
		//	Inserisco nella CL la posizione del file tcpdump da analizzare (se specificata)
			if(!commandLineTagList.get(6).equals(""))
				clTokens.add(" -r "+ commandLineTagList.get(6));
		
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
		//	Questo ArrayList di String contiene i tag corrispondenti alle funzionalità di snort, che verranno
		//	controllate dall'interfaccia grafica per abilitare o meno i checkbox per la formazione della CommandLine
		//	se un IDS non dovesse avere certe funzionalità, queste risultarebbero non selezionabili a livello di
		//	interfaccia
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
	
	
/*	//	questo metodo attiva l'attività di analisi		NO, VA NELL'IDS, NON QUI
	public void analize()
	{
		
	}/*
	
	/*public String analize(String alertMode, String configurationFile, String displayMode, String logFile, String networkInterface, String tcpdumpFile)
	{
		
		return("");
	}*/
