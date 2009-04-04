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
package it.polimi.elet.vplab.idswrapper.filemanager;

import java.io.*;

public class ConfigureUlPreprocessor {
	
	
	private String IDSpath;
	private File configureFile;
	private File tempFile;
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;
	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	
	
	public ConfigureUlPreprocessor(String IDSpath) {
		
		this.IDSpath = IDSpath;
		
		if(this.IDSpath.endsWith("/")) {
			
			configureFile = new File(IDSpath+"etc/snort.conf");
			tempFile = new File(IDSpath+"etc/temp.conf");
			
		}
			
		else {
			
			configureFile = new File(IDSpath+"/etc/snort.conf");
			tempFile = new File(IDSpath+"/etc/temp.conf");
			
		}
			
	}
	
	public boolean setParameters(String baseName, String sensitivity, String detectionRate) {
		
		try {
			
			fis = new FileInputStream(configureFile);
			fos = new FileOutputStream(tempFile);
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		
		}
		
		isr = new InputStreamReader(fis);
		osw = new OutputStreamWriter(fos);
		
		br = new BufferedReader(isr);
		bw = new BufferedWriter(osw);
		
		try {
			
			boolean flag = true;
			String line = br.readLine();
			
			while (line != null) {
								
				if (line.length() >= 30) {
					
					String ulPreproc = line.substring(0, 30);
					
					if(ulPreproc.compareTo("preprocessor unsupervised_IDS:") == 0) {
						
						bw.write(ulPreproc+" "+baseName+","+sensitivity+","+detectionRate+"\n");
						
						flag = false;
						
					}
					
				}
				
				if (flag) 
					
					bw.write(line+"\n");
					
				else
				
					flag = true;
					
				bw.flush();
				
				line = br.readLine();
				
			}
			
			//Fase di sostituzione del file snort.conf con temp.conf
			
			tempFile.renameTo(configureFile);
			
			//Chiusura degli stream e dei file
			
			br.close();
			isr.close();
			fis.close();
			
			bw.close();
			osw.close();
			fos.close();
			
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return true;
		
	}

}
