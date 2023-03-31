/*

 *



 */
package gg.evlieye.keybinds;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.clickgui.screens.ClickGuiScreen;
import gg.evlieye.command.CmdProcessor;
import gg.evlieye.events.KeyPressListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.hack.HackList;
import gg.evlieye.util.ChatUtils;

public final class KeybindProcessor implements KeyPressListener
{
	private final HackList hax;
	private final KeybindList keybinds;
	private final CmdProcessor cmdProcessor;
	
	public KeybindProcessor(HackList hax, KeybindList keybinds,
		CmdProcessor cmdProcessor)
	{
		this.hax = hax;
		this.keybinds = keybinds;
		this.cmdProcessor = cmdProcessor;
	}
	
	@Override
	public void onKeyPress(KeyPressEvent event)
	{
		if(event.getAction() != GLFW.GLFW_PRESS)
			return;
		
		Screen screen = EvlieyeClient.MC.currentScreen;
		if(screen != null && !(screen instanceof ClickGuiScreen))
			return;
		
		String keyName = getKeyName(event);
		
		String cmds = keybinds.getCommands(keyName);
		if(cmds == null)
			return;
		
		processCmds(cmds);
	}
	
	private String getKeyName(KeyPressEvent event)
	{
		int keyCode = event.getKeyCode();
		int scanCode = event.getScanCode();
		return InputUtil.fromKeyCode(keyCode, scanCode).getTranslationKey();
	}
	
	private void processCmds(String cmds)
	{
		cmds = cmds.replace(";", "\u00a7").replace("\u00a7\u00a7", ";");
		
		for(String cmd : cmds.split("\u00a7"))
			processCmd(cmd.trim());
	}
	
	private void processCmd(String cmd)
	{
		if(cmd.startsWith("."))
			cmdProcessor.process(cmd.substring(1));
		else if(cmd.contains(" "))
			cmdProcessor.process(cmd);
		else
		{
			Hack hack = hax.getHackByName(cmd);
			
			if(hack == null)
			{
				cmdProcessor.process(cmd);
				return;
			}
			
			if(!hack.isEnabled() && hax.tooManyHaxHack.isEnabled()
				&& hax.tooManyHaxHack.isBlocked(hack))
			{
				ChatUtils.error(hack.getName() + " is blocked by TooManyHax.");
				return;
			}
			
			hack.setEnabled(!hack.isEnabled());
		}
	}
}
