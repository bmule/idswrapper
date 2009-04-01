package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.*;
import it.polimi.elet.vplab.idswrapper.gui.*;

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
	System.out.println("Ho settato l'input handler:"+ inHandler.getHandlerName());
	}
	
	public void selectOutputType(OutputHandler outHandler)
	{
		if(outHandlerList == null)
			outHandlerList = new ArrayList();
		outHandlerList.add(outHandler);
	System.out.println("Ho settato l'output handler:"+ outHandler.getHandlerName());
	}
	
	public void selectIDS(IDS ids)
	{
		this.ids = ids;
System.out.println("HO SETTATO L'IDS!!");
System.out.println(ids.getName());
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
