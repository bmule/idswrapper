package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.OutputHandler;

public class IDMEFHandler extends OutputHandler
{
	public IDMEFHandler()
	{
		handlerName = "IDMEF Handler";
		handlerType = "Gestisco l'output in formato IDEMF";
			
		System.out.println(handlerName+"   "+handlerType);
	}

}
