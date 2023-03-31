/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;

public final class FilterArmorStandsSetting extends EntityFilterCheckbox
{
	public FilterArmorStandsSetting(String description, boolean checked)
	{
		super("Filter armor stands", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof ArmorStandEntity);
	}
	
	public static FilterArmorStandsSetting genericCombat(boolean checked)
	{
		return new FilterArmorStandsSetting("Won't attack armor stands.",
			checked);
	}
}
