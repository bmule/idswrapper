package it.polimi.elet.vplab.idswrapper.ids;

import java.util.ArrayList;

public abstract class CommandLineAnalysis 
{
	protected String relatedIDS;
	protected ArrayList<String> clTokens;
	
	//	Questo metodo genera una commandline che esegua l'analisi del traffico
	public void analize(ArrayList<String> par)
	{
		
	}
	//	Questo metodo genera una commandline volta a configurare l'IDS associato
	public void configure(ArrayList<String> par)
	{
		
	}
	
	//	Questo metodo genera una commandline che attivi la modalità di addestramento dell'IDS
	public void train(ArrayList<String> par)
	{
		
	}
	
	//	Questo metodo genera una commandline che disattiva l'IDS
	public void stopIDS(ArrayList<String> par)
	{
		
	}
	
	public String getTextualCL()
	{
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public void updateCommandLine(ArrayList commandLineTagList)
	{
		
	}
	
	public ArrayList<String> getCLFeatures()
	{
		return new ArrayList<String>();
	}
	
}
