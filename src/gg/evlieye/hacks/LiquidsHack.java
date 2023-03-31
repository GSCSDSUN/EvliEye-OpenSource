/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.events.HitResultRayTraceListener;
import gg.evlieye.hack.Hack;

public final class LiquidsHack extends Hack implements HitResultRayTraceListener
{
	public LiquidsHack()
	{
		super("Liquids");
		setCategory(Category.BLOCKS);
	}
	
	@Override
	protected void onEnable()
	{
		EVENTS.add(HitResultRayTraceListener.class, this);
	}
	
	@Override
	protected void onDisable()
	{
		EVENTS.remove(HitResultRayTraceListener.class, this);
	}
	
	@Override
	public void onHitResultRayTrace(float float_1)
	{
		MC.crosshairTarget = MC.getCameraEntity()
			.raycast(MC.interactionManager.getReachDistance(), float_1, true);
	}
}
