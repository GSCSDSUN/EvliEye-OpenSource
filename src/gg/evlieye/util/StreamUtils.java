/*

 *



 */
package gg.evlieye.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public enum StreamUtils
{
	;
	
	public static ArrayList<String> readAllLines(InputStream input)
		throws IOException
	{
		try(BufferedReader br =
			new BufferedReader(new InputStreamReader(input)))
		{
			ArrayList<String> lines = new ArrayList<>();
			String line;
			
			while((line = br.readLine()) != null)
				lines.add(line);
			
			return lines;
		}
	}
}
