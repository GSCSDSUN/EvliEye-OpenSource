/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;

public final class FilterCrystalsSetting extends EntityFilterCheckbox
{
	public FilterCrystalsSetting(String description, boolean checked)
	{
		super("Filter end crystals", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof EndCrystalEntity);
	}
	
	public static FilterCrystalsSetting genericCombat(boolean checked)
	{
		return new FilterCrystalsSetting("Won't attack end crystals.", checked);
	}
}
