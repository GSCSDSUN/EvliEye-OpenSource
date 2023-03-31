/*

 *



 */
package gg.evlieye.other_features;

import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.options.KeybindManagerScreen;
import gg.evlieye.other_feature.OtherFeature;

@SearchTags({"KeybindManager", "keybind manager", "KeybindsManager",
	"keybinds manager"})
@DontBlock
public final class KeybindManagerOtf extends OtherFeature
{
	public KeybindManagerOtf()
	{
		super("Keybinds",
			"This is just a shortcut to let you open the Keybind Manager from within the GUI. Normally you would go to Evlieye Options > Keybinds.");
	}
	
	@Override
	public String getPrimaryAction()
	{
		return "Open Keybind Manager";
	}
	
	@Override
	public void doPrimaryAction()
	{
		MC.setScreen(new KeybindManagerScreen(MC.currentScreen));
	}
}
