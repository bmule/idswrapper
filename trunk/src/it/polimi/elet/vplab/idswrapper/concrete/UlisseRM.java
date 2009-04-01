package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.RulesManager;
import it.polimi.elet.vplab.idswrapper.filemanager.FileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class UlisseRM
	extends RulesManager
{
	FileManager fmanager;
	
	public UlisseRM()
	{
		relatedIDS = "Ulisse";
	}
	
	public boolean checkRulesSintax(String ruleStr)
	{
		boolean sintaxIsCorrect = false;
		
		
		return sintaxIsCorrect;
	}
	
	public ArrayList<String> loadRules(String fileName)
	{
		FileReader reader;
		rulesList = new ArrayList<String>();
		
		try
		{
			reader = new FileReader(fileName);
		} 
		catch (FileNotFoundException e) 
		{
			return rulesList;
		}
		Scanner in = new Scanner(reader);
		
		String ruleUnderConstruction = "";
		boolean previousRuleClosed = true;
		
		while(in.hasNextLine())
		{   
			String line = in.nextLine();
			String fw = getFirstWord(line);
			String lastLineChar = "";
			
			if( (fw.equals("alert")) || (fw.equals("log")) || (fw.equals("pass")) || (fw.equals("activate")) || (fw.equals("dynamic")) || (fw.equals("drop")) || (fw.equals("reject")) || (fw.equals("sdrop")) ) 
			{
				if(previousRuleClosed)
				{
					if(getLastTwoChars(line).equals(";)"))
					{
						//	In questo caso abbiamo una regola completa su un'unica linea
						rulesList.add(line);
					}
					else
						{
						previousRuleClosed = false;
						ruleUnderConstruction = line;
						if( (!line.equals("")) && (!getLastChar(line).equals(" ")) )
							lastLineChar = getLastChar(line);
						}
				}
			}
			else
			{
				if(!previousRuleClosed)
				{
					ruleUnderConstruction = ruleUnderConstruction + line;
					if( (getLastTwoChars(line).equals(";)")) || (getLastTwoChars(line).equals(")") && lastLineChar.equals(";")) )
					{
						previousRuleClosed = true;
						rulesList.add(ruleUnderConstruction);
					}
				}
			}
		}
		return rulesList;
	}
	
	public String getRule(int index)
	{
		String str = "";
		
		return str; 
	}
	
	public void changeRule(String newRule, int index)
	{
		
	}
	
	public void insertNewRule(String newRule)
	{
		
	}
	
	private String getLastTwoChars(String str)
	{
		char[] charArray = str.toCharArray();
		String result = "";
		
		if(charArray.length > 0)
		{
			int i = charArray.length-1;
			while( (String.valueOf(charArray[i]).equals(" ")) && (i>0) )
				i = i-1;
			result = String.valueOf(charArray[i]);
	
			boolean isNotEnought = true;
			if(i>0)
			{	
				i = i-1;
				while( (i>0) && (isNotEnought) )
				{
					if(String.valueOf(charArray[i]).equals(" "))
						i = i-1;
					else
						isNotEnought = false;
				}
				result = String.valueOf(charArray[i])+result;
			}
			
		}
		return result;
	}
	
	private String getFirstWord(String str)
	{
		char[] charArray = str.toCharArray();
		String firstWord = "";
		int i = 0;
		
		if(charArray.length > 0)
		{
			while( (charArray[i] == ' ') && (i<charArray.length-1) )
				i++;
		
			while( (charArray[i] != ' ') && (i<charArray.length-1) )
			{
				firstWord = firstWord+charArray[i];
				i++;
			}
		}
		return firstWord;
	}
	
	private String getLastChar(String str)
	{
		char[] charArray = str.toCharArray();
		//String result = "";
		
		if(charArray.length > 0)
		{
			int i = charArray.length-1;
			while( (i>0) && (charArray[i] == ' ') )
			{
				i = i-1;
			}
			return String.valueOf(charArray[i]);
		}
		return "";
	}
}


