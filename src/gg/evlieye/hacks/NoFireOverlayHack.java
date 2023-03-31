/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@SearchTags({"no fire overlay"})
public final class NoFireOverlayHack extends Hack
{
	private final SliderSetting offset =
		new SliderSetting("Offset", "The amount to lower the fire overlay by.",
			0.6, 0.01, 0.6, 0.01, ValueDisplay.DECIMAL);
	
	public NoFireOverlayHack()
	{
		super("NoFireOverlay");
		setCategory(Category.RENDER);
		addSetting(offset);
	}
	
	public float getOverlayOffset()
	{
		return isEnabled() ? offset.getValueF() : 0;
	}
	
	// See InGameOverlayRendererMixin.getFireOffset()
}
