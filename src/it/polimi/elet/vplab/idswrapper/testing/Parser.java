package it.polimi.elet.vplab.idswrapper.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/** 
 * 
 * This class specifies how to parse a file of entities: simply returns the 
 * entities, like an iterator. It is a superclass of all the concrete parsers.
 * To add new specific parser, add the class to the package, 
 * then set it to the {@link ParserFactory}.
 * 
 * @author Claudio Magni
 * @version 1.0
 * 
 */

public class Parser {

	/*
	 * entity is a single alert or attack.
	 */
	protected Entity entity;
	protected TimeZone timeZ; // Timezone offset
	
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
	
	protected void closeFile() throws IOException
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
	public void setTimeZone(String timeZone) {
		this.timeZ = TimeZone.getTimeZone(timeZone);
	}
	
	/**
	 * Specifies which file to parse.
	 * 
	 * @param path The complete name of the file.
	 */
	public void setFile(String path) {
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
