/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;

public final class FilterEndermenSetting extends EntityFilterCheckbox
{
	public FilterEndermenSetting(String description, boolean checked)
	{
		super("Filter endermen", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof EndermanEntity);
	}
	
	public static FilterEndermenSetting genericCombat(boolean checked)
	{
		return new FilterEndermenSetting("Won't attack endermen.", checked);
	}
}
