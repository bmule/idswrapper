package it.polimi.elet.vplab.idswrapper.testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** 
 * Extends {@link Parser} interface.<br />
 * This one in particular is for the DARPA SET IDEVAL truth file, thus it parses attacks.
 * The truth files can be found at http://www.ll.mit.edu/IST/ideval/.
 * 
 * @author Claudio Magni
 * @version 1.0
 * 
 */

public class TruthFile99Parser extends Parser {
	
	private String id = null;
	private String name;
	private ArrayList<String> targetArray;
	private ArrayList<Long> timeArray;
	private ArrayList<Integer> durationArray;
	private boolean listFound;
	
	private String line;
	private Pattern pSum;
	private Matcher mSum;
	private Pattern pNull;
	private Matcher mNull;
	private Pattern p;
	private Matcher m;
	private Pattern p1;	// for ip normalization
	private Matcher m1;	// for ip normalization
	private final String regexIP = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
	
	/*
	 * Empty line
	 */
	private final String regexNull = "[^\\w]*";
	
	/*
	 * First line of the summarized attack.
	 */
	private final String regexSum = "\\s*Summarized attack:\\s*(\\d\\d\\.\\d+)\\s*";
	
	/*
	 * This (very complex) regex will match a line (the alert) of the truth file.
	 * It is written so that every value will be grouped by capturing parenthesis.
	 * It's gonna be a real mess! ;)
	 */
	private final String regex = "\\s" +											// starting space
										"(\\d\\d\\.\\d{6})" +							// ID (1)
										"(\\d\\d/\\d\\d/\\d\\d\\d\\d)\\s+" +			// Date (2)
										"(\\d\\d:\\d\\d:\\d\\d)\\s+" +					// Time (3)
										"(\\d\\d:\\d\\d:\\d\\d)\\s+" +					// Duration (4)
										"(\\d\\d\\d.\\d\\d\\d.\\d\\d\\d.\\d\\d\\d)" +	// IP (5)
										"(.{11})" + 									// Attack name (6)
										".*";	
	
	
	/**
	 * Creates a new parser ready to move on within the supplied file.
	 */
	public TruthFile99Parser()
	{
		super();
		p = Pattern.compile(regex);
		p1 = Pattern.compile(regexIP);
		pSum = Pattern.compile(regexSum);
		pNull = Pattern.compile(regexNull);
	}

	
	/**
	 * Retrieves next entity (attack) in the file.
	 * 
	 * @return The next entity.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Entity getNextEntity ()
	{
		boolean check;
		listFound = false;
		targetArray = new ArrayList();
		timeArray = new ArrayList();
		durationArray = new ArrayList();
		
		try {
			
			line = br.readLine();
			while (line != null) {
				mSum = pSum.matcher(line);
				check = mSum.matches();
				if (check) {
					
					// Summarized Attack found
					id = mSum.group(1);
					line = br.readLine();
					
					while (line != null) {
						
						m = p.matcher(line);
						check = m.matches();
						
						if (check) {
							// Line OK
							if (listFound == false) {
								name = m.group(6).trim();
								listFound = true;
							}

							// Handle IP
							String ip = m.group(5).trim();
							int[] n = new int[4];
							m1 = p1.matcher(ip);
							if (m1.matches()) {
								for (int j = 0; j < n.length; j++) {
									n[j] = Integer.parseInt(m1.group(j+1));
								}
								ip = n[0] + "." + n[1] + "." + n[2] + "." + n[3];
							}
							
							// Handle date, we need to return the timestamp
							String date = m.group(2).trim();
							int month = Integer.parseInt(date.substring(0, 2));
							int day = Integer.parseInt(date.substring(3, 5));
							int year = Integer.parseInt(date.substring(6));
							String time = m.group(3).trim();
							int hour = Integer.parseInt(time.substring(0, 2));
							int min = Integer.parseInt(time.substring(3, 5));
							int sec = Integer.parseInt(time.substring(6));
							
							if (timeZ == null) timeZ = TimeZone.getTimeZone("GMT");
							GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, min, sec);
							calendar.setTimeZone(timeZ);
							long timeStamp = calendar.getTimeInMillis();
							
							// Handle duration. Remember, it represents number of millisecs
							time = m.group(4).trim();
							hour = Integer.parseInt(time.substring(0, 2));
							min = Integer.parseInt(time.substring(3, 5));
							sec = Integer.parseInt(time.substring(6, 8));
							int duration = sec + min * 60 + hour * 3600;
							duration *= 1000;
							
							targetArray.add(ip);
							timeArray.add(timeStamp);
							durationArray.add(duration);
						} else {
							mNull = pNull.matcher(line);
							check = mNull.matches();
							if (check && listFound) {
								// Perfect. return entity
								return returnEntity();
							}
							if (check && !listFound) break; // Search for another Summarized Attack
						}
						line = br.readLine(); // Alert loop
						
						// Before continue check if EOF!!
						// That means we have to return last entity.
						if (line == null) {
							// Perfect. return entity
							return returnEntity();
						}
					}
				}
				line = br.readLine(); // Summarized Attack loop
			}	
			// if EOF close
			this.closeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Entity returnEntity() {
		int size = targetArray.size();
		String[] target = new String[size];
		long[] time = new long[size];
		int[] duration = new int[size];
		for (int a = 0; a < size; a++) {
			target[a] = targetArray.get(a);
			time[a] = timeArray.get(a);
			duration[a] = durationArray.get(a);
		}
		
		entity = new Attack(id, name, target, time, duration);
		
		return entity;
	}
}
