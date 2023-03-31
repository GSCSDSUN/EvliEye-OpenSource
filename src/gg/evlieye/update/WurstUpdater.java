/*

 *



 */
package gg.evlieye.update;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.util.ChatUtils;
import gg.evlieye.util.json.JsonException;
import gg.evlieye.util.json.JsonUtils;
import gg.evlieye.util.json.WsonArray;
import gg.evlieye.util.json.WsonObject;

public final class EvlieyeUpdater implements UpdateListener
{
	private Thread thread;
	private boolean outdated;
	private Text component;
	
	@Override
	public void onUpdate()
	{
		if(thread == null)
		{
			thread = new Thread(this::checkForUpdates, "EvlieyeUpdater");
			thread.start();
			return;
		}
		
		if(thread.isAlive())
			return;
		
		if(component != null)
			ChatUtils.component(component);
		
		EvlieyeClient.INSTANCE.getEventManager().remove(UpdateListener.class,
			this);
	}
	
	public void checkForUpdates()
	{
		Version currentVersion = new Version(EvlieyeClient.VERSION);
		Version latestVersion = null;
		
		try
		{
			WsonArray wson = JsonUtils.parseURLToArray(
				"https://api.github.com/repos/Evlieye-Imperium/Evlieye-MCX2/releases");
			
			for(WsonObject release : wson.getAllObjects())
			{
				if(!currentVersion.isPreRelease()
					&& release.getBoolean("prerelease"))
					continue;
				
				if(!containsCompatibleAsset(release.getArray("assets")))
					continue;
				
				String tagName = release.getString("tag_name");
				latestVersion = new Version(tagName.substring(1));
				break;
			}
			
			if(latestVersion == null)
				throw new NullPointerException("Latest version is missing!");
			
			System.out.println("[Updater] Current version: " + currentVersion);
			System.out.println("[Updater] Latest version: " + latestVersion);
			outdated = currentVersion.shouldUpdateTo(latestVersion);
			
		}catch(Exception e)
		{
			System.err.println("[Updater] An error occurred!");
			e.printStackTrace();
		}
		
		if(latestVersion == null || latestVersion.isInvalid())
		{
			String text = "An error occurred while checking for updates."
				+ " Click \u00a7nhere\u00a7r to check manually.";
			String url =
				"https://www.wurstclient.net/download/?utm_source=Evlieye+Client&utm_medium=EvlieyeUpdater+chat+message&utm_content=An+error+occurred+while+checking+for+updates.";
			showLink(text, url);
			return;
		}
		
		if(!outdated)
			return;
		
		String textPart1 = "Evlieye " + latestVersion + " MC"
			+ EvlieyeClient.MC_VERSION + " is now available.";
		String text =
			textPart1 + " Click \u00a7nhere\u00a7r to download the update.";
		String url =
			"https://www.wurstclient.net/download/?utm_source=Evlieye+Client&utm_medium=EvlieyeUpdater+chat+message&utm_content="
				+ URLEncoder.encode(textPart1, StandardCharsets.UTF_8);
		showLink(text, url);
	}
	
	private void showLink(String text, String url)
	{
		ClickEvent event = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
		component = Text.literal(text).styled(s -> s.withClickEvent(event));
	}
	
	private boolean containsCompatibleAsset(WsonArray wsonArray)
		throws JsonException
	{
		String compatibleSuffix = "MC" + EvlieyeClient.MC_VERSION + ".jar";
		
		for(WsonObject asset : wsonArray.getAllObjects())
		{
			String assetName = asset.getString("name");
			if(!assetName.endsWith(compatibleSuffix))
				continue;
			
			return true;
		}
		
		return false;
	}
	
	public boolean isOutdated()
	{
		return outdated;
	}
}
