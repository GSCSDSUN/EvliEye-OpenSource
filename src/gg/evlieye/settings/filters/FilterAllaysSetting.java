/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AllayEntity;

public final class FilterAllaysSetting extends EntityFilterCheckbox
{
	public FilterAllaysSetting(String description, boolean checked)
	{
		super("Filter allays", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof AllayEntity);
	}
	
	public static FilterAllaysSetting genericCombat(boolean checked)
	{
		return new FilterAllaysSetting("Won't attack allays.", checked);
	}
}
