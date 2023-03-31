/*

 *



 */
package gg.evlieye.other_features;

import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.other_feature.OtherFeature;
import gg.evlieye.settings.EnumSetting;

@SearchTags({"tab gui", "HackMenu", "hack menu", "SideBar", "side bar",
	"blocks movement combat render chat fun items other"})
@DontBlock
public final class TabGuiOtf extends OtherFeature
{
	private final EnumSetting<Status> status =
		new EnumSetting<>("Status", Status.values(), Status.DISABLED);
	
	public TabGuiOtf()
	{
		super("TabGUI", "Allows you to quickly toggle hacks while playing.\n"
			+ "Use the arrow keys to navigate.\n\n"
			+ "Change the \u00a76HackList \u00a76Position\u00a7r setting to \u00a76Right\u00a7r to prevent TabGUI from overlapping with the HackList.");
		
		addSetting(status);
	}
	
	public boolean isHidden()
	{
		return status.getSelected() == Status.DISABLED;
	}
	
	private enum Status
	{
		ENABLED("Enabled"),
		DISABLED("Disabled");
		
		private final String name;
		
		private Status(String name)
		{
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}
