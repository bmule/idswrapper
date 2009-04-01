package it.polimi.elet.vplab.idswrapper.ids;

import java.util.ArrayList;

public abstract class RulesManager 
{
	protected String relatedIDS;
	protected ArrayList<String> rulesList;
	
	
	//	Questo metodo ha il compito di controllare se la regola considerata è sintatticamente compatibile all'IDS
	//	selezionato
	public boolean checkRulesSintax(String ruleStr)
	{
		boolean sintaxIsCorrect = true;
		
		return sintaxIsCorrect;
	}
	
	//	Questo metodo ha il compito di caricare dal file in input le regole che vi sono contenute:
	//	è compito di questo metodo distinguere il punto iniziale e terminale di ogni regola. Al termine viene 
	//	restituito un ArrayList di String contenente le regole
	public ArrayList<String> loadRules(String fileName)
	{
		return rulesList;
	}
	
	//	Questo metodo restituisce la regola corrispondente all'indice selezionato
	public String getRule(int index)
	{
		String str = "";
		
		return str; 
	}
	
	//	Questo metodo sovrascrive la regola in posizione "index" di ruleList
	public void changeRule(String newRule, int index)
	{
		
	}
	
	//	Questo metodo inserisce una nuova regola nella ruleList
	public void insertNewRule(String newRule)
	{
		
	}
	
	
}
