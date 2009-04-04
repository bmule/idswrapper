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

import it.polimi.elet.vplab.idswrapper.ids.*;
import it.polimi.elet.vplab.idswrapper.gui.*;
import it.polimi.elet.vplab.idswrapper.Main;

import java.util.ArrayList;

@SuppressWarnings({"unchecked"})
public class IDSWrapper 
{
	ArrayList inHandlerList;
	ArrayList outHandlerList;
	IDS ids;
	IDSWrapperVisualInterface visualInterface;
	//CommandLine cline;

	public void selectInputType(InputHandler inHandler)
	{
		if(inHandlerList == null)
			inHandlerList = new ArrayList();
		inHandlerList.add(inHandler);

		Main.logger.info("Input handler set: " + inHandler.getHandlerName());
	}

	public void selectOutputType(OutputHandler outHandler)
	{
		if(outHandlerList == null)
			outHandlerList = new ArrayList();
		outHandlerList.add(outHandler);

		Main.logger.info("Output handler set: " + outHandler.getHandlerName());
	}

	public void selectIDS(IDS ids)
	{
		this.ids = ids;
		Main.logger.info("IDS set: " + ids.getName());
	}

	public IDS getIDS()
	{
		return ids;
	}

	public ArrayList<InputHandler> getInputHandlerList()
	{
		return inHandlerList;
	}

	public void startAnalysis()
	{

	}

	public void startTraining()
	{

	}

	public void launchGui()
	{
		this.startVisualInterface();
	}

	private void startVisualInterface()
	{
		visualInterface = new IDSWrapperVisualInterface();
		visualInterface.startInterface(this);
		//	selectIDS(visualInterface.getIDS());
		//visualInterface = new IDSWrapperVisualInterface();
		//visualInterface.start();
		//selectIDS(visualInterface.getIDS());
	}

	public IDSWrapperVisualInterface getVisualInterface()
	{
		return visualInterface;
	}

	public static void main(String[] args)
	{
		IDSWrapper idsWrapper = new IDSWrapper();
		idsWrapper.startVisualInterface();
	}

}
