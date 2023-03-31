/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.CheckboxSetting;

@SearchTags({"name tags"})
public final class NameTagsHack extends Hack
{
	private final CheckboxSetting unlimitedRange =
		new CheckboxSetting("Unlimited range",
			"Removes the 64 block distance limit for nametags.", true);
	
	private final CheckboxSetting seeThrough = new CheckboxSetting(
		"See-through mode",
		"Renders nametags on the see-through text layer. This makes them"
			+ " easier to read behind walls, but harder to read behind water"
			+ " and other transparent things.",
		false);
	
	private final CheckboxSetting forceNametags = new CheckboxSetting(
		"Force nametags",
		"Forces nametags of all players to be visible, even your own.", false);
	
	public NameTagsHack()
	{
		super("NameTags");
		setCategory(Category.RENDER);
		addSetting(unlimitedRange);
		addSetting(seeThrough);
		addSetting(forceNametags);
	}
	
	public boolean isUnlimitedRange()
	{
		return isEnabled() && unlimitedRange.isChecked();
	}
	
	public boolean isSeeThrough()
	{
		return isEnabled() && seeThrough.isChecked();
	}
	
	public boolean shouldForceNametags()
	{
		return isEnabled() && forceNametags.isChecked();
	}
	
	// See LivingEntityRendererMixin and
	// EntityRendererMixin.wurstRenderLabelIfPresent()
}
