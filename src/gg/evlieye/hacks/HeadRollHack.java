/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;

@SearchTags({"head roll", "nodding", "yes"})
public final class HeadRollHack extends Hack implements UpdateListener
{
	public HeadRollHack()
	{
		super("HeadRoll");
		setCategory(Category.FUN);
	}
	
	@Override
	public void onEnable()
	{
		// disable incompatible derps
		WURST.getHax().derpHack.setEnabled(false);
		WURST.getHax().tiredHack.setEnabled(false);
		
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		float timer = MC.player.age % 20 / 10F;
		float pitch = MathHelper.sin(timer * (float)Math.PI) * 90F;
		
		MC.player.networkHandler.sendPacket(
			new PlayerMoveC2SPacket.LookAndOnGround(MC.player.getYaw(), pitch,
				MC.player.isOnGround()));
	}
}
