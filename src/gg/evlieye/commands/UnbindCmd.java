/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.command.CmdException;
import gg.evlieye.command.Command;

public final class UnbindCmd extends Command
{
	public UnbindCmd()
	{
		super("unbind", "Shortcut for '.binds remove'.", ".unbind <key>",
			"Use .binds for more options.");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		WURST.getCmdProcessor()
			.process("binds remove " + String.join(" ", args));
	}
}
