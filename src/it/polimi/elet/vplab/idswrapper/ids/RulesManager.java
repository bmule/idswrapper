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
package it.polimi.elet.vplab.idswrapper.ids;

import java.util.ArrayList;

public abstract class RulesManager 
{
	protected String relatedIDS;
	protected ArrayList<String> rulesList;
	
	
	public boolean checkRulesSintax(String ruleStr)
	{
		boolean sintaxIsCorrect = true;
		
		return sintaxIsCorrect;
	}
	
	public ArrayList<String> loadRules(String fileName)
	{
		return rulesList;
	}
	
	public String getRule(int index)
	{
		String str = "";
		
		return str; 
	}
	
	public boolean changeARule(String filePath, String ruleToChange, String newRule)
	{
		boolean value = true;
		
		return value;
	}
	
	public boolean insertNewRule(String filePath, String newRule)
	{
		boolean value = true;
		
		return value;
	}
	
	public boolean loadRuleFile(String filePath)
	{
		boolean value = true;
		
		return value;
	}
	
	
}
