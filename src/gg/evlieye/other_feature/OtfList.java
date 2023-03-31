/*

 *



 */
package gg.evlieye.other_feature;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.TreeMap;

import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import gg.evlieye.other_features.*;

public final class OtfList
{
	public final ChangelogOtf changelogOtf = new ChangelogOtf();
	public final CleanUpOtf cleanUpOtf = new CleanUpOtf();
	public final DisableOtf disableOtf = new DisableOtf();
	public final HackListOtf hackListOtf = new HackListOtf();
	public final KeybindManagerOtf keybindManagerOtf = new KeybindManagerOtf();
	public final LastServerOtf lastServerOtf = new LastServerOtf();
	public final NoChatReportsOtf noChatReportsOtf = new NoChatReportsOtf();
	public final NoTelemetryOtf noTelemetryOtf = new NoTelemetryOtf();
	public final ReconnectOtf reconnectOtf = new ReconnectOtf();
	public final ServerFinderOtf serverFinderOtf = new ServerFinderOtf();
	public final TabGuiOtf tabGuiOtf = new TabGuiOtf();
	public final TranslationsOtf translationsOtf = new TranslationsOtf();
	public final VanillaSpoofOtf vanillaSpoofOtf = new VanillaSpoofOtf();
	public final EvlieyeCapesOtf evlieyeCapesOtf = new EvlieyeCapesOtf();
	public final EvlieyeLogoOtf evlieyeLogoOtf = new EvlieyeLogoOtf();
	public final ZoomOtf zoomOtf = new ZoomOtf();
	
	private final TreeMap<String, OtherFeature> otfs =
		new TreeMap<>(String::compareToIgnoreCase);
	
	public OtfList()
	{
		try
		{
			for(Field field : OtfList.class.getDeclaredFields())
			{
				if(!field.getName().endsWith("Otf"))
					continue;
				
				OtherFeature otf = (OtherFeature)field.get(this);
				otfs.put(otf.getName(), otf);
			}
			
		}catch(Exception e)
		{
			String message = "Initializing other Evlieye features";
			CrashReport report = CrashReport.create(e, message);
			throw new CrashException(report);
		}
	}
	
	public OtherFeature getOtfByName(String name)
	{
		return otfs.get(name);
	}
	
	public Collection<OtherFeature> getAllOtfs()
	{
		return otfs.values();
	}
	
	public int countOtfs()
	{
		return otfs.size();
	}
}
