/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.SearchTags;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;

@SearchTags({".legit", "dots in chat", "command bypass", "prefix"})
public final class SayCmd extends Command
{
	public SayCmd()
	{
		super("say",
			"Sends the given chat message, even if it starts with a\n" + "dot.",
			".say <message>");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length < 1)
			throw new CmdSyntaxError();
		
		String message = String.join(" ", args);
		if(message.startsWith("/"))
			MC.getNetworkHandler().sendChatCommand(message.substring(1));
		else
			MC.getNetworkHandler().sendChatMessage(message);
	}
}
