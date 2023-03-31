/*

 *



 */
package gg.evlieye.commands;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.ChatUtils;

public final class CopyItemCmd extends Command
{
	public CopyItemCmd()
	{
		super("copyitem",
			"Allows you to copy items that other people are holding\n"
				+ "or wearing. Requires creative mode.",
			".copyitem <player> <slot>",
			"Valid slots: hand, head, chest, legs, feet");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 2)
			throw new CmdSyntaxError();
		
		if(!MC.player.getAbilities().creativeMode)
			throw new CmdError("Creative mode only.");
		
		AbstractClientPlayerEntity player = getPlayer(args[0]);
		ItemStack item = getItem(player, args[1]);
		giveItem(item);
		
		ChatUtils.message("Item copied.");
	}
	
	private AbstractClientPlayerEntity getPlayer(String name) throws CmdError
	{
		for(AbstractClientPlayerEntity player : MC.world.getPlayers())
		{
			if(!player.getEntityName().equalsIgnoreCase(name))
				continue;
			
			return player;
		}
		
		throw new CmdError("Player \"" + name + "\" could not be found.");
	}
	
	private ItemStack getItem(AbstractClientPlayerEntity player, String slot)
		throws CmdSyntaxError
	{
		switch(slot.toLowerCase())
		{
			case "hand":
			return player.getInventory().getMainHandStack();
			
			case "head":
			return player.getInventory().getArmorStack(3);
			
			case "chest":
			return player.getInventory().getArmorStack(2);
			
			case "legs":
			return player.getInventory().getArmorStack(1);
			
			case "feet":
			return player.getInventory().getArmorStack(0);
			
			default:
			throw new CmdSyntaxError();
		}
	}
	
	private void giveItem(ItemStack stack) throws CmdError
	{
		int slot = MC.player.getInventory().getEmptySlot();
		if(slot < 0)
			throw new CmdError("Cannot give item. Your inventory is full.");
		
		if(slot < 9)
			slot += 36;
		
		CreativeInventoryActionC2SPacket packet =
			new CreativeInventoryActionC2SPacket(slot, stack);
		MC.player.networkHandler.sendPacket(packet);
	}
}
