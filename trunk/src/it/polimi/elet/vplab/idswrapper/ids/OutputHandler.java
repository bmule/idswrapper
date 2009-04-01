package it.polimi.elet.vplab.idswrapper.ids;

public abstract class OutputHandler 
{
	protected String outputFormat;
	protected String handlerName;
	protected String handlerType;
	
	public void convertTo()
	{
		
	}
	
	public String getOutputType()
	{
		return handlerType;
	}
	
	public String getHandlerName()
	{
		return handlerName;
	}
	
}
