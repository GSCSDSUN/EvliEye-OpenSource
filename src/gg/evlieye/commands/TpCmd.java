/*

 *



 */
package gg.evlieye.commands;

import java.util.Comparator;
import java.util.stream.StreamSupport;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.FakePlayerEntity;
import gg.evlieye.util.MathUtils;

public final class TpCmd extends Command
{
	public TpCmd()
	{
		super("tp", "Teleports you up to 10 blocks away.", ".tp <x> <y> <z>",
			".tp <entity>");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		BlockPos pos = argsToPos(args);
		
		ClientPlayerEntity player = MC.player;
		player.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	}
	
	private BlockPos argsToPos(String... args) throws CmdException
	{
		switch(args.length)
		{
			default:
			throw new CmdSyntaxError("Invalid coordinates.");
			
			case 1:
			return argsToEntityPos(args[0]);
			
			case 3:
			return argsToXyzPos(args);
		}
	}
	
	private BlockPos argsToEntityPos(String name) throws CmdError
	{
		LivingEntity entity = StreamSupport
			.stream(MC.world.getEntities().spliterator(), true)
			.filter(e -> e instanceof LivingEntity).map(e -> (LivingEntity)e)
			.filter(e -> !e.isRemoved() && e.getHealth() > 0)
			.filter(e -> e != MC.player)
			.filter(e -> !(e instanceof FakePlayerEntity))
			.filter(e -> name.equalsIgnoreCase(e.getDisplayName().getString()))
			.min(
				Comparator.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
			.orElse(null);
		
		if(entity == null)
			throw new CmdError("Entity \"" + name + "\" could not be found.");
		
		return BlockPos.ofFloored(entity.getPos());
	}
	
	private BlockPos argsToXyzPos(String... xyz) throws CmdSyntaxError
	{
		BlockPos playerPos = BlockPos.ofFloored(MC.player.getPos());
		int[] player = {playerPos.getX(), playerPos.getY(), playerPos.getZ()};
		int[] pos = new int[3];
		
		for(int i = 0; i < 3; i++)
			if(MathUtils.isInteger(xyz[i]))
				pos[i] = Integer.parseInt(xyz[i]);
			else if(xyz[i].equals("~"))
				pos[i] = player[i];
			else if(xyz[i].startsWith("~")
				&& MathUtils.isInteger(xyz[i].substring(1)))
				pos[i] = player[i] + Integer.parseInt(xyz[i].substring(1));
			else
				throw new CmdSyntaxError("Invalid coordinates.");
			
		return new BlockPos(pos[0], pos[1], pos[2]);
	}
}
