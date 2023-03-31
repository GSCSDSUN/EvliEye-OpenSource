/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"AntiBlindness", "NoBlindness", "anti blindness", "no blindness",
	"AntiDarkness", "NoDarkness", "anti darkness", "no darkness",
	"AntiWardenEffect", "anti warden effect", "NoWardenEffect",
	"no warden effect"})
public final class AntiBlindHack extends Hack
{
	public AntiBlindHack()
	{
		super("AntiBlind");
		setCategory(Category.RENDER);
	}
	
	// See BackgroundRendererMixin, LightTextureManagerMixin, WorldRendererMixin
}
