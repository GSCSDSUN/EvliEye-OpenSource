/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"no overlay", "NoWaterOverlay", "no water overlay"})
public final class NoOverlayHack extends Hack
{
	public NoOverlayHack()
	{
		super("NoOverlay");
		setCategory(Category.RENDER);
	}
	
	// See CameraMixin.onGetSubmersionType() and
	// InGameOverlayRendererMixin.onRenderUnderwaterOverlay()
}
