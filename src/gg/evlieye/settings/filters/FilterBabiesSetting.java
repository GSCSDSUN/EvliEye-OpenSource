/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PassiveEntity;

public final class FilterBabiesSetting extends EntityFilterCheckbox
{
	public FilterBabiesSetting(String description, boolean checked)
	{
		super("Filter babies", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof PassiveEntity && ((PassiveEntity)e).isBaby());
	}
	
	public static FilterBabiesSetting genericCombat(boolean checked)
	{
		return new FilterBabiesSetting(
			"Won't attack baby pigs, baby villagers, etc.", checked);
	}
}
