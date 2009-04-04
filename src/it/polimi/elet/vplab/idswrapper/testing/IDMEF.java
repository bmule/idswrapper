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
package it.polimi.elet.vplab.idswrapper.testing;

import java.util.*;
import java.io.*;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;

/**
 * Module to build a <b>IDMEF</b> message.
 *
 * IDMEF is an XML based protocol designed mainly for representing Intrusion 
 * Detection (ID) alert messages. It has been designed for simplifying the task
 * of translating a key-value based format to its IDMEF representation, which
 * is the most common situation when writing a log export module for a given IDS
 * software. A typical session involves the creation of a new IDMEF message, the
 * initialisation of some of its fields and the addition of new IDMEF tags to this
 * message. Everything is handled through the DOM abstraction (not suitable
 * for large files).
 *
 * The possibility to output an IDMEF file is also provided.
 * 
 * @author Claudio Magni
 * @version $Id$
 */
public class IDMEF {

	// TODO unique ids
	// TODO ntpstamp (try to handle millisecs)
	// TODO check DTD

	private Document doc;
	// private DocType type; // Not used now
	private Namespace nameSpace;
	private Element root;
	private Element alert;
	private Element heartbeat;
	private boolean openAlert = false;
	private boolean openHeartbeat = false;

	/*
	 * Variables needed to handle file
	 */
	private String fileName;
	private File f;
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;


	/**
	 * Creates a new empty IDMEF message.
	 */
	public IDMEF () {
		nameSpace = Namespace.getNamespace("idmef", "http://iana.org/idmef");
		root = new Element("IDMEF-Message", nameSpace);
		root.setAttribute("version", "1.0");
		root.addNamespaceDeclaration(nameSpace);
		doc = new Document(root);
	}

	/**
	 * Creates a new IDMEF message from a file. File has to be correct XML.
	 * 
	 * @param source The path of the source file.
	 */
	public IDMEF (String source) {
		fileName = source;
		f = new File(fileName);
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(br);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		root = doc.getRootElement();
		nameSpace = root.getNamespace();
	}

	/*
	 * Handle the adding of content
	 */
	private void add(Element a) {
		if (openAlert) alert.addContent(a);
		else if (openHeartbeat) heartbeat.addContent(a);
	}

	/*
	 * Keep numbers in 2digit format, used for time handling
	 */
	private String handleZero(int time) {
		Integer tim = new Integer(time);
		String buffer = tim.toString();
		if (buffer.length() == 1) return "0" + buffer;
		return buffer;
	}


	/**
	 * Adds a new empty alert. The class is appended at the end of the message.
	 */
	public void addAlert() {
		alert = new Element("Alert", nameSpace);
		// TODO add messageid too?
		root.addContent(alert);
		openAlert = true;
		openHeartbeat = false;
	}

	/**
	 * Adds a new empty heart beat. The class is appended at the end of the message.
	 */
	public void addHeartBeat() {
		heartbeat = new Element("Heartbeat", nameSpace);
		// TODO add messageid too?
		root.addContent(heartbeat);
		openAlert = false;
		openHeartbeat = true;
	}

	/**
	 * Adds the Analyzer to last added class (alert or heartbeat).
	 * 
	 * @param category The category of the node (null permitted).
	 * @param location The location of the node (null permitted).
	 * @param name The name (like dns name) of the node (null permitted).
	 */
	public void setAnalyzer(String category, String location, String name) {
		Element analyzer = new Element("Analyzer", nameSpace);
		Element node = new Element("Node", nameSpace);
		// TODO add analyzerid too?
		if (category != null) {
			node.setAttribute("category", category);
		}
		if (location != null) {
			Element loc = new Element("location", nameSpace);
			loc.setText(location);
			node.addContent(loc);
		}
		if (name != null) {
			Element nm = new Element("name", nameSpace);
			nm.setText(name);
			node.addContent(nm);
		}

		// Finally append the class to the document
		if (category != null || location != null || name != null) {
			analyzer.addContent(node);

			this.add(analyzer);

		}
	}

	/**
	 * Adds the time of creation of the last added class (alert or heartbeat).
	 * 
	 * @param timeStamp Timestamp in milliseconds from 1970 UTC.
	 * @param timeZone The Timezone of the system.
	 */
	public void setCreateTime(long timeStamp, TimeZone timeZone)
	{
		this.setTime(timeStamp, timeZone, "CreateTime");
	}

	/**
	 * Adds the time of detection of the last added class (alert or heartbeat).
	 * 
	 * @param timeStamp Timestamp in milliseconds from 1970 UTC.
	 * @param timeZone The Timezone of the system.
	 */
	public void setDetectTime(long timeStamp, TimeZone timeZone)
	{
		this.setTime(timeStamp, timeZone, "DetectTime");
	}

	/*
	 * Adds a Time class, whether CreateTime or DetectTime
	 */
	private void setTime(long timeStamp, TimeZone timeZone, String type)
	{
		Calendar calendar;
		TimeZone zone;
		if (timeZone == null) zone = TimeZone.getDefault();
		else zone = timeZone;
		calendar = new GregorianCalendar(zone);

		calendar.setTimeInMillis(timeStamp);
		String value = "";
		// Format: YYYY-MM-DDThh:mm:ss.ss+hh:mm
		value += calendar.get(Calendar.YEAR) + "-";
		value += handleZero(calendar.get(Calendar.MONTH)) + "-";
		value += handleZero(calendar.get(Calendar.DAY_OF_MONTH));
		value += "T";
		value += handleZero(calendar.get(Calendar.HOUR_OF_DAY)) + ":";
		value += handleZero(calendar.get(Calendar.MINUTE)) + ":";
		value += handleZero(calendar.get(Calendar.SECOND));// + ".";
		//value += calendar.get(Calendar.MILLISECOND);

		// Handle time zone
		Integer offset = (zone.getRawOffset() / 3600000);
		if (offset == 0) value += "Z";
		else {
			if (offset > 0) value += "+";
			else if (offset < 0) value += "-";
			value += handleZero(offset) + ":00";
		}

		Element time = new Element(type, nameSpace);

		// Damn code for the ntpstamp format
		Calendar temp = Calendar.getInstance();
		temp.set(1900, 1, 1, 0, 0, 0); // this will be negative
		String hex = Long.toHexString((- temp.getTimeInMillis() + timeStamp) / 1000 +
			1000000000);
		String hex1 = hex;
		String hex2 = "00000000";

		if (hex.length() > 8) {
			hex1 = hex.substring(0, 8);
			hex2 = hex.substring(8, hex.length());
			if (hex2.length() < 8) {
				int pad = 8 - hex2.length();
				for (int i = 0; i < pad; i++) {
					hex2 += "0";
				}
			}
		}
		hex1 = "0x" + hex1;
		hex2 = "0x" + hex2;
		time.setAttribute("ntpstamp", hex1 + "." + hex2);
		time.setText(value);

		this.add(time);
	}

	/**
	 * Adds source to the last added class (alert or heartbeat).
	 * 
	 * @param spoofed Whether it was spoofed or not.
	 * @param address IP of the node.
	 * @param name Name (= like dns name) of the node.
	 * @param location Location of the node.
	 */
	public void setSource(String address, String name, String location)
	{
		this.setHost(true, address, name, location);
	}

	/**
	 * Adds target to the last added class (alert or heartbeat).
	 * 
	 * @param decoy Whether it is decoy or not.
	 * @param address IP of the node.
	 * @param name Name (= like dns name) of the node.
	 * @param location Location of the node.
	 */
	public void setTarget(String address, String name, String location)
	{
		this.setHost(false, address, name, location);
	}

	/*
	 * Adds source or target host
	 */
	private void setHost(boolean source, String address, String name, String location)
	{
		Element host;
		if (source) {
			host = new Element("Source", nameSpace);
		} else {
			host = new Element("Target", nameSpace);
		}
		Element node = new Element("Node", nameSpace);
		host.addContent(node);

		if (address != null) {
			Element addr1 = new Element("Address", nameSpace);
			Element addr2 = new Element("address", nameSpace);
			addr2.setText(address);
			// TODO check Address category
			addr1.addContent(addr2);
			node.addContent(addr1);
		}
		if (location != null) {
			Element loc = new Element("location", nameSpace);
			loc.setText(location);
			node.addContent(loc);
		}
		if (name != null) {
			Element nm = new Element("name", nameSpace);
			nm.setText(name);
			node.addContent(nm);
		}

		this.add(host);
	}

	/**
	 * Adds classification to the last added class (alert or heartbeat).
	 * 
	 * @param value The content of the classification.
	 */
	public void setClassification(String value)
	{
		if (value != null) {
			Element classification = new Element("Classification", nameSpace);
			classification.setAttribute("text", value);

			this.add(classification);		
		}
	}

	/**
	 * Adds additional data to the last added class (alert or heartbeat).
	 * 
	 * @param meaning A string explaining how to interpret this info.
	 * @param value The data itself.
	 */
	public void setAdditionalData(String meaning, String value)
	{
		Element add = new Element("AdditionalData", nameSpace);
		add.setAttribute("meaning", meaning);
		add.setText(value);

		this.add(add);
	}


	/**
	 * Returns a String representing the entire message (a well-formed XML).
	 * 
	 * @return The IDMEF message.
	 */
	public String getString()
	{
		XMLOutputter outp = new XMLOutputter(Format.getPrettyFormat());
		return outp.outputString(doc);
	}

	/**
	 * Store message into the xml file supplied.
	 * 
	 * @param path The path of the file to write.
	 */
	public void output(String path)
	{
		File f = new File(path);
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));

			XMLOutputter outXML = new XMLOutputter(Format.getPrettyFormat());
			outXML.output(doc, out);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
