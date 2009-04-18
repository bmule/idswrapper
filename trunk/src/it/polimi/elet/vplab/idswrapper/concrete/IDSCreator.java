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

import it.polimi.elet.vplab.idswrapper.ids.IDS;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.List;

/**
 * This class is called from IDSSelectionInterface interface.
 *
 * If you're going to add a new IDS it is necessary to modify the method
 * "createIDSIstance" of this class.
 *
 * @author $Author$
 * @version $Id$
 */
@SuppressWarnings({"unchecked"})
public class IDSCreator 
{
	IDS ids;
	
	public IDS createIDSIstance(IDS ids, String IDSpath)
	{
		IDS IDS = null;

		if(ids.getName().equals("Snort")) 
		{
			IDS = ids;
			IDS.setPath(IDSpath);
			IDS.newCommandLineAnalysis();
		} 
		else if(ids.getName().equals("Ulisse")) 
		{
			IDS = ids;
			IDS.setPath(IDSpath);
			IDS.newCommandLineAnalysis();
			IDS.newCommandLineTraining();
		}

		return IDS;

	}
	
	@SuppressWarnings("unused")
	private ArrayList stringListToArray(String strList)
	{
		ArrayList alist = new ArrayList();
		StringTokenizer st = new StringTokenizer(strList,",");

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			alist.add(token);
		}

		return alist;
	}
	
	public List<IDS> getImplementedIDSList()
	{
		List idsList = new ArrayList();
		Snort snort = new Snort();
		Ulisse ulisse = new Ulisse();
		
		idsList.add(snort);
		idsList.add(ulisse);
		
		return idsList;
	}
}
