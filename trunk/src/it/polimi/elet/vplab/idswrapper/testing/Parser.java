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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/** 
 * This class specifies how to parse a file of entities: simply returns the 
 * entities, like an iterator. It is a superclass of all the concrete parsers.
 *
 * To add new specific parser, add the class to the package, 
 * then set it to the {@link ParserFactory}.
 * 
 * @author Claudio Magni
 * @version $Id$
 */

public class Parser {

	/*
	 * entity is a single alert or attack.
	 */
	protected Entity entity;
	protected TimeZone timeZ;
	
	/*
	 * Variables needed to handle file and lines
	 */
	protected String file_name;
	protected File f;
	protected FileInputStream fis;
	protected InputStreamReader isr;
	protected BufferedReader br;
	
	
	/**
	 * Creates a new parser.
	 */
	public Parser()
	{
		super();
	}

	protected void closeFile()
		throws IOException
	{
		fis.close();
		isr.close();
		br.close();
	}
	
	/**
	 * Specifies the timeZone (practically the offset in hours) to apply to all dates.
	 * Proper format is "GMT+hh" or "GMT-hh".
	 * 
	 * @param timeZone The time zone string.
	 */
	public void setTimeZone(String timeZone)
	{
		this.timeZ = TimeZone.getTimeZone(timeZone);
	}
	
	/**
	 * Specifies which file to parse.
	 * 
	 * @param path The complete name of the file.
	 */
	public void setFile(String path)
	{
		file_name = path;
		f = new File(file_name);

		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		isr = new InputStreamReader(fis);
		br = new BufferedReader(isr);
	}
	
	/**
	 * Retrieves next entity (attack) in the file.
	 * 
	 * @return The next entity.
	 * @throws IOException
	 */
	public Entity getNextEntity ()
	{
		return null;
	}
}
