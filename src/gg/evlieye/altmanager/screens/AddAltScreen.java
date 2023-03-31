/*

 *



 */
package gg.evlieye.altmanager.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import gg.evlieye.altmanager.AltManager;
import gg.evlieye.altmanager.CrackedAlt;
import gg.evlieye.altmanager.MojangAlt;

public final class AddAltScreen extends AltEditorScreen
{
	private final AltManager altManager;
	
	public AddAltScreen(Screen prevScreen, AltManager altManager)
	{
		super(prevScreen, Text.literal("New Alt"));
		this.altManager = altManager;
	}
	
	@Override
	protected String getDoneButtonText()
	{
		return "Add";
	}
	
	@Override
	protected void pressDoneButton()
	{
		String nameOrEmail = getNameOrEmail();
		String password = getPassword();
		
		if(password.isEmpty())
			altManager.add(new CrackedAlt(nameOrEmail));
		else
			altManager.add(new MojangAlt(nameOrEmail, password));
		
		client.setScreen(prevScreen);
	}
}
