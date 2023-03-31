/*

 *



 */
package gg.evlieye.other_features;

import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.other_feature.OtherFeature;
import gg.evlieye.settings.CheckboxSetting;

@SearchTags({"turn off", "hide wurst logo", "ghost mode", "stealth mode",
	"vanilla Minecraft"})
@DontBlock
public final class DisableOtf extends OtherFeature
{
	private final CheckboxSetting hideEnableButton = new CheckboxSetting(
		"Hide enable button",
		"Removes the \"Enable Evlieye\" button as soon as you close the Statistics screen."
			+ " You will have to restart the game to re-enable Evlieye.",
		false);
	
	public DisableOtf()
	{
		super("Disable Evlieye",
			"To disable Evlieye, go to the Statistics screen and press the \"Disable Evlieye\" button.\n"
				+ "It will turn into an \"Enable Evlieye\" button once pressed.");
		addSetting(hideEnableButton);
	}
	
	public boolean shouldHideEnableButton()
	{
		return !WURST.isEnabled() && hideEnableButton.isChecked();
	}
}
