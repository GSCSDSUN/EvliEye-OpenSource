/*

 *



 */
package gg.evlieye;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

import com.google.gson.JsonArray;

import gg.evlieye.util.json.JsonException;
import gg.evlieye.util.json.JsonUtils;
import gg.evlieye.util.json.WsonArray;

public final class TooManyHaxFile
{
	private final Path path;
	private final ArrayList<Feature> blockedFeatures;
	
	public TooManyHaxFile(Path path, ArrayList<Feature> blockedFeatures)
	{
		this.path = path;
		this.blockedFeatures = blockedFeatures;
	}
	
	public void load()
	{
		try
		{
			WsonArray wson = JsonUtils.parseFileToArray(path);
			setBlockedFeatures(wson);
			
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
	
	public void loadProfile(Path profilePath) throws IOException, JsonException
	{
		if(!profilePath.getFileName().toString().endsWith(".json"))
			throw new IllegalArgumentException();
		
		WsonArray wson = JsonUtils.parseFileToArray(profilePath);
		setBlockedFeatures(wson);
		
		save();
	}
	
	private void setBlockedFeatures(WsonArray wson)
	{
		blockedFeatures.clear();
		
		for(String name : wson.getAllStrings())
		{
			Feature feature = EvlieyeClient.INSTANCE.getFeatureByName(name);
			
			if(feature != null && feature.isSafeToBlock())
				blockedFeatures.add(feature);
		}
		
		blockedFeatures
			.sort(Comparator.comparing(f -> f.getName().toLowerCase()));
	}
	
	public void save()
	{
		JsonArray json = createJson();
		
		try
		{
			JsonUtils.toJson(json, path);
			
		}catch(IOException | JsonException e)
		{
			System.out.println("Couldn't save " + path.getFileName());
			e.printStackTrace();
		}
	}
	
	public void saveProfile(Path profilePath) throws IOException, JsonException
	{
		if(!profilePath.getFileName().toString().endsWith(".json"))
			throw new IllegalArgumentException();
		
		JsonArray json = createJson();
		Files.createDirectories(profilePath.getParent());
		JsonUtils.toJson(json, profilePath);
	}
	
	private JsonArray createJson()
	{
		JsonArray json = new JsonArray();
		blockedFeatures.stream().filter(Feature::isSafeToBlock)
			.map(Feature::getName).forEach(name -> json.add(name));
		
		return json;
	}
}
