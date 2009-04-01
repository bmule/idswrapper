package it.polimi.elet.vplab.idswrapper.filemanager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class FileManager 
{
	
	ArrayList IDSProperty = new ArrayList();
	
	public ArrayList readIDSFile()
    {
		FileReader reader;
		try 
		{
			reader = new FileReader("IDS.txt");
		} 
		catch (FileNotFoundException e) 
		{
			//	in caso di errore sarà vuoto e l'interfaccia grafica segnalerà l'errore
			return IDSProperty;
		}
		Scanner in = new Scanner(reader);
		while(in.hasNextLine())
		{   
			String line = in.nextLine();
			ArrayList handledLine = (ArrayList) handleString(line);
			if(!(handledLine.size() == 0))
			{

//System.out.println("Dimensione della linea: "+handledLine.size());
//for(int k=0;k<handledLine.size();k++)
	//System.out.println("ciclo di prova..."+handledLine.get(k));
				IDSProperty.add(handledLine);
			}
			//IDSProperty.add(line);
		}
		
		return IDSProperty;
	}
	
	private ArrayList handleString(String line)
	{
	ArrayList tokenList = new ArrayList();
        
    StringTokenizer st = new StringTokenizer(line);
    boolean typeListOpen = false;
    @SuppressWarnings("unused")
	boolean typeListEmpty = true;

    while (st.hasMoreTokens()) 
    	{
    		String token = st.nextToken();
    		if(token.equals("//"))
    		{
     		   return tokenList;
     	   	}
    		else if(token.equals("|"))
    		{
    			
    		}
    		else if(token.equals("("))
    		{
    			typeListOpen = true;
    		}
    		else
    		{
    			if(typeListOpen)
    			{
    				String strList = token;
    				String nextTokenInList = "";
    				boolean loopValue = true;
    				while(loopValue)
    				{
    					if(st.hasMoreTokens())
    					{
    						nextTokenInList = (st.nextToken()).toString();
    						if(!(nextTokenInList.equals(")")))
    						{
    							strList+= ","+nextTokenInList;
    						}
    						else
    						{
    							typeListOpen = false;
    			    			typeListEmpty = true;
    			    			loopValue = false;
    						}
    					}
    					else
    						loopValue = false;
    				}
    				tokenList.add(strList);	
    			}
    			else
    			{
    				tokenList.add(token);
    			}
    		}
    	}
    return tokenList;
	}
	
	public boolean loadRuleFile(String filePath)
	{
		@SuppressWarnings("unused")
		FileReader reader;
		try 
		{
			reader = new FileReader(filePath);
			return true;
		} 
		catch (FileNotFoundException e) 
		{
			//	in caso il file non sia presente sarà visualizzato un errore
			return false;
		}
	}
	
	public boolean insertNewRule(String filePath, String newRule)
	{

		FileWriter writer;
		try 
		{
			writer = new FileWriter(filePath,true);
			writer.write("\n"+newRule);
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			//	in caso di errore sarà vuoto e l'interfaccia grafica segnalerà l'errore
			return false;
		}
		 catch (IOException e) 
		 {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean changeARule(String filePath, String ruleToChange, String newRule)
	{
		//	Questo metodo è richiamato per la modifica di una regola.
		//	Si cerca all'interno del file la regola da cambiare che sia uguale a ruleToChange e la si sovrascrive con
		//	newRule
		
		FileReader reader;
		//	greatString è una stringa che conterrà l'intero contenuto del file delle regole
		String greatString = "";
		String newGreatString = "";
		
		try 
		{
			reader = new FileReader(filePath);
			
		} 
		catch (FileNotFoundException e) 
		{
			return false;
		}
		Scanner in = new Scanner(reader);
		
		while(in.hasNextLine())
		{   
			greatString = greatString + in.nextLine() + "\n";
		}		
		newGreatString = greatString.replace(ruleToChange, newRule);
		//	Adesso salvo newGreatString nel file delle regole: mantiene la vecchia formattazione
		FileWriter writer;
		try 
		{
			writer = new FileWriter(filePath);
			writer.write(newGreatString);
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			//	In caso di errore sarà vuoto e l'interfaccia grafica segnalerà l'errore
			return false;
		}
		 catch (IOException e) 
		 {
			e.printStackTrace();
			return false;
		}

		 return true;
	}
	
/*	public static void main(String[] args) 
	{
		FileManager fm = new FileManager();
	//	boolean ris = fm.insertNewRule("/home/paolo/Materiale_labo_software/provascrittura.txt","\n\nalert tcp $EXTERNAL_NET any -> $TELNET_SERVERS 23 (msg:'TELNET login buffer overflow attempt'; flow:to_server,established; flowbits:isnotset,ttyprompt; content:'|FF FA 27 00 00|TTYPROMPT|01|'; rawbytes; flowbits:set,ttyprompt; reference:cve,2001-0797; reference:bugtraq,3681; classtype:attempted-admin; sid:3147; rev:2;)");
	boolean ris = fm.changeARule("/home/paolo/Materiale_labo_software/provascrittura.txt", "alert tcp $EXTERNAL_GIGAPAO any -> $TELNET_SERVERS 23 (msg:\"TELNET login buffer overflow attempt\"; flow:to_server,established; flowbits:isnotset,ttyprompt; content:\"|FF FA 27 00 00|TTYPROMPT|01|\"; rawbytes; flowbits:set,ttyprompt; reference:cve,2001-0797; reference:bugtraq,3681; classtype:attempted-admin; sid:GIGAPAO; rev:XXXXXXXXXXXXXXXXXXXXXX;)", "alert tcp $EXTERNAL_SUPERPAO any -> $TELNET_SERVERS 23 (msg:\"TELNET login buffer overflow attempt\"; flow:to_server,established; flowbits:isnotset,ttyprompt; content:\"|FF FA 27 00 00|TTYPROMPT|01|\"; rawbytes; flowbits:set,ttyprompt; reference:cve,2001-0797; reference:bugtraq,3681; classtype:attempted-admin; sid:SUPERPAO; rev:1000;)");
		
if(ris)
	System.out.println("fatto");
else
	System.out.println("acc.....");
	}*/
		  
}
