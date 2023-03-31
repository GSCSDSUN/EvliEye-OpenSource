/*

 *



 */
package gg.evlieye.commands;

import java.util.ArrayList;

import gg.evlieye.DontBlock;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.ChatUtils;
import gg.evlieye.util.MathUtils;

@DontBlock
public final class HelpCmd extends Command
{
	private static final int CMDS_PER_PAGE = 8;
	
	public HelpCmd()
	{
		super("help", "Shows help for a command or a list of commands.",
			".help <command>", "List commands: .help [<page>]");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length > 1)
			throw new CmdSyntaxError();
		
		String arg = args.length > 0 ? args[0] : "1";
		
		if(MathUtils.isInteger(arg))
			listCommands(Integer.parseInt(arg));
		else
			help(arg);
	}
	
	private void listCommands(int page) throws CmdException
	{
		ArrayList<Command> cmds = new ArrayList<>(evlieye.getCmds().getAllCmds());
		int pages = (int)Math.ceil(cmds.size() / (double)CMDS_PER_PAGE);
		pages = Math.max(pages, 1);
		
		if(page > pages || page < 1)
			throw new CmdSyntaxError("Invalid page: " + page);
		
		String total = "Total: " + cmds.size() + " command";
		total += cmds.size() != 1 ? "s" : "";
		ChatUtils.message(total);
		
		int start = (page - 1) * CMDS_PER_PAGE;
		int end = Math.min(page * CMDS_PER_PAGE, cmds.size());
		
		ChatUtils.message("Command list (page " + page + "/" + pages + ")");
		for(int i = start; i < end; i++)
			ChatUtils.message("- " + cmds.get(i).getName());
	}
	
	private void help(String cmdName) throws CmdException
	{
		if(cmdName.startsWith("."))
			cmdName = cmdName.substring(1);
		
		Command cmd = evlieye.getCmds().getCmdByName(cmdName);
		if(cmd == null)
			throw new CmdSyntaxError("Unknown command: ." + cmdName);
		
		ChatUtils.message("Available help for ." + cmdName + ":");
		cmd.printHelp();
	}
}
