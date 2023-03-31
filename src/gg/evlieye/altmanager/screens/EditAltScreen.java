/*

 *



 */
package gg.evlieye.altmanager.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import gg.evlieye.altmanager.Alt;
import gg.evlieye.altmanager.AltManager;
import gg.evlieye.altmanager.MojangAlt;

public final class EditAltScreen extends AltEditorScreen
{
	private final AltManager altManager;
	private Alt editedAlt;
	
	public EditAltScreen(Screen prevScreen, AltManager altManager,
		Alt editedAlt)
	{
		super(prevScreen, Text.literal("Edit Alt"));
		this.altManager = altManager;
		this.editedAlt = editedAlt;
	}
	
	@Override
	protected String getDefaultNameOrEmail()
	{
		return editedAlt instanceof MojangAlt
			? ((MojangAlt)editedAlt).getEmail() : editedAlt.getName();
	}
	
	@Override
	protected String getDefaultPassword()
	{
		return editedAlt instanceof MojangAlt
			? ((MojangAlt)editedAlt).getPassword() : "";
	}
	
	@Override
	protected String getDoneButtonText()
	{
		return "Save";
	}
	
	@Override
	protected void pressDoneButton()
	{
		altManager.edit(editedAlt, getNameOrEmail(), getPassword());
		client.setScreen(prevScreen);
	}
}
