/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"no levitation", "levitation", "levitate"})
public final class NoLevitationHack extends Hack
{
	public NoLevitationHack()
	{
		super("NoLevitation");
		setCategory(Category.MOVEMENT);
	}
	
	// See ClientPlayerEntityMixin.hasStatusEffect()
}
