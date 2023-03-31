/*

 *



 */
package gg.evlieye.hacks;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemGroups;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.clickgui.screens.ClickGuiScreen;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.mixinterface.IKeyBinding;
import gg.evlieye.settings.CheckboxSetting;

@SearchTags({"inv walk", "inventory walk", "InvMove", "inv move",
	"inventory move", "MenuWalk", "menu walk"})
public final class InvWalkHack extends Hack implements UpdateListener
{
	private final CheckboxSetting allowClickGUI =
		new CheckboxSetting("Allow ClickGUI",
			"description.wurst.setting.invwalk.allow_clickgui", true);
	
	private final CheckboxSetting allowOther =
		new CheckboxSetting("Allow other screens",
			"description.wurst.setting.invwalk.allow_other", true);
	
	private final CheckboxSetting allowSneak =
		new CheckboxSetting("Allow sneak key", true);
	
	private final CheckboxSetting allowSprint =
		new CheckboxSetting("Allow sprint key", true);
	
	private final CheckboxSetting allowJump =
		new CheckboxSetting("Allow jump key", true);
	
	public InvWalkHack()
	{
		super("InvWalk");
		setCategory(Category.MOVEMENT);
		addSetting(allowClickGUI);
		addSetting(allowOther);
		addSetting(allowSneak);
		addSetting(allowSprint);
		addSetting(allowJump);
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		Screen screen = MC.currentScreen;
		if(screen == null)
			return;
		
		if(!isAllowedScreen(screen))
			return;
		
		ArrayList<KeyBinding> keys =
			new ArrayList<>(Arrays.asList(MC.options.forwardKey,
				MC.options.backKey, MC.options.leftKey, MC.options.rightKey));
		
		if(allowSneak.isChecked())
			keys.add(MC.options.sneakKey);
		
		if(allowSprint.isChecked())
			keys.add(MC.options.sprintKey);
		
		if(allowJump.isChecked())
			keys.add(MC.options.jumpKey);
		
		for(KeyBinding key : keys)
			((IKeyBinding)key).resetPressedState();
	}
	
	private boolean isAllowedScreen(Screen screen)
	{
		if(screen instanceof AbstractInventoryScreen
			&& !isCreativeSearchBarOpen(screen))
			return true;
		
		if(allowClickGUI.isChecked() && screen instanceof ClickGuiScreen)
			return true;
		
		if(allowOther.isChecked() && screen instanceof HandledScreen
			&& !hasTextBox(screen))
			return true;
		
		return false;
	}
	
	private boolean isCreativeSearchBarOpen(Screen screen)
	{
		if(!(screen instanceof CreativeInventoryScreen))
			return false;
		
		return CreativeInventoryScreen.selectedTab == ItemGroups.SEARCH;
	}
	
	private boolean hasTextBox(Screen screen)
	{
		return screen.children().stream()
			.anyMatch(e -> e instanceof TextFieldWidget);
	}
}
