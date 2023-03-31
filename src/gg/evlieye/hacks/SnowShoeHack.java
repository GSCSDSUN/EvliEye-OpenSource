/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"snow shoe", "SnowJesus", "snow jesus", "NoSnowSink",
	"no snow sink", "AntiSnowSink", "anti snow sink"})
public final class SnowShoeHack extends Hack
{
	public SnowShoeHack()
	{
		super("SnowShoe");
		setCategory(Category.MOVEMENT);
	}
	
	// See PowderSnowBlockMixin
}
