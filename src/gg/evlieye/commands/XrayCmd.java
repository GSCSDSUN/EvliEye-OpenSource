/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.command.CmdException;
import gg.evlieye.command.Command;

public final class XrayCmd extends Command
{
	public XrayCmd()
	{
		super("xray", "Shortcut for '.blocklist X-Ray Ores'.",
			".xray add <block>", ".xray remove <block>", ".xray list [<page>]",
			".xray reset", "Example: .xray add gravel");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		WURST.getCmdProcessor()
			.process("blocklist X-Ray Ores " + String.join(" ", args));
	}
}
