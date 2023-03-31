/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;

public final class FilterNamedSetting extends EntityFilterCheckbox
{
	public FilterNamedSetting(String description, boolean checked)
	{
		super("Filter named", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !e.hasCustomName();
	}
	
	public static FilterNamedSetting genericCombat(boolean checked)
	{
		return new FilterNamedSetting("Won't attack name-tagged entities.",
			checked);
	}
}
