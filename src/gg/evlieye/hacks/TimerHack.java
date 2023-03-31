/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

public final class TimerHack extends Hack
{
	private final SliderSetting speed =
		new SliderSetting("Speed", 2, 0.1, 20, 0.1, ValueDisplay.DECIMAL);
	
	public TimerHack()
	{
		super("Timer");
		setCategory(Category.OTHER);
		addSetting(speed);
	}
	
	@Override
	public String getRenderName()
	{
		return getName() + " [" + speed.getValueString() + "]";
	}
	
	public float getTimerSpeed()
	{
		return isEnabled() ? speed.getValueF() : 1;
	}
}
