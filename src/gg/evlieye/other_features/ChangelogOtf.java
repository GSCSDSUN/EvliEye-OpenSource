/*

 *



 */
package gg.evlieye.other_features;

import net.minecraft.util.Util;
import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.other_feature.OtherFeature;
import gg.evlieye.update.Version;

@SearchTags({"change log", "wurst update", "release notes", "what's new",
	"what is new", "new features", "recently added features"})
@DontBlock
public final class ChangelogOtf extends OtherFeature
{
	public ChangelogOtf()
	{
		super("Changelog", "Opens the changelog in your browser.");
	}
	
	@Override
	public String getPrimaryAction()
	{
		return "View Changelog";
	}
	
	@Override
	public void doPrimaryAction()
	{
		String link = new Version(EvlieyeClient.VERSION).getChangelogLink()
			+ "?utm_source=Evlieye+Client&utm_medium=ChangelogOtf&utm_content=View+Changelog";
		Util.getOperatingSystem().open(link);
	}
}
