/*

 *



 */
package gg.evlieye.commands;

import java.util.Comparator;
import java.util.stream.StreamSupport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.hacks.ProtectHack;
import gg.evlieye.util.FakePlayerEntity;

public final class ProtectCmd extends Command
{
	public ProtectCmd()
	{
		super("protect", "Protects the given entity from other entities.",
			".protect <entity>");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 1)
			throw new CmdSyntaxError();
		
		ProtectHack protectHack = evlieye.getHax().protectHack;
		
		if(protectHack.isEnabled())
			protectHack.setEnabled(false);
		
		Entity entity = StreamSupport
			.stream(MC.world.getEntities().spliterator(), true)
			.filter(e -> e instanceof LivingEntity)
			.filter(e -> !e.isRemoved() && ((LivingEntity)e).getHealth() > 0)
			.filter(e -> e != MC.player)
			.filter(e -> !(e instanceof FakePlayerEntity))
			.filter(e -> args[0].equalsIgnoreCase(e.getName().getString()))
			.min(
				Comparator.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
			.orElse(null);
		
		if(entity == null)
			throw new CmdError(
				"Entity \"" + args[0] + "\" could not be found.");
		
		protectHack.setFriend(entity);
		protectHack.setEnabled(true);
	}
}
