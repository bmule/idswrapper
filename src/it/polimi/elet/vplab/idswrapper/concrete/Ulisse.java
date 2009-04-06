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
package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.gui.TestInterfaceUlisseSS;
import it.polimi.elet.vplab.idswrapper.Main;
import it.polimi.elet.vplab.idswrapper.ids.IDS;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Ulisse
	extends IDS 
{
	
	@SuppressWarnings("unchecked")
		public Ulisse(String IDSpath, String IDSversion, ArrayList input,
			ArrayList output)
		{
			path = IDSpath;
			type = "network";
			name = "Ulisse";
			inputFormat = input;
			outputFormat = output;
			version = IDSversion;
			idsParadigm = "anomaly";
			ruleManager = new UlisseRM();
		}

	public UlisseRM RuleManager()
	{
		return (UlisseRM)ruleManager;
	}

	public UlisseCLAnalysis CommandLineAnalysis() 
	{
		return (UlisseCLAnalysis)commandLineAnalysis;
	}

	public void newCommandLineAnalysis() 
	{
		this.commandLineAnalysis = new UlisseCLAnalysis();
	}

	public UlisseCLTraining CommandLineTraining() 
	{
		return (UlisseCLTraining)commandLineTraining;
	}

	public void newCommandLineTraining() 
	{
		this.commandLineTraining = new UlisseCLTraining();
	}

	//	Testing interface methods
	public TestInterfaceUlisseSS getTestingInterface(String SSBaseName)
	{
		testingInterface = new TestInterfaceUlisseSS();
		return (TestInterfaceUlisseSS) testingInterface;
	}
	
	/*	This method allow to modify Ulisse's XML configuration files with the training GUI
	 * */
	public void changeTrainSettings(String XMLPathAndBase, String basenameFirstPart,
		String ip_address, String net_mask)
	{
		try {
			File file = new File(XMLPathAndBase+"_ia.xml");

			Main.logger.info("Il file XML che ho aperto è "+XMLPathAndBase+"_ia.xml");

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			Main.logger.info("Root element " + doc.getDocumentElement().getNodeName());
			NodeList nodeLst = doc.getElementsByTagName("Variables");

			Node varsNode =  nodeLst.item(0);
			Main.logger.info(varsNode.getNodeName());

			Main.logger.info(((Node) varsNode.getChildNodes().item(21)).getNodeName());
			
			//	Creation of the node with Variable Type ="PLD_CLS"
			Node varNode = (Node) varsNode.getChildNodes().item(21);
			NamedNodeMap attr = varNode.getAttributes();
			Main.logger.info(attr.getNamedItem("XMLBaseName").getNodeValue());
			attr.getNamedItem("XMLBaseName").setNodeValue(basenameFirstPart);

			//	Creation of the node with Variable Type="IP_SRC_SP"
			Node varNodeIP = (Node) varsNode.getChildNodes().item(17);
			NamedNodeMap attrIP = varNodeIP.getAttributes();
			Main.logger.info(attrIP.getNamedItem("NetworkAddress").getNodeValue());
			attrIP.getNamedItem("NetworkAddress").setNodeValue(ip_address);
			Main.logger.info(attrIP.getNamedItem("NetworkMask").getNodeValue());
			attrIP.getNamedItem("NetworkMask").setNodeValue(net_mask);

			//	Save on XML file
			XMLSerializer serializer = new XMLSerializer();
			serializer.setOutputCharStream(new java.io.FileWriter(XMLPathAndBase +
				"_ia.xml"));
			serializer.serialize(doc);

		} catch (Exception e) {
			Main.logger.severe("Error in file access");
		}
	}

	public void analizeTraffic()
	{
		Main.logger.info("Command line: " + this.commandLineAnalysis.getTextualCL());

		String[] cmd;
		int token_number = 0;
		StringTokenizer strToken = new StringTokenizer(this.commandLineAnalysis.getTextualCL());

		while (strToken.hasMoreTokens()) {
			strToken.nextToken();
			token_number++;
		}
		
		cmd = new String[token_number];
		int k = 0;
		strToken = new StringTokenizer(this.commandLineAnalysis.getTextualCL());

		while (strToken.hasMoreTokens()) {
			cmd[k] = strToken.nextToken();
			k++;
		}

		//	Start the analysis activity
		try {            
			//	String cmd = this.commandLine.getTextualCL();
			Process analisysProgess;
			analisysProgess = Runtime.getRuntime().exec(cmd);

			BufferedReader input = new BufferedReader(
				new InputStreamReader(analisysProgess.getInputStream()));
			String line = "";

			while ((line = input.readLine()) != null)
				Main.logger.fine(line);
			
			input.close();
			Main.logger.info("Launched");
		} catch (Throwable t) {
			Main.logger.severe(t.toString());
		}
	}

	public void trainIDS()
	{
		Main.logger.info("Command line: " + this.commandLineTraining.getTextualCL());
		String[] cmd;
		int token_number = 0;
		StringTokenizer strToken = new StringTokenizer(this.commandLineTraining.getTextualCL());

		while (strToken.hasMoreTokens()) {
			strToken.nextToken();
			token_number++;
		}

		cmd = new String[token_number];
		int k = 0;
		strToken = new StringTokenizer(this.commandLineTraining.getTextualCL());

		while (strToken.hasMoreTokens()) {
			cmd[k] = strToken.nextToken();
			k++;
		}

		try {            
			Process trainigProgess;
			trainigProgess = Runtime.getRuntime().exec(cmd);

			BufferedReader input = new BufferedReader(new InputStreamReader(
						trainigProgess.getInputStream()));
			String line = "";

			while ((line = input.readLine()) != null)
				Main.logger.fine(line);
			input.close();

			Main.logger.info("Training launched");
		} catch (Throwable t) {
			Main.logger.severe(t.toString());
		}
	}
}
