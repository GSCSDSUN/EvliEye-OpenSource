/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.TameableEntity;

public final class FilterPetsSetting extends EntityFilterCheckbox
{
	public FilterPetsSetting(String description, boolean checked)
	{
		super("Filter pets", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof TameableEntity && ((TameableEntity)e).isTamed())
			&& !(e instanceof AbstractHorseEntity
				&& ((AbstractHorseEntity)e).isTame());
	}
	
	public static FilterPetsSetting genericCombat(boolean checked)
	{
		return new FilterPetsSetting(
			"Won't attack tamed wolves, tamed horses, etc.", checked);
	}
}
