/*

 *



 */
package gg.evlieye.navigator;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

import gg.evlieye.util.json.JsonException;
import gg.evlieye.util.json.JsonUtils;
import gg.evlieye.util.json.WsonObject;

public final class PreferencesFile
{
	private final Path path;
	private final HashMap<String, Long> preferences;
	
	public PreferencesFile(Path path, HashMap<String, Long> preferences)
	{
		this.path = path;
		this.preferences = preferences;
	}
	
	public void load()
	{
		try
		{
			WsonObject wson = JsonUtils.parseFileToObject(path);
			
			for(Entry<String, Number> e : wson.getAllNumbers().entrySet())
				preferences.put(e.getKey(), e.getValue().longValue());
			
		}catch(NoSuchFileException e)
		{
			// The file doesn't exist yet. No problem, we'll create it later.
			
		}catch(IOException | JsonException e)
		{
			System.out.println("Couldn't load " + path.getFileName());
			e.printStackTrace();
		}
		
		save();
	}
	
	public void save()
	{
		JsonObject json = createJson();
		
		try
		{
			JsonUtils.toJson(json, path);
			
		}catch(IOException | JsonException e)
		{
			System.out.println("Couldn't save " + path.getFileName());
			e.printStackTrace();
		}
	}
	
	private JsonObject createJson()
	{
		JsonObject json = new JsonObject();
		
		for(Entry<String, Long> e : preferences.entrySet())
			json.addProperty(e.getKey(), e.getValue());
		
		return json;
	}
}
