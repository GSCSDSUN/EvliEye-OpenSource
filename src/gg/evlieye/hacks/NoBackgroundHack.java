/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.CheckboxSetting;

@SearchTags({"no background", "NoGuiBackground", "no gui background",
	"NoGradient", "no gradient"})
public final class NoBackgroundHack extends Hack
{
	public final CheckboxSetting allGuis = new CheckboxSetting("All GUIs",
		"Removes the background for all GUIs, not just inventories.", false);
	
	public NoBackgroundHack()
	{
		super("NoBackground");
		setCategory(Category.RENDER);
		addSetting(allGuis);
	}
	
	public boolean shouldCancelBackground(Screen screen)
	{
		if(!isEnabled())
			return false;
		
		if(MC.world == null)
			return false;
		
		if(!allGuis.isChecked() && !(screen instanceof HandledScreen))
			return false;
		
		return true;
	}
	
	// See ScreenMixin.onRenderBackground()
}
