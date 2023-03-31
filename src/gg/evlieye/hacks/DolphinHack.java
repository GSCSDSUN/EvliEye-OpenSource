/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;

@SearchTags({"AutoSwim", "auto swim"})
public final class DolphinHack extends Hack implements UpdateListener
{
	public DolphinHack()
	{
		super("Dolphin");
		setCategory(Category.MOVEMENT);
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(UpdateListener.class, this);
		WURST.getHax().fishHack.setEnabled(false);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		ClientPlayerEntity player = MC.player;
		if(!player.isWet() || player.isSneaking())
			return;
		
		Vec3d velocity = player.getVelocity();
		player.setVelocity(velocity.x, velocity.y + 0.04, velocity.z);
	}
}
