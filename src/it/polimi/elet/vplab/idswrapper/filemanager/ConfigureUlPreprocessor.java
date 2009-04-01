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
