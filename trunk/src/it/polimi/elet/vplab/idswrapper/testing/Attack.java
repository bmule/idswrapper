package it.polimi.elet.vplab.idswrapper.testing;

/**
 * Implements Entity interface.
 * 
 * In particular this is for the truth file of DARPA Dataset. It allows to keep
 * an attack in memory.
 * 
 * @author Claudio Magni
 * 
 */

public class Attack
	extends Entity {
	
	private String[] targetIP;
	private long[] time;
	private int[] duration;

	/**
	 * Empty constructor.
	 */
	public Attack()
	{
		super();
	}

	/**
	 * Construct a new attack object.
	 *
	 * @param id The ID of the attack.
	 * @param name A name associated to the attack.
	 * @param targetIP The victim's IP.
	 * @param time Timestamp of the attack event (beginning).
	 * @param duration The duration, in seconds, of the attack.
	 *
	 */
	public Attack(String id, String name, String[] targetIP, long[] time, int[] duration)
	{
		this.id = id;
		this.time = time;
		this.duration = duration;
		this.name = name;
		this.targetIP = targetIP;
	}

	/**
	 * Get time.
	 *
	 * @return timestamp.
	 */
	public long[] getTime()
	{
		return time;
	}
	
	/**
	 * Get the victim IP.
	 *
	 * @return the victim IP.
	 */
	public String[] getTargetIP()
	{
		return targetIP;
	}
	
	/**
	 * Get the duration of the attack.
	 *
	 * @return duration.
	 */
	public int[] getDuration()
	{
		return duration;
	}
	
	/**
	 * Set the timestamp (beginning).
	 *
	 * @param timestamp Timestamp of the attack event (beginning).
	 */
	public void setTime(long[] timestamp)
	{
		this.time = timestamp;
	}
	
	/**
	 * Set the victim IP.
	 *
	 * @param targetIP The victim's IP address.
	 */
	public void setTargetIP(String[] ip)
	{
		this.targetIP = ip;
	}
	
	/**
	 * Set the duration.
	 *
	 * @param duration.
	 */
	public void setDuration(int[] duration)
	{
		this.duration = duration;
	}
	
	/**
	 * Returns a string representation of the alert.
	 *
	 * @return a string representation of the alert.
	 */
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
