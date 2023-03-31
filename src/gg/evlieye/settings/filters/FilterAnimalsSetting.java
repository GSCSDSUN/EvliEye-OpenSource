/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;

public final class FilterAnimalsSetting extends EntityFilterCheckbox
{
	public FilterAnimalsSetting(String description, boolean checked)
	{
		super("Filter animals", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof AnimalEntity || e instanceof AmbientEntity
			|| e instanceof WaterCreatureEntity);
	}
	
	public static FilterAnimalsSetting genericCombat(boolean checked)
	{
		return new FilterAnimalsSetting("Won't attack pigs, cows, etc.",
			checked);
	}
}
