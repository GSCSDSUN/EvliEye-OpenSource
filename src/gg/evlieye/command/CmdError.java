/*

 *



 */
package gg.evlieye.command;

import gg.evlieye.util.ChatUtils;

public final class CmdError extends CmdException
{
	public CmdError(String message)
	{
		super(message);
	}
	
	@Override
	public void printToChat(Command cmd)
	{
		ChatUtils.error(getMessage());
	}
}
