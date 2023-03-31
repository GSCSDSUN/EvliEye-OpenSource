/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;

public final class FilterPigmenSetting extends EntityFilterCheckbox
{
	public FilterPigmenSetting(String description, boolean checked)
	{
		super("Filter pigmen", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof ZombifiedPiglinEntity);
	}
	
	public static FilterPigmenSetting genericCombat(boolean checked)
	{
		return new FilterPigmenSetting("Won't attack zombie pigmen.", checked);
	}
}
