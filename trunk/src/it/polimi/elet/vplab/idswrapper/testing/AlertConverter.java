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
 * "idmef-messages.log", un file di log, in un unico file di alert in formato IDMEF 
 * in modo che possa essere valutato dalla funzionalita` di testing dell'IDS preso
 * in esame da IDSWrapper.
 * 
 * @author $Author$
 * @version $Id$
 */
public class AlertConverter
{
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

	/**
	 * Constructs an AlertConverter object. It just calls <code>super()</code>.
	 */
	public AlertConverter()
	{
		super();
	}

	/**
	 * Constructs an AlertConverter object.
	 *
	 * @param alertPath The path of the alert log file.
	 * @param idmefFolder The folder of the IDMEF file.
	 */
	public AlertConverter(String alertPath, String idmefFolder)
	{
		this.alertPath = alertPath;
		this.idmefFolder = idmefFolder;
	}

	/**
	 * Performs the actual conversion.
	 */
	public void convert()
	{
		log = new File(this.alertPath);
		xml = new File(this.idmefFolder + "/alert.xml");

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
					}
				}
			} else 
				System.out.println("Errore nel file!");

			while(line != null) {
				if(line.compareTo("</IDMEF-Message>") == 0) {
					line = br.readLine();

					if(line == null)
						bw.write("</IDMEF-Message>");
					else if (line.compareTo("<?xml version=\"1.0\"?>") == 0) {
						line = br.readLine();

						if(line.compareTo("<!DOCTYPE IDMEF-Message PUBLIC \"-" +
									"//IETF//DTD RFC XXXX IDMEF v1.0//EN\"" +
									" \"/usr/local/share/idmef-message.dtd\">") == 0) {

							line = br.readLine();

							if(line.compareTo("<IDMEF-Message version=\"1.0\">") == 0)
								line = br.readLine();
						}
					} 
				}

				if(line != null)
					bw.write(line+"\n");
				
				line = br.readLine();
				bw.flush();
			}

			this.closeLogFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the log file.
	 */
	private void closeLogFile()
		throws IOException
	{
		fis.close();
		isr.close();
		br.close();
	}
}
