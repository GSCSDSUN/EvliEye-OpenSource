/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"true sight"})
public final class TrueSightHack extends Hack
{
	public TrueSightHack()
	{
		super("TrueSight");
		setCategory(Category.RENDER);
	}
	
	// See LivingEntityRendererMixin
}
