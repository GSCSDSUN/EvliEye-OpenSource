/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"anti wobble", "NoWobble", "no wobble", "AntiNausea",
	"anti nausea", "NoNausea", "no nausea"})
public final class AntiWobbleHack extends Hack
{
	public AntiWobbleHack()
	{
		super("AntiWobble");
		setCategory(Category.RENDER);
	}
	
	// See GameRendererMixin.evlieyeNauseaLerp()
}
