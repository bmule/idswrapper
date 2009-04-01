package it.polimi.elet.vplab.idswrapper.testing;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 * 
 * This class is used to compare two <b>IDMEF</b> messages/files to find the accuracy of the 
 * Intrusion Detection System that generated the alerts. The results are indexes of the accuracy:
 * True Positives, True Negatives, False Positives, False Negatives. They are necessary to build 
 * a <b>ROC</b> curve.
 * 
 * @author Claudio Magni
 * @version 1.0
 *
 */

public class IDMEFAnalyzer {
	
	/*
	 * Variables needed to handle DOM document
	 */
	private Document truthDoc;
	private Document alertDoc;
	// private DocType type; // Not used now
	private Namespace truthNameSpace;
	private Namespace alertNameSpace;
	private Element truthRoot;
	private Element alertRoot;
	
	/*
	 * Variables needed to handle file
	 */
	private String truthFile;
	private String alertFile;
	private File f;
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;
	
	/*
	 * Variables needed for the test
	 */
	private int tp 		= 0;
	private int fp 		= 0;
	private int fn 		= 0;
	private int tn		= 0;
	private int numSeq 	= 1;
	
	
	/**
	 * Creates a new instance of Analyzer
	 */
	public IDMEFAnalyzer () {
		super();
	}
	
	
	/**
	 * Sets the path of the truth file to analyze. It has to be a well-formed
	 * XML and IDMEF file.
	 * 
	 * @param path The path of the truth file.
	 * @throws JDOMException 
	 * @throws IOException
	 */
	public void setTruthFile(String path) {
		truthFile = path;
		readTruth(truthFile);
	}
	
	/**
	 * Sets the number of sequences corresponding to the truth file. These can be seen as 
	 * all the objects among which alerts are raised.
	 * 
	 * @param number Number of sequencies
	 */
	public void setNumSeq(int number) {
		numSeq = number;
	}
	
	/**
	 * Sets the path of file containing the alerts. This is then compared to the
	 * truth file to find correct/wrong alerts. It has to be a well-formed XML and IDMEF file.
	 * 
	 * @param path The path of the alert file.
	 * @throws JDOMException 
	 * @throws IOException
	 */
	public void setAlertFile(String path) {
		alertFile = path;
		readAlert(alertFile);
	}
	
	/**
	 * This is the real <b>THING</b>. It compares the two IDMEF files and stores useful 
	 * infos about this test.
	 * 
	 * @throws JDOMException If IDMEF files aren't correct.
	 */
	@SuppressWarnings("unchecked")
	public void compare() {
		try {
			// We need a third jdom tree to store hit attacks
			ArrayList<Element> hits = new ArrayList();
			int numHits = 0;
			
			// Get the list of alerts in alert file
			List<Element> alerts = alertRoot.getChildren("Alert", alertNameSpace);
			int numAlerts = alerts.size();
			if (numAlerts == 0) throw new JDOMException("Empty or malformed IDMEF document");
			
			for (int i = 0; i < numAlerts; i++) {
				// Get the list of alerts in truth file
				List<Element> truths = truthRoot.getChildren("Alert", truthNameSpace);
				int numTruths = truths.size();
				if (numTruths == 0) throw new JDOMException("Empty or malformed IDMEF document");
				
				boolean found = false;
				
				// First search the hit list
				for (int k = 0; k < numHits; k++) {
					if (same(alerts.get(i), hits.get(k))) {
						// Hooah! Alert is correct, but it is for an attack already detected. Do nothing

						/* This is for debug
						System.out.println("Hooah!");
						System.out.println(alerts.get(i).getChild("CreateTime", alertNameSpace).getText());
						System.out.println(truths.get(j).getChild("CreateTime", truthNameSpace).getText());
						*/

						found = true;
						break;
					}
				}
				
				// Then we can search the truth list
				for (int j = 0; j < numTruths && !found; j++) {
					if (same(alerts.get(i), truths.get(j))) {
						// Hooah! Alert is correct, good job ids.
						tp++;
						
						
						System.out.println("\nHooah!");
						System.out.println(alerts.get(i).getChild("CreateTime", alertNameSpace).getText());
						System.out.println(truths.get(j).getChild("CreateTime", truthNameSpace).getText());
						
						
						// Add the attack to the hits and remove from truths
						hits.add(truths.get(j));
						numHits = hits.size();
						truthRoot.removeContent(truths.get(j));
						found = true;
						break;
					}
				}
				if (!found)	fp++;
			}
			
			// Finished. We can finally check for False Negatives and True Negatives
			int survivors = truthRoot.getChildren("Alert", truthNameSpace).size();
			fn = survivors;
			tn = numSeq - (tp + fp + fn);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get all info about the result
	 */
	public int getTP() {
		return tp;
	}
	public int getFP() {
		return fp;
	}
	public int getFN() {
		return fn;
	}
	public int getTN() {
		return tn;
	}
	
	
	/**
	 * Returns the number of sequences corresponding to the truth file.
	 * 
	 * @return Number of sequences.
	 */
	public int getNumSeq() {
		return numSeq;
	}
	/**
	 * Returns the True Positive Rate (TPR) of the test performed by 
	 * {@link jidstest.IDMEFAnalyzer#compare()}. <br />
	 * TPR is calculated as follows: tpr = tp / (tp + fn). 
	 * It represents the sensitivity.
	 * 
	 * @return The TPR as a float.
	 */
	public float getTPR() {
		float tpr = (float)tp / (float)(tp + fn);
		return tpr * 100;
	}
	/**
	 * Returns the False Positive Rate (FPR) of the test performed by 
	 * {@link jidstest.IDMEFAnalyzer#compare()}. <br />
	 * FPR is calculated as follows: fpr = fp / (fp + tn). 
	 * It represents the opposite of the specificity.
	 * 
	 * @return The TPR as a float.
	 */
	public float getFPR() {
		float fpr = (float)fp / (float)(fp + tn);
		return fpr * 100;
	}
	
	/*
	 * Compare 2 alerts and finds whether they are equals.
	 * Equals if they have same Time and Target Address
	 */
	@SuppressWarnings("unchecked")
	private boolean same(Element alert, Element truth) {
		try {
			Element alertTimeNode = alert.getChild("CreateTime", alertNameSpace);
			Element truthTimeNode = truth.getChild("CreateTime", truthNameSpace);
			if (alertTimeNode == null || truthTimeNode == null) 
				throw new JDOMException("Malformed IDMEF document: no CreateTime");
			List<Element> truthData = truth.getChildren("AdditionalData", truthNameSpace);
			String duration = "0";
			for (int i = 0; i < truthData.size(); i++) {
				if (truthData.get(i).getAttributeValue("meaning") != null) {
					if (truthData.get(i).getAttributeValue("meaning").equals("duration"))
						duration = truthData.get(i).getTextTrim();
				}
			}
			String alertTime = alertTimeNode.getTextNormalize();
			String truthTime = truthTimeNode.getTextNormalize();
			
			if (sameTime(alertTime, truthTime, duration)) {
				/*
				Element alertAddr = alert.getChild("Source", alertNameSpace).getChild("Node", alertNameSpace).getChild("Address", alertNameSpace).getChild("address", alertNameSpace);
				Element truthAddr = truth.getChild("Source", truthNameSpace).getChild("Node", truthNameSpace).getChild("Address", truthNameSpace).getChild("address", truthNameSpace);
				if (alertAddr != null && truthAddr != null) {
					// Damn we cannot rely on source address cause it isn't supplied in
					// IDEVAL truth files... idiots...
					// Let's just go on
					// throw new JDOMException("Malformed IDMEF document: no Source address");
					String alertSource = alertAddr.getTextNormalize();
					String truthSource = truthAddr.getTextNormalize();
					if (!alertSource.equals(truthSource)) return false;
				}
				*/
				Element alertTrg = alert.getChild("Target", alertNameSpace).getChild("Node", alertNameSpace).getChild("Address", alertNameSpace).getChild("address", alertNameSpace);
				Element truthTrg = truth.getChild("Target", truthNameSpace).getChild("Node", truthNameSpace).getChild("Address", truthNameSpace).getChild("address", truthNameSpace);
				if (alertTrg == null || truthTrg == null) 
					throw new JDOMException("Malformed IDMEF document: no Target address");
				
				String alertTarget = alertTrg.getTextNormalize();
				String truthTarget = truthTrg.getTextNormalize();
				
				if (sameIP(alertTarget, truthTarget)) {
					// Hooah! Alert is correct, good job ids.
					return true;
				}
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Returns whether 2 ips are equal.
	 */
	private boolean sameIP(String ip1, String ip2) {
		try {
			int[] n1 = new int[4];
			int[] n2 = new int[4];
			String regex = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
			Pattern p = Pattern.compile(regex);
			Matcher m;
			
			m = p.matcher( ip1 );
			if (!m.matches()) {
				System.out.println(ip1);
				System.out.println(" ");
				throw new JDOMException("Malformed IDMEF document: bad ip String");
			}
			for (int i = 0; i < n1.length; i++) {
				n1[i] = Integer.parseInt(m.group(i+1).trim());
			}
			
			m = p.matcher( ip2 );
			if (!m.matches()) throw new JDOMException("Malformed IDMEF document: bad ip String");
			for (int i = 0; i < n1.length; i++) {
				n2[i] = Integer.parseInt(m.group(i+1).trim());
			}

			for (int i = 0; i < n1.length; i++) {
				if (n1[i] != n2[i]) return false;
			}

			return true;
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * Returns whether alert date in inside truth file interval interval.
	 * I really hate this sloppy method, but hell I can't find a better way.
	 * These files are just shit!
	 */
	private boolean sameTime(String alert, String truth, String duration) {
		try {
			long millisecs = Long.parseLong(duration);
			// Format: YYYY-MM-DDThh:mm:ss.ss+hh:mm
			//String regex = "([^,\\.]*)[,\\.](\\d*)Z"; vecchio regex
			
			String regex = "\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\dZ";
			Pattern p = Pattern.compile(regex);
			Matcher m;
			
			m = p.matcher( alert );
			if (!m.matches()) throw new JDOMException("Malformed IDMEF document: bad time String");
			//String s1 = m.group(0);
			int alertYear = Integer.parseInt(alert.substring(0, 4));
			int alertMonth = Integer.parseInt(alert.substring(5, 7));
			int alertDay = Integer.parseInt(alert.substring(8, 10));
			int alertHour = Integer.parseInt(alert.substring(11, 13));
			int alertMin = Integer.parseInt(alert.substring(14, 16));
			int alertSec = Integer.parseInt(alert.substring(17, 19));
			
			//System.out.println(alertYear+"-"+alertMonth+"-"+alertDay+"T"+
			//		alertHour+":"+alertMin+":"+alertSec+"Z");
			
			m = p.matcher( truth );
			if (!m.matches()) throw new JDOMException("Malformed IDMEF document: bad time String");
			//String s2 = m.group(1);
			int truthYear = Integer.parseInt(truth.substring(0, 4));
			int truthMonth = Integer.parseInt(truth.substring(5, 7));
			int truthDay = Integer.parseInt(truth.substring(8, 10));
			int truthHour = Integer.parseInt(truth.substring(11, 13));
			int truthMin = Integer.parseInt(truth.substring(14, 16));
			int truthSec = Integer.parseInt(truth.substring(17, 19));
			
			//System.out.println(truthYear+"-"+truthMonth+"-"+truthDay+"T"+
			//		truthHour+":"+truthMin+":"+truthSec+"Z");
			
			TimeZone zone = TimeZone.getTimeZone("GMT"); // Truth file should always GMT!
			Calendar alertCalendar = new GregorianCalendar(zone);
			Calendar truthCalendar = new GregorianCalendar(zone);
			
			alertCalendar.set(alertYear, alertMonth, alertDay, alertHour, alertMin, alertSec);
			truthCalendar.set(truthYear, truthMonth, truthDay, truthHour, truthMin, truthSec);
			
			long alertTime = alertCalendar.getTimeInMillis();
			long startTime = truthCalendar.getTimeInMillis();
			long endTime = startTime + millisecs;
			
			// Relax margins by one second
			startTime -= 1000;
			endTime += 1000;
			
			// OK now compare
			if (alertTime >= startTime && alertTime <= endTime) return true;
	
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * Reads a file into a DOM document
	 * Truth file version
	 */
	private void readTruth(String path) {
		f = new File(path);
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			SAXBuilder builder = new SAXBuilder();
			truthDoc = builder.build(br);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		truthRoot = truthDoc.getRootElement();
		truthNameSpace = truthRoot.getNamespace();
	}
	
	/*
	 * Reads a file into a DOM document
	 * Alert file version
	 */
	private void readAlert(String path) {
		f = new File(path);
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			SAXBuilder builder = new SAXBuilder();
			alertDoc = builder.build(br);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		alertRoot = alertDoc.getRootElement();
		alertNameSpace = alertRoot.getNamespace();
	}
	
}
