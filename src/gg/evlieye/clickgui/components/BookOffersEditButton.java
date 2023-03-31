/*

 *



 */
package gg.evlieye.clickgui.components;

import java.util.Objects;

import gg.evlieye.clickgui.screens.EditBookOffersScreen;
import gg.evlieye.settings.BookOffersSetting;
import gg.evlieye.settings.Setting;

public final class BookOffersEditButton extends AbstractListEditButton
{
	private final BookOffersSetting setting;
	
	public BookOffersEditButton(BookOffersSetting setting)
	{
		this.setting = Objects.requireNonNull(setting);
		setWidth(getDefaultWidth());
		setHeight(getDefaultHeight());
	}
	
	@Override
	protected void openScreen()
	{
		MC.setScreen(new EditBookOffersScreen(MC.currentScreen, setting));
	}
	
	@Override
	protected String getText()
	{
		return setting.getName() + ": " + setting.getOffers().size();
	}
	
	@Override
	protected Setting getSetting()
	{
		return setting;
	}
}
