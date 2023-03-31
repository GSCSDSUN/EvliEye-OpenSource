/*

 *



 */
package gg.evlieye.altmanager.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import gg.evlieye.altmanager.LoginException;
import gg.evlieye.altmanager.LoginManager;
import gg.evlieye.altmanager.MicrosoftLoginManager;

public final class DirectLoginScreen extends AltEditorScreen
{
	public DirectLoginScreen(Screen prevScreen)
	{
		super(prevScreen, Text.literal("Direct Login"));
	}
	
	@Override
	protected String getDoneButtonText()
	{
		return "Login";
	}
	
	@Override
	protected void pressDoneButton()
	{
		String nameOrEmail = getNameOrEmail();
		String password = getPassword();
		
		if(password.isEmpty())
			LoginManager.changeCrackedName(nameOrEmail);
		else
			try
			{
				MicrosoftLoginManager.login(nameOrEmail, password);
				
			}catch(LoginException e)
			{
				try
				{
					LoginManager.login(nameOrEmail, password);
					
				}catch(LoginException e2)
				{
					message = "\u00a7c\u00a7lMicrosoft:\u00a7c "
						+ e.getMessage() + "\n\u00a7c\u00a7lMojang:\u00a7c "
						+ e2.getMessage();
					
					doErrorEffect();
				}
				return;
			}
		
		message = "";
		client.setScreen(new TitleScreen());
	}
}
