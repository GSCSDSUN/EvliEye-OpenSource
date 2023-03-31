/*

 *



 */
package gg.evlieye.analytics;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import com.google.gson.JsonObject;

import gg.evlieye.analytics.dmurph.VisitorData;
import gg.evlieye.util.json.JsonException;
import gg.evlieye.util.json.JsonUtils;
import gg.evlieye.util.json.WsonObject;

public final class AnalyticsConfigFile
{
	private final Path path;
	
	public AnalyticsConfigFile(Path path)
	{
		this.path = path;
	}
	
	public void load(EvlieyeAnalyticsTracker tracker)
	{
		try
		{
			WsonObject wson = JsonUtils.parseFileToObject(path);
			tracker.setEnabled(wson.getBoolean("enabled"));
			tracker.getConfigData().setVisitorData(readVisitorData(wson));
			
		}catch(NoSuchFileException e)
		{
			// The file doesn't exist yet. No problem, we'll create it later.
			
		}catch(IOException | JsonException e)
		{
			System.out.println("Couldn't load " + path.getFileName());
			e.printStackTrace();
		}
		
		save(tracker);
	}
	
	private VisitorData readVisitorData(WsonObject wson) throws JsonException
	{
		int visitorID = wson.getInt("id");
		long firstLaunch = wson.getLong("first_launch");
		long lastLaunch = wson.getLong("last_launch");
		int launches = wson.getInt("launches");
		
		VisitorData visitorData = VisitorData.newSession(visitorID, firstLaunch,
			lastLaunch, launches);
		
		// change visitor ID after 3 days
		if(visitorData.getTimestampCurrent()
			- visitorData.getTimestampFirst() >= 259200)
			visitorData = VisitorData.newVisitor();
		
		return visitorData;
	}
	
	public void save(EvlieyeAnalyticsTracker tracker)
	{
		JsonObject json = createJson(tracker);
		
		try
		{
			JsonUtils.toJson(json, path);
			
		}catch(IOException | JsonException e)
		{
			System.out.println("Couldn't save " + path.getFileName());
			e.printStackTrace();
		}
	}
	
	private JsonObject createJson(EvlieyeAnalyticsTracker tracker)
	{
		JsonObject json = new JsonObject();
		json.addProperty("enabled", tracker.isEnabled());
		
		VisitorData visitorData = tracker.getConfigData().getVisitorData();
		json.addProperty("id", visitorData.getVisitorId());
		json.addProperty("first_launch", visitorData.getTimestampFirst());
		json.addProperty("last_launch", visitorData.getTimestampCurrent());
		json.addProperty("launches", visitorData.getVisits());
		
		return json;
	}
}
