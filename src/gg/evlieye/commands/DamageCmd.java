/*

 *



 */
package gg.evlieye.commands;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.MathUtils;

public final class DamageCmd extends Command
{
	public DamageCmd()
	{
		super("damage", "Applies the given amount of damage.",
			".damage <amount>", "Note: The amount is in half-hearts.",
			"Example: .damage 7 (applies 3.5 hearts)",
			"To apply more damage, run the command multiple times.");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length == 0)
			throw new CmdSyntaxError();
		
		if(MC.player.getAbilities().creativeMode)
			throw new CmdError("Cannot damage in creative mode.");
		
		int amount = parseAmount(args[0]);
		applyDamage(amount);
	}
	
	private int parseAmount(String dmgString) throws CmdSyntaxError
	{
		if(!MathUtils.isInteger(dmgString))
			throw new CmdSyntaxError("Not a number: " + dmgString);
		
		int dmg = Integer.parseInt(dmgString);
		
		if(dmg < 1)
			throw new CmdSyntaxError("Minimum amount is 1.");
		
		if(dmg > 7)
			throw new CmdSyntaxError("Maximum amount is 7.");
		
		return dmg;
	}
	
	private void applyDamage(int amount)
	{
		Vec3d pos = MC.player.getPos();
		
		for(int i = 0; i < 80; i++)
		{
			sendPosition(pos.x, pos.y + amount + 2.1, pos.z, false);
			sendPosition(pos.x, pos.y + 0.05, pos.z, false);
		}
		
		sendPosition(pos.x, pos.y, pos.z, true);
	}
	
	private void sendPosition(double x, double y, double z, boolean onGround)
	{
		MC.player.networkHandler.sendPacket(
			new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround));
	}
}
