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

@SearchTags({"FastClimb", "fast ladder", "fast climb"})
public final class FastLadderHack extends Hack implements UpdateListener
{
	public FastLadderHack()
	{
		super("FastLadder");
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
		
		if(!player.isClimbing() || !player.horizontalCollision)
			return;
		
		if(player.input.movementForward == 0
			&& player.input.movementSideways == 0)
			return;
		
		Vec3d velocity = player.getVelocity();
		player.setVelocity(velocity.x, 0.2872, velocity.z);
	}
}
