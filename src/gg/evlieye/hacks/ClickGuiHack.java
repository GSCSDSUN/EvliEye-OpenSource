/*

 *



 */
package gg.evlieye.hacks;

import java.awt.Color;

import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.clickgui.screens.ClickGuiScreen;
import gg.evlieye.hack.DontSaveState;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.ColorSetting;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@DontSaveState
@DontBlock
@SearchTags({"click gui", "WindowGUI", "window gui", "HackMenu", "hack menu"})
public final class ClickGuiHack extends Hack
{
	private final ColorSetting bgColor =
		new ColorSetting("Background", "Background color", new Color(0x404040));
	
	private final ColorSetting acColor =
		new ColorSetting("Accent", "Accent color", new Color(0x101010));
	
	private final ColorSetting txtColor =
		new ColorSetting("Text", "Text color", new Color(0xF0F0F0));
	
	private final SliderSetting opacity = new SliderSetting("Opacity", 0.5,
		0.15, 0.85, 0.01, ValueDisplay.PERCENTAGE);
	
	private final SliderSetting ttOpacity = new SliderSetting("Tooltip opacity",
		0.75, 0.15, 1, 0.01, ValueDisplay.PERCENTAGE);
	
	private final SliderSetting maxHeight = new SliderSetting("Max height",
		"Maximum window height\n" + "0 = no limit", 200, 0, 1000, 50,
		ValueDisplay.INTEGER);
	
	private final SliderSetting maxSettingsHeight =
		new SliderSetting("Max settings height",
			"Maximum height for settings windows\n" + "0 = no limit", 200, 0,
			1000, 50, ValueDisplay.INTEGER);
	
	public ClickGuiHack()
	{
		super("ClickGUI");
		addSetting(bgColor);
		addSetting(acColor);
		addSetting(txtColor);
		addSetting(opacity);
		addSetting(ttOpacity);
		addSetting(maxHeight);
		addSetting(maxSettingsHeight);
	}
	
	@Override
	public void onEnable()
	{
		MC.setScreen(new ClickGuiScreen(WURST.getGui()));
		setEnabled(false);
	}
	
	public float[] getBackgroundColor()
	{
		return bgColor.getColorF();
	}
	
	public float[] getAccentColor()
	{
		return acColor.getColorF();
	}
	
	public int getTextColor()
	{
		return txtColor.getColorI();
	}
	
	public float getOpacity()
	{
		return opacity.getValueF();
	}
	
	public float getTooltipOpacity()
	{
		return ttOpacity.getValueF();
	}
	
	public int getMaxHeight()
	{
		return maxHeight.getValueI();
	}
	
	public int getMaxSettingsHeight()
	{
		return maxSettingsHeight.getValueI();
	}
}
