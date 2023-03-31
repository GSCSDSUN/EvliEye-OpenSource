/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.util.math.Vec3d;
import gg.evlieye.Category;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;

public final class NoWebHack extends Hack implements UpdateListener
{
	public NoWebHack()
	{
		super("NoWeb");
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
		IMC.getPlayer().setMovementMultiplier(Vec3d.ZERO);
	}
}
