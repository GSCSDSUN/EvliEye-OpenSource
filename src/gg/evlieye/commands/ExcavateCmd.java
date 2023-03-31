/*

 *



 */
package gg.evlieye.commands;

import net.minecraft.util.math.BlockPos;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.MathUtils;

public final class ExcavateCmd extends Command
{
	public ExcavateCmd()
	{
		super("excavate",
			"Automatically destroys all blocks in the selected area.",
			".excavate <x1> <y1> <z1> <x2> <y2> <z2>");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 6)
			throw new CmdSyntaxError();
		
		BlockPos pos1 = argsToXyzPos(args[0], args[1], args[2]);
		BlockPos pos2 = argsToXyzPos(args[3], args[4], args[5]);
		evlieye.getHax().excavatorHack.enableWithArea(pos1, pos2);
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
