/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"no slowdown", "no slow down"})
public final class NoSlowdownHack extends Hack
{
	public NoSlowdownHack()
	{
		super("NoSlowdown");
		setCategory(Category.MOVEMENT);
	}
	
	// See BlockMixin.onGetVelocityMultiplier() and
	// ClientPlayerEntityMixin.wurstIsUsingItem()
}
