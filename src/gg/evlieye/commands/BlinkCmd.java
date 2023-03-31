/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.hacks.BlinkHack;

public final class BlinkCmd extends Command
{
	public BlinkCmd()
	{
		super("blink", "Enables, disables or cancels Blink.", ".blink [on|off]",
			".blink cancel");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length > 1)
			throw new CmdSyntaxError();
		
		BlinkHack blinkHack = evlieye.getHax().blinkHack;
		
		if(args.length == 0)
		{
			blinkHack.setEnabled(!blinkHack.isEnabled());
			return;
		}
		
		switch(args[0].toLowerCase())
		{
			default:
			throw new CmdSyntaxError();
			
			case "on":
			blinkHack.setEnabled(true);
			break;
			
			case "off":
			blinkHack.setEnabled(false);
			break;
			
			case "cancel":
			cancel(blinkHack);
			break;
		}
	}
	
	private void cancel(BlinkHack blinkHack) throws CmdException
	{
		if(!blinkHack.isEnabled())
			throw new CmdError("Cannot cancel, Blink is already turned off!");
		
		blinkHack.cancel();
	}
}
