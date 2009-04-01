package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.IDS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Snort extends IDS 
{
	@SuppressWarnings("unchecked")
	public Snort(String IDSpath, String IDSversion, ArrayList input, ArrayList output)
	{
		path = IDSpath;
		type = "network";
		name = "Snort";
		inputFormat = input;
		outputFormat = output;
		version = IDSversion;
		idsParadigm = "misuse";
		ruleManager = new SnortRM();
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
		//	Creazione di un vettore di String a partire dalla CL presente nell'attributo dell'IDS
System.out.println("La command line che eseguirà l'IDS è: "+this.commandLineAnalysis.getTextualCL());
		String[] cmd;
		int token_number = 0;
		StringTokenizer strToken = new StringTokenizer(this.commandLineAnalysis.getTextualCL());

		while(strToken.hasMoreTokens())
		{
			strToken.nextToken();
			token_number++;
		}
		cmd = new String[token_number];
		int k = 0;
		strToken = new StringTokenizer(this.commandLineAnalysis.getTextualCL());
		while(strToken.hasMoreTokens())
		{
			cmd[k] = strToken.nextToken();
			k++;
		}
		
		//	Lancio dell'attività di analisi
		try
        {            
		//	String cmd = this.commandLine.getTextualCL();
			Process analisysProgess;
			analisysProgess = Runtime.getRuntime().exec(cmd);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(analisysProgess.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) 
		    {
		        System.out.println(line);
		    }
		      input.close();
		   
		      
			System.out.println("lanciato...");
        } catch (Throwable t)
          {
        	System.out.println("ERRORE");
            t.printStackTrace();
          }
	}
	
	/*public void configureIDS()
	{
		try
        {            
			String[] cmd = {"snort", "-A", "fast", "-k", "ascii", "-l", "/var/log/snort", "-i", "any"};
			Runtime.getRuntime().exec(cmd);
			System.out.println("lanciato...");
        } catch (Throwable t)
          {
        	System.out.println("ERRORE");
            t.printStackTrace();
          }
	}*/
	
	/*public static void main(String[] args)
	{
		System.out.println("Provo a lanciare snort...");
		Snort snort = new Snort();
		snort.configureIDS();
		System.out.println("Allegria....");
	}*/
	
}
