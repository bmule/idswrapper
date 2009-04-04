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

import it.polimi.elet.vplab.idswrapper.concrete.IDSWrapper;

import java.util.TimeZone;

public class TruthConverter {
	
	private IDSWrapper wrapper;
	private String truthPath;
	private String idmefFolder;
	private String timeZone = "GMT";
	private int MAX_ATTACKS = 20;
	
	/**
	 * Creates a new converter.
	 *
	 * @param wrapper The IDSWrapper instance.
	 * @param truthPath Path to the truth file.
	 * @param idmefFolder Folder of the IDMEF files.
	 * @param timeOffset Time(zone) offset.
	 * @param attacksNumber Number of attacks.
	 * 
	 */
	public TruthConverter(IDSWrapper wrapper, String truthPath, String idmefFolder, 
			String timeOffset, int attacksNumber)
	{
		this.wrapper= wrapper;
		this.truthPath = truthPath;
		this.idmefFolder = idmefFolder;
		this.timeZone = timeZone + timeOffset;
		this.MAX_ATTACKS = attacksNumber;
	}

	/**
	 * Performs the actual conversion.
	 */
	public void convert()
	{
		TruthFile99Parser parser = new TruthFile99Parser();
		parser.setFile(truthPath);
		parser.setTimeZone(timeZone);
		
		IDMEF msg = new IDMEF();
		
		Attack attack;
		attack = (Attack) parser.getNextEntity();
		
		int i = 0;
		
		while(attack != null && i < MAX_ATTACKS) {
			for (int j = 0; j < attack.getTargetIP().length && i < MAX_ATTACKS ; j ++) {
				
				String targetIP = attack.getTargetIP()[j];
				long[] times = attack.getTime();
				String duration = String.valueOf(attack.getDuration()[j]);
				
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
	}
	
}
