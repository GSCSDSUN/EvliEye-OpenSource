/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.GolemEntity;

public final class FilterGolemsSetting extends EntityFilterCheckbox
{
	public FilterGolemsSetting(String description, boolean checked)
	{
		super("Filter golems", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof GolemEntity);
	}
	
	public static FilterGolemsSetting genericCombat(boolean checked)
	{
		return new FilterGolemsSetting(
			"Won't attack iron golems, snow golems and shulkers.", checked);
	}
}
