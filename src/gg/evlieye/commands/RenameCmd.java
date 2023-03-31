/*

 *



 */
package gg.evlieye.commands;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.ChatUtils;

public final class RenameCmd extends Command
{
	public RenameCmd()
	{
		super("rename", "Renames the item in your hand.", ".rename <new_name>",
			"Use $ for colors, use $$ for $.", "Example:", ".rename $cRed Name",
			"(changes the item's name to \u00a7cRed Name\u00a7r)");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(!MC.player.getAbilities().creativeMode)
			throw new CmdError("Creative mode only.");
		
		if(args.length == 0)
			throw new CmdSyntaxError();
		
		String message = args[0];
		for(int i = 1; i < args.length; i++)
			message += " " + args[i];
		
		message = message.replace("$", "\u00a7").replace("\u00a7\u00a7", "$");
		ItemStack item = MC.player.getInventory().getMainHandStack();
		
		if(item == null)
			throw new CmdError("There is no item in your hand.");
		
		item.setCustomName(Text.literal(message));
		ChatUtils.message("Renamed item to \"" + message + "\u00a7r\".");
	}
}
