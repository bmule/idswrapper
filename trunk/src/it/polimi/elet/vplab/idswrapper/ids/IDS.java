package it.polimi.elet.vplab.idswrapper.ids;

import java.util.ArrayList;

/*
 * Classe astratta che rappresenta l'astrazione di un IDS. 
 */


public abstract class IDS 
{
	/*
	 * Metodi che rappresentano gli attributi di un IDS generico
	 */
	protected String path;
	protected String type;
	protected String name;
	protected ArrayList<String> inputFormat; 
	protected ArrayList<String> outputFormat;
	protected CommandLineAnalysis commandLineAnalysis;
	protected CommandLineTraining commandLineTraining;
	protected String version;
	protected String idsParadigm;
	protected RulesManager ruleManager;
	//	Attributo legato all'interfaccia di testing specifica per ogni IDS
	protected IDSTestingInterface testingInterface;
	
	//	getters and setters

	public String getVersion() 
	{
		return version;
	}

	public void setVersion(String version) 
	{
		this.version = version;
	}

	public String getPath() 
	{
		return path;
	}

	public void setPath(String path) 
	{
		this.path = path;
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public ArrayList<String> getInputFormat() 
	{
		return inputFormat;
	}

	public void setInputFormat(ArrayList<String> inputFormat) 
	{
		this.inputFormat = inputFormat;
	}

	public ArrayList<String> getOutputFormat() 
	{
		return outputFormat;
	}

	public void setOutputFormat(ArrayList<String> outputFormat) 
	{
		this.outputFormat = outputFormat;
	}
	
	public String getIdsParadigm() 
	{
		return idsParadigm;
	}

	public void setIdsParadigm(String idsParadigm) 
	{
		this.idsParadigm = idsParadigm;
	}

	//	Metodi per la configurazione e l'utilizzo di un IDS generico.
	public void configureIDS()
	{
		
	}
	
	public void trainIDS()
	{
		
	}
	
	public void analizeTraffic()
	{
		
	}
	
	//	??
	public void newCommandLineAnalysis()
	{
		
	}
	
	public void newCommandLineTraining()
	{
		
	}
	
	public CommandLineAnalysis getAnalysisCommandLine()
	{
		return this.commandLineAnalysis;
	}
	
	public CommandLineTraining getTrainingCommandLine()
	{
		return this.commandLineTraining;
	}
	
	public RulesManager getRulesManager()
	{
		return this.ruleManager;
	}
	
	public void changeTrainSettings(String str1, String str2, String str3, String str4)
	{
		
	}
	
	public IDSTestingInterface getTestingInterface(String str)
	{
		return testingInterface;
	}
	
}
