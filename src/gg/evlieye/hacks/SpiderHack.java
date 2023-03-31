/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.Category;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;

public final class SpiderHack extends Hack implements UpdateListener
{
	public SpiderHack()
	{
		super("Spider");
		setCategory(Category.MOVEMENT);
	}
	
	@Override
	public void onEnable()
	{
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
		ClientPlayerEntity player = MC.player;
		if(!player.horizontalCollision)
			return;
		
		Vec3d velocity = player.getVelocity();
		if(velocity.y >= 0.2)
			return;
		
		player.setVelocity(velocity.x, 0.2, velocity.z);
	}
}
