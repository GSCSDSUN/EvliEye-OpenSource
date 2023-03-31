/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@SearchTags({"high jump"})
public final class HighJumpHack extends Hack
{
	private final SliderSetting height = new SliderSetting("Height",
		"Jump height in blocks.\n"
			+ "This gets very inaccurate at higher values.",
		6, 1, 100, 1, ValueDisplay.INTEGER);
	
	public HighJumpHack()
	{
		super("HighJump");
		
		setCategory(Category.MOVEMENT);
		addSetting(height);
	}
	
	public float getAdditionalJumpMotion()
	{
		return isEnabled() ? height.getValueF() * 0.1F : 0;
	}
}
