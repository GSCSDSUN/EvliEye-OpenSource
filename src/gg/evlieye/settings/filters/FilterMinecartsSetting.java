/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;

public final class FilterMinecartsSetting extends EntityFilterCheckbox
{
	public FilterMinecartsSetting(String description, boolean checked)
	{
		super("Filter minecarts", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof AbstractMinecartEntity);
	}
}
