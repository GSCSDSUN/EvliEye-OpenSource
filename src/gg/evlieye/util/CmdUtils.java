/*

 *



 */
package gg.evlieye.util;

import java.util.stream.Stream;

import gg.evlieye.Feature;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.command.CmdError;
import gg.evlieye.settings.Setting;

public enum CmdUtils
{
	;
	
	public static Feature findFeature(String name) throws CmdError
	{
		Stream<Feature> stream =
			EvlieyeClient.INSTANCE.getNavigator().getList().stream();
		stream = stream.filter(f -> name.equalsIgnoreCase(f.getName()));
		Feature feature = stream.findFirst().orElse(null);
		
		if(feature == null)
			throw new CmdError(
				"A feature named \"" + name + "\" could not be found.");
		
		return feature;
	}
	
	public static Setting findSetting(Feature feature, String name)
		throws CmdError
	{
		name = name.replace("_", " ").toLowerCase();
		Setting setting = feature.getSettings().get(name);
		
		if(setting == null)
			throw new CmdError("A setting named \"" + name
				+ "\" could not be found in " + feature.getName() + ".");
		
		return setting;
	}
}
