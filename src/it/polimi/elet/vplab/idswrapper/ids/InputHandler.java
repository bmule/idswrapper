package it.polimi.elet.vplab.idswrapper.ids;

public abstract class InputHandler 
{
	protected String handlerName;
	protected String handlerType;
	
	
	public void handleInput()
	{
		
	}
	
	public void controlInputType()
	{
		
	}
	
	public String getInputType()
	{
		return handlerType;
	}
	
	public String getHandlerName()
	{
		return handlerName;
	}
}
