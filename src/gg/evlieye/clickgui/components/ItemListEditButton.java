/*

 *



 */
package gg.evlieye.clickgui.components;

import java.util.Objects;

import gg.evlieye.clickgui.screens.EditItemListScreen;
import gg.evlieye.settings.ItemListSetting;
import gg.evlieye.settings.Setting;

public final class ItemListEditButton extends AbstractListEditButton
{
	private final ItemListSetting setting;
	
	public ItemListEditButton(ItemListSetting setting)
	{
		this.setting = Objects.requireNonNull(setting);
		setWidth(getDefaultWidth());
		setHeight(getDefaultHeight());
	}
	
	@Override
	protected void openScreen()
	{
		MC.setScreen(new EditItemListScreen(MC.currentScreen, setting));
	}
	
	@Override
	protected String getText()
	{
		return setting.getName() + ": " + setting.getItemNames().size();
	}
	
	@Override
	protected Setting getSetting()
	{
		return setting;
	}
}
