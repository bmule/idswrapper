package it.polimi.elet.vplab.idswrapper.testing;

/**
 * 
 * Implements Entity interface.
 * In particular this is for the truth file of DARPA Dataset. It allows to keep
 * an attack in memory.
 * 
 * @author Claudio Magni
 * 
 ******************************************************************************/

public class Attack
	extends Entity {
	
	private String[] targetIP;
	private long[] time;
	private int[] duration;
	
	public Attack()
	{
		super();
	}
	
	public Attack(String id, String name, String[] targetIP, long[] time, int[] duration)
	{
		this.id = id;
		this.time = time;
		this.duration = duration;
		this.name = name;
		this.targetIP = targetIP;
	}
	
	public long[] getTime() {
		return time;
	}
	
	public String[] getTargetIP() {
		return targetIP;
	}
	
	public int[] getDuration() {
		return duration;
	}

	
	public void setTime(long[] timestamp) {
		this.time = timestamp;
	}
	
	public void setTargetIP(String[] ip) {
		this.targetIP = ip;
	}
	
	public void setDuration(int[] duration) {
		this.duration = duration;
	}
	
	
	public String toString()
	{
		String buffer = "";
		
		buffer += "ALERT # " + id + "\n";
		buffer += "Name: " + name + "\n";
		buffer += "Timestamp: " + time + "\n";
		buffer += "Duration: " + duration + "\n";
		buffer += "Target IPS:" + targetIP + "\n";
		
		return buffer;
	}
	
}
