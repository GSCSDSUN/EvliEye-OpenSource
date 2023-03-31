/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;

public final class FilterTradersSetting extends EntityFilterCheckbox
{
	public FilterTradersSetting(String description, boolean checked)
	{
		super("Filter traders", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof MerchantEntity);
	}
	
	public static FilterTradersSetting genericCombat(boolean checked)
	{
		return new FilterTradersSetting(
			"Won't attack villagers, wandering traders, etc.", checked);
	}
}
