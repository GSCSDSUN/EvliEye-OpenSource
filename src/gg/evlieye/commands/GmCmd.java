/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;

public final class GmCmd extends Command
{
	public GmCmd()
	{
		super("gm", "Shortcut for /gamemode.", ".gm <gamemode>");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length < 1)
			throw new CmdSyntaxError();
		
		String args2 = String.join(" ", args);
		switch(args2)
		{
			case "s":
			case "0":
			args2 = "survival";
			break;
			
			case "c":
			case "1":
			args2 = "creative";
			break;
			
			case "a":
			case "2":
			args2 = "adventure";
			break;
			
			case "sp":
			case "3":
			args2 = "spectator";
			break;
		}
		
		String message = "gamemode " + args2;
		MC.getNetworkHandler().sendChatCommand(message);
	}
}
