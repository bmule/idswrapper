package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.InputHandler;

public class pcapHandler
	extends InputHandler
{
	public pcapHandler()
	{
		handlerName = "pcap Handler";
		handlerType = "Handle pcap files";
		
		System.out.println(handlerName+"   "+handlerType);
	}
}
