/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

public final class NoWeatherHack extends Hack
{
	private final CheckboxSetting disableRain =
		new CheckboxSetting("Disable Rain", true);
	
	private final CheckboxSetting changeTime =
		new CheckboxSetting("Change World Time", false);
	
	private final SliderSetting time =
		new SliderSetting("Time", 6000, 0, 23900, 100, ValueDisplay.INTEGER);
	
	private final CheckboxSetting changeMoonPhase =
		new CheckboxSetting("Change Moon Phase", false);
	
	private final SliderSetting moonPhase =
		new SliderSetting("Moon Phase", 0, 0, 7, 1, ValueDisplay.INTEGER);
	
	public NoWeatherHack()
	{
		super("NoWeather");
		setCategory(Category.RENDER);
		
		addSetting(disableRain);
		addSetting(changeTime);
		addSetting(time);
		addSetting(changeMoonPhase);
		addSetting(moonPhase);
	}
	
	public boolean isRainDisabled()
	{
		return isEnabled() && disableRain.isChecked();
	}
	
	public boolean isTimeChanged()
	{
		return isEnabled() && changeTime.isChecked();
	}
	
	public long getChangedTime()
	{
		return time.getValueI();
	}
	
	public boolean isMoonPhaseChanged()
	{
		return isEnabled() && changeMoonPhase.isChecked();
	}
	
	public int getChangedMoonPhase()
	{
		return moonPhase.getValueI();
	}
}
