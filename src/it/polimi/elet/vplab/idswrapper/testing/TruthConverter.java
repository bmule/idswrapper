package it.polimi.elet.vplab.idswrapper.testing;

import it.polimi.elet.vplab.idswrapper.concrete.IDSWrapper;

import java.util.TimeZone;

public class TruthConverter {
	
	private IDSWrapper wrapper;
	private String truthPath;
	private String idmefFolder;
	private String timeZone = "GMT";
	private int MAX_ATTACKS = 20;
	
	public TruthConverter() {
		
	}
	
	public TruthConverter(IDSWrapper wrapper, String truthPath, String idmefFolder, 
			String timeOffset, int attacksNumber) {
		
		this.wrapper= wrapper;
		this.truthPath = truthPath;
		this.idmefFolder = idmefFolder;
		this.timeZone = timeZone + timeOffset;
		this.MAX_ATTACKS = attacksNumber;
				
	}
	
	
	
	public void convert() {
		
		TruthFile99Parser parser = new TruthFile99Parser();
		parser.setFile(truthPath);
		parser.setTimeZone(timeZone);
		
		//System.out.println("Parser Inizializzato.");
		//System.out.println("Inizio conversione...");
			
		IDMEF msg = new IDMEF();
		
		Attack attack;
		attack = (Attack) parser.getNextEntity();
		
		int i = 0;
		
		while(attack != null && i < MAX_ATTACKS) {
			
			for (int j = 0; j < attack.getTargetIP().length && i < MAX_ATTACKS ; j ++) {
				
				//Estraggo i parametri degli attacchi contenuti nel truth file
				//e le caratteristiche dell'IDS usato da IDSWrapper
				
				String targetIP = attack.getTargetIP()[j];
				long[] times = attack.getTime();
				String duration = String.valueOf(attack.getDuration()[j]);
				
				//Attributi dell'IDS che utilizza IDSWrapper.
				String idsName = wrapper.getIDS().getName();
				String idsCategory = wrapper.getIDS().getType();
				String idsLocation = wrapper.getIDS().getPath();
				
				msg.addAlert();
				msg.setAnalyzer(idsCategory, idsLocation, idsName);
				msg.setCreateTime(times[j], TimeZone.getTimeZone("GMT"));
				msg.setTarget(targetIP, null, null);
				msg.setClassification(attack.getName());
				msg.setAdditionalData("duration", duration);
				
				i++;
				
			}
			
			attack = (Attack) parser.getNextEntity();
						
		}
		
		msg.output(idmefFolder+"/truth.xml");
		//System.out.println("Conversione terminata.");
		
	}
	
}
