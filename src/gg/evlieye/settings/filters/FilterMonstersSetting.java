/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.Monster;

public final class FilterMonstersSetting extends EntityFilterCheckbox
{
	public FilterMonstersSetting(String description, boolean checked)
	{
		super("Filter monsters", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof Monster);
	}
	
	public static FilterMonstersSetting genericCombat(boolean checked)
	{
		return new FilterMonstersSetting("Won't attack zombies, creepers, etc.",
			checked);
	}
}
