package it.polimi.elet.vplab.idswrapper.ids;

import java.util.ArrayList;

public abstract class CommandLineTraining 
{
	protected String relatedIDS;
	protected ArrayList<String> clTokens;
	
	
	//	Questo metodo genera una commandline che attivi la modalità di addestramento dell'IDS
	public void train(ArrayList<String> par)
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
