/*

 *



 */
package gg.evlieye.other_features;

import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.other_feature.OtherFeature;

@SearchTags({"last server"})
@DontBlock
public final class LastServerOtf extends OtherFeature
{
	public LastServerOtf()
	{
		super("LastServer",
			"Evlieye adds a \"Last Server\" button to the server selection screen that automatically brings you back to the last server you played on.\n\n"
				+ "Useful when you get kicked and/or have a lot of servers.");
	}
}
