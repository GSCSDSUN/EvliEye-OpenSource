/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"no pumpkin", "AntiPumpkin", "anti pumpkin"})
public final class NoPumpkinHack extends Hack
{
	public NoPumpkinHack()
	{
		super("NoPumpkin");
		setCategory(Category.RENDER);
	}
	
	// See IngameHudMixin.onRenderOverlay()
}
