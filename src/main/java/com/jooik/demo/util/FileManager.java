package com.jooik.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 * File related operations simplifying our daily work with files and directories,
 * such as recursive directory deleteion etc...
 */
public class FileManager
{
	// ------------------------------------------------------------------------
	// members
	// ------------------------------------------------------------------------

	// log4j...
	static final Logger logger = Logger.getLogger(FileManager.class);
	
	// ------------------------------------------------------------------------
	// public usage
	// ------------------------------------------------------------------------

	/**
	 * Delete entire directory recursively...
	 * @param directory
	 * @return
	 */
	public boolean deleteDirectory(String directory)
	{
		File file = new File(directory);
		return deleteDir(file);
	}
	
	/**
	 * Delete entire directory recursively...
	 * @param dir
	 * @return
	 */
	public boolean deleteDir(File dir) 
	{
	    if (dir.isDirectory()) 
	    {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) 
	        {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) 
	            {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}
	
	/**
	 * Read text file to string...
	 * @param absolutePath
	 * @return
	 */
	public static String readTextFileIntoString(String absolutePath)
	{
		StringBuffer contents = new StringBuffer();
		
		try 
		{
			File fileDir = new File(absolutePath);
	 
			BufferedReader in = new BufferedReader(
			   new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF8"));
	 
			String str;
	 
			while ((str = in.readLine()) != null) 
			{
			    contents.append(str);
			    contents.append(System.getProperty("line.separator"));
			}
	 
	        in.close();
		} 
		catch (UnsupportedEncodingException e) 
		{
			logger.error(e.getMessage());
		} 
		catch (IOException e) 
		{
			logger.error(e.getMessage());
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		
		return contents.toString();
	}
	
	/**
	 * Convert file to byte array.
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] convertFileToByteArray(File file)
		throws FileNotFoundException, IOException
	{
		FileInputStream in = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];    
		in.read(data);
		in.close();
		
		return data;
	}
	
	// ------------------------------------------------------------------------
	// private usage
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// GETTER & SETTER
	// ------------------------------------------------------------------------
}
