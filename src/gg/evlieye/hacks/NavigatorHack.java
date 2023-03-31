/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.DontSaveState;
import gg.evlieye.hack.Hack;
import gg.evlieye.navigator.NavigatorMainScreen;

@DontSaveState
@DontBlock
@SearchTags({"ClickGUI", "click gui", "SearchGUI", "search gui", "HackMenu",
	"hack menu"})
public final class NavigatorHack extends Hack
{
	public NavigatorHack()
	{
		super("Navigator");
	}
	
	@Override
	public void onEnable()
	{
		if(!(MC.currentScreen instanceof NavigatorMainScreen))
			MC.setScreen(new NavigatorMainScreen());
		
		setEnabled(false);
	}
}
