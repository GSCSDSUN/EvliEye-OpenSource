/*

 *



 */
package gg.evlieye.hacks.chestesp;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.settings.ColorSetting;

public final class ChestEspEntityGroup extends ChestEspGroup
{
	private final ArrayList<Entity> entities = new ArrayList<>();
	
	public ChestEspEntityGroup(ColorSetting color, CheckboxSetting enabled)
	{
		super(color, enabled);
	}
	
	public void add(Entity e)
	{
		entities.add(e);
	}
	
	@Override
	public void clear()
	{
		entities.clear();
		super.clear();
	}
	
	public void updateBoxes(float partialTicks)
	{
		boxes.clear();
		
		for(Entity e : entities)
		{
			double offsetX = -(e.getX() - e.lastRenderX)
				+ (e.getX() - e.lastRenderX) * partialTicks;
			double offsetY = -(e.getY() - e.lastRenderY)
				+ (e.getY() - e.lastRenderY) * partialTicks;
			double offsetZ = -(e.getZ() - e.lastRenderZ)
				+ (e.getZ() - e.lastRenderZ) * partialTicks;
			
			boxes.add(e.getBoundingBox().offset(offsetX, offsetY, offsetZ));
		}
	}
}
