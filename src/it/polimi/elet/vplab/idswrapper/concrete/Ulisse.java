package it.polimi.elet.vplab.idswrapper.concrete;

import it.polimi.elet.vplab.idswrapper.ids.IDS;
import it.polimi.elet.vplab.idswrapper.gui.TestInterfaceUlisseSS;

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
	//	Attributo relativo all'interfaccia di testing (molto specifica per Ulisse, non generalizzabile per altri IDS)
//	TestInterfaceUlisseSS testingInterface;
	
	@SuppressWarnings("unchecked")
	public Ulisse(String IDSpath, String IDSversion, ArrayList input, ArrayList output)
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
	
	//	Metodi relativi all'interfaccia di testing
	public TestInterfaceUlisseSS getTestingInterface(String SSBaseName)
	{
		testingInterface = new TestInterfaceUlisseSS();
		return (TestInterfaceUlisseSS) testingInterface;
	}
	
	//	Il seguente metodo permette di modificare i file XML di configurazione di Ulisse evitando l'edit manuale
	//	I dati relativi all'indirizzo ip e alla netmask saranno forniti dall'utente (tramite l'interfaccia) al momento
	//	della configurazione della fase di training.
	public void changeTrainSettings(String XMLPathAndBase, String basenameFirstPart, String ip_address, String net_mask)
	{
		try
		{
			File file = new File(XMLPathAndBase+"_ia.xml");
System.out.println("Il file XML che ho aperto è "+XMLPathAndBase+"_ia.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			NodeList nodeLst = doc.getElementsByTagName("Variables");
			
			Node varsNode =  nodeLst.item(0);
System.out.println(varsNode.getNodeName());
			
System.out.println(((Node) varsNode.getChildNodes().item(21)).getNodeName());
			//	Creazione del nodo con Variable Type="PLD_CLS"
			Node varNode = (Node) varsNode.getChildNodes().item(21);
			NamedNodeMap attr = varNode.getAttributes();
System.out.println(attr.getNamedItem("XMLBaseName").getNodeValue());
			attr.getNamedItem("XMLBaseName").setNodeValue(basenameFirstPart);
			
			//	Creazione del nodo con Variable Type="IP_SRC_SP"
			Node varNodeIP = (Node) varsNode.getChildNodes().item(17);
			NamedNodeMap attrIP = varNodeIP.getAttributes();
System.out.println(attrIP.getNamedItem("NetworkAddress").getNodeValue());
			attrIP.getNamedItem("NetworkAddress").setNodeValue(ip_address);
System.out.println(attrIP.getNamedItem("NetworkMask").getNodeValue());
			attrIP.getNamedItem("NetworkMask").setNodeValue(net_mask);
			
			//	Salvataggio delle modifiche nel file XML
			XMLSerializer serializer = new XMLSerializer();
			serializer.setOutputCharStream(new java.io.FileWriter(XMLPathAndBase+"_ia.xml"));
			//	debug: serializer.setOutputCharStream(new java.io.FileWriter(XMLPathAndBase+"_ia2.xml"));
			serializer.serialize(doc);
				
		} 
		catch (Exception e)
		{
		//	e.printStackTrace();
			System.out.println("Error in file access");
		}
	}
	
	public void analizeTraffic()
	{
		//	Creazione di un vettore di String a partire dalla CL presente nell'attributo dell'IDS
System.out.println("La command line che eseguirà l'IDS è: "+this.commandLineAnalysis.getTextualCL());
		String[] cmd;
		int token_number = 0;
		StringTokenizer strToken = new StringTokenizer(this.commandLineAnalysis.getTextualCL());

		while(strToken.hasMoreTokens())
		{
			strToken.nextToken();
			token_number++;
		}
		cmd = new String[token_number];
		int k = 0;
		strToken = new StringTokenizer(this.commandLineAnalysis.getTextualCL());
		while(strToken.hasMoreTokens())
		{
			cmd[k] = strToken.nextToken();
			k++;
		}
		
		//	Lancio dell'attività di analisi
		try
        {            
		//	String cmd = this.commandLine.getTextualCL();
			Process analisysProgess;
			analisysProgess = Runtime.getRuntime().exec(cmd);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(analisysProgess.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) 
		    {
		        System.out.println(line);
		    }
		      input.close();
		   
			System.out.println("lanciato...");
        } catch (Throwable t)
          {
        	System.out.println("ERRORE");
            t.printStackTrace();
          }
	}
	
	public void trainIDS()
	{
		System.out.println("La command line che eseguirà l'IDS è: "+this.commandLineTraining.getTextualCL());
		String[] cmd;
		int token_number = 0;
		StringTokenizer strToken = new StringTokenizer(this.commandLineTraining.getTextualCL());

		while(strToken.hasMoreTokens())
		{
			strToken.nextToken();
			token_number++;
		}
		cmd = new String[token_number];
		int k = 0;
		strToken = new StringTokenizer(this.commandLineTraining.getTextualCL());
		while(strToken.hasMoreTokens())
		{
			cmd[k] = strToken.nextToken();
			k++;
		}
		
		//	Lancio l'attività di training
		try
        {            
			Process trainigProgess;
			trainigProgess = Runtime.getRuntime().exec(cmd);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(trainigProgess.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) 
		    {
		        System.out.println(line);
		    }
		      input.close();
		   
		      
			System.out.println("training lanciato...");
        } catch (Throwable t)
          {
        	System.out.println("ERRORE");
            t.printStackTrace();
          }
	}
}
