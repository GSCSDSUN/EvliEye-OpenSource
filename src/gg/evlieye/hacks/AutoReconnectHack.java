/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@SearchTags({"auto reconnect", "AutoRejoin", "auto rejoin"})
@DontBlock
public final class AutoReconnectHack extends Hack
{
	private final SliderSetting waitTime =
		new SliderSetting("Wait time", "Time before reconnecting in seconds.",
			5, 0, 60, 0.5, ValueDisplay.DECIMAL.withSuffix("s"));
	
	public AutoReconnectHack()
	{
		super("AutoReconnect");
		setCategory(Category.OTHER);
		addSetting(waitTime);
	}
	
	public int getWaitTicks()
	{
		return (int)(waitTime.getValue() * 20);
	}
	
	// See DisconnectedScreenMixin
}
