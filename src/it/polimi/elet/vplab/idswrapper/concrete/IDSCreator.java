package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.IDS;

import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * Questa classe viene richiamata dall'interfaccia IDSSelectionInterface e deve essere modificata al momento
 * dell'introduzione di un nuovo IDS. In particolare l'aggiunta va introdotta nel metodo "createIDSIstance"
 */

@SuppressWarnings({"unchecked"})
public class IDSCreator 
{
	IDS ids;
	
	
	public IDS createIDSIstance(ArrayList alist, int selectedIndex, String IDSpath)
	{
		IDS IDS = null;
	
		if((((ArrayList)(alist.get(selectedIndex))).get(0)).toString().equals("Snort"))
		{
			IDS = new it.polimi.elet.vplab.idswrapper.concrete.Snort(IDSpath, ((((ArrayList)(alist.get(selectedIndex))).get(1)).toString()), stringListToArray((((ArrayList)(alist.get(selectedIndex))).get(4)).toString()),stringListToArray((((ArrayList)(alist.get(selectedIndex))).get(5)).toString()));
			IDS.newCommandLineAnalysis();
		}
		else if((((ArrayList)(alist.get(selectedIndex))).get(0)).toString().equals("Ulisse"))
		{
			IDS = new it.polimi.elet.vplab.idswrapper.concrete.Ulisse(IDSpath, ((((ArrayList)(alist.get(selectedIndex))).get(1)).toString()), stringListToArray((((ArrayList)(alist.get(selectedIndex))).get(4)).toString()),stringListToArray((((ArrayList)(alist.get(selectedIndex))).get(5)).toString()));
			IDS.newCommandLineAnalysis();
			IDS.newCommandLineTraining();
		}

		return IDS;
		
	}
	
	private ArrayList stringListToArray(String strList)
	{
		ArrayList alist = new ArrayList();
		
		StringTokenizer st = new StringTokenizer(strList,",");
		while(st.hasMoreTokens())
		{
			String token = st.nextToken();
				alist.add(token);
		}
		return alist;
	}
	

}
