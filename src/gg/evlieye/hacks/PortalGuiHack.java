/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"portal gui"})
public final class PortalGuiHack extends Hack
{
	public PortalGuiHack()
	{
		super("PortalGUI");
		setCategory(Category.OTHER);
	}
	
	// See ClientPlayerEntityMixin.beforeUpdateNausea()
}
