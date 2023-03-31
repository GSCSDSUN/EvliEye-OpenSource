/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public final class FilterPlayersSetting extends EntityFilterCheckbox
{
	public FilterPlayersSetting(String description, boolean checked)
	{
		super("Filter players", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof PlayerEntity);
	}
	
	public static FilterPlayersSetting genericCombat(boolean checked)
	{
		return new FilterPlayersSetting("Won't attack other players.", checked);
	}
}
