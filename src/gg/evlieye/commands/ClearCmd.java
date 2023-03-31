/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;

public final class ClearCmd extends Command
{
	public ClearCmd()
	{
		super("clear", "Clears the chat completely.", ".clear");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length > 0)
			throw new CmdSyntaxError();
		
		MC.inGameHud.getChatHud().clear(true);
	}
}
