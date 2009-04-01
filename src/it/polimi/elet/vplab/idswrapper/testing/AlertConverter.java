package it.polimi.elet.vplab.idswrapper.testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

	/**
	 * Lo scopo di questa classe è quello di convertire un file particolare chiamato
	 * "idmef-messages.log",un file di log, in un unico file di alert in formato IDMEF 
	 * in modo che possa essere valutato dalla funzionalità di testing dell'IDS preso
	 * in esame da IDSWrapper.
	 * 
	 * @author Davide Polino
	 * @version 1.0
	 *
	 */
public class AlertConverter {
	private String alertPath;
	private String idmefFolder;
	private File log;
	private File xml;
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;
	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	
	public AlertConverter()
	{
				
	}
	
	public AlertConverter(String alertPath, String idmefFolder)
	{
		this.alertPath = alertPath;
		this.idmefFolder = idmefFolder;
	}
	
	public void convert()
	{
		log = new File(this.alertPath);
		xml = new File(this.idmefFolder+"/alert.xml");
		
		try {
			
			fis = new FileInputStream(log);
			fos = new FileOutputStream(xml);
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		
		}
		
		isr = new InputStreamReader(fis);
		osw = new OutputStreamWriter(fos);
		
		br = new BufferedReader(isr);
		bw = new BufferedWriter(osw);
		
		try {
			
			String line = br.readLine();
			
			if (line.compareTo("<?xml version=\"1.0\"?>") == 0) {
				
				bw.write(line+"\n");
				line = br.readLine();
				
				if(line.compareTo("<!DOCTYPE IDMEF-Message PUBLIC \"-" +
						"//IETF//DTD RFC XXXX IDMEF v1.0//EN\"" +
						" \"/usr/local/share/idmef-message.dtd\">") == 0) {
					
					//bw.write(line+"\n");
					line = br.readLine();
					
					if(line.compareTo("<IDMEF-Message version=\"1.0\">") == 0) {
						
						bw.write(line+"\n");
						line = br.readLine();
						//System.out.println("1° Test OK!");
						
					}
					
				}
			
			}
			
			else {
				
				System.out.println("Errore nel file!");
				
			}
			
			while(line != null) {
				
				if(line.compareTo("</IDMEF-Message>") == 0) {
					
					line = br.readLine();
					
					if(line == null) {
						
						bw.write("</IDMEF-Message>");
						//System.out.println("Sono arrivato alla fine del log.");
												
					}
					
					else if (line.compareTo("<?xml version=\"1.0\"?>") == 0) {
						
						line = br.readLine();
						
						if(line.compareTo("<!DOCTYPE IDMEF-Message PUBLIC \"-" +
								"//IETF//DTD RFC XXXX IDMEF v1.0//EN\"" +
								" \"/usr/local/share/idmef-message.dtd\">") == 0) {
							
							line = br.readLine();
							
							if(line.compareTo("<IDMEF-Message version=\"1.0\">") == 0) {
								
								line = br.readLine();
																
							}
							
						}
					
					} 
					
				}
				
				if(line != null)
					bw.write(line+"\n");
				line = br.readLine();
				
				bw.flush();
				
			}
			
			this.closeLogFile();
			
			//System.out.println("Conversione terminata.");
			
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void closeLogFile()
		throws IOException
	{
		fis.close();
		isr.close();
		br.close();
	}

	
}
