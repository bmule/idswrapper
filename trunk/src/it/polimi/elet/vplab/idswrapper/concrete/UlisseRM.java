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

import it.polimi.elet.vplab.idswrapper.ids.RulesManager;
import it.polimi.elet.vplab.idswrapper.filemanager.FileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
			return false;
		}
	}
	
	public ArrayList<String> loadRules(String fileName)
	{
		FileReader reader;
		rulesList = new ArrayList<String>();

		try {
			reader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			return rulesList;
		}

		Scanner in = new Scanner(reader);
		String ruleUnderConstruction = "";
		boolean previousRuleClosed = true;

		while (in.hasNextLine()) {   
			String line = in.nextLine();
			String fw = getFirstWord(line);
			String lastLineChar = "";

			if (fw.equals("alert") || fw.equals("log") ||
					fw.equals("pass") || fw.equals("activate") ||
					fw.equals("dynamic") || fw.equals("drop") ||
					fw.equals("reject") || fw.equals("sdrop")) {

				if (previousRuleClosed) {
					if (getLastTwoChars(line).equals(";)")) {
						//	The rule is on a unique row
						rulesList.add(line);
					} else {
						previousRuleClosed = false;
						ruleUnderConstruction = line;

						if (!line.equals("") && !getLastChar(line).equals(" "))
							lastLineChar = getLastChar(line);
					}
				} else {
					if (!previousRuleClosed) {
						ruleUnderConstruction = ruleUnderConstruction + line;

						if ((getLastTwoChars(line).equals(";)")) ||
								(getLastTwoChars(line).equals(")") &&
								 lastLineChar.equals(";"))) {

							previousRuleClosed = true;
							rulesList.add(ruleUnderConstruction);
						}
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
	
	public boolean changeARule(String filePath, String ruleToChange, String newRule)
	{
		/*
		 * This method search for a rule that is equals to "ruleToChange" and replace it with "newRule"
		 */
		
		FileReader reader;
		//	greatString will contain all the rules in the file
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
		//	Save newGreatString into rules file
		FileWriter writer;
		try 
		{
			writer = new FileWriter(filePath);
			writer.write(newGreatString);
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			return false;
		}
		 catch (IOException e) 
		 {
			e.printStackTrace();
			return false;
		}

		 return true;
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
			return false;
		}
		 catch (IOException e) 
		 {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String getLastTwoChars(String str)
	{
		char[] charArray = str.toCharArray();
		String result = "";
		
		if (charArray.length > 0) {
			int i = charArray.length-1;

			while (String.valueOf(charArray[i]).equals(" ") && i > 0)
				i = i-1;

			result = String.valueOf(charArray[i]);
			boolean isNotEnought = true;

			if (i > 0) {
				i--;
				
				while(i > 0 && isNotEnought)
					if(String.valueOf(charArray[i]).equals(" "))
						i--;
					else
						isNotEnought = false;

				result = String.valueOf(charArray[i]) + result;
			}
		}

		return result;
	}
	
	private String getFirstWord(String str)
	{
		char[] charArray = str.toCharArray();
		String firstWord = "";
		int i = 0;
		
		if (charArray.length > 0) {
			while (charArray[i] == ' ' && i < charArray.length - 1)
				i++;
		
			while (charArray[i] != ' ' && i < charArray.length - 1) {
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
		
		if(charArray.length > 0) {
			int i = charArray.length - 1;

			while (i > 0 && charArray[i] == ' ')
				i--;

			return String.valueOf(charArray[i]);
		}

		return "";
	}
}

