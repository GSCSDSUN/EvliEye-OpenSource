/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.hacks.RemoteViewHack;

public final class RvCmd extends Command
{
	public RvCmd()
	{
		super("rv", "Makes RemoteView target a specific entity.",
			".rv <entity>");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		RemoteViewHack remoteView = WURST.getHax().remoteViewHack;
		
		if(args.length != 1)
			throw new CmdSyntaxError();
		
		remoteView.onToggledByCommand(args[0]);
	}
}
