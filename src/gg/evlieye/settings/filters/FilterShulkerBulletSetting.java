/*

 *



 */
package gg.evlieye.settings.filters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;

public final class FilterShulkerBulletSetting extends EntityFilterCheckbox
{
	public FilterShulkerBulletSetting(String description, boolean checked)
	{
		super("Filter shulker bullets", description, checked);
	}
	
	@Override
	public boolean test(Entity e)
	{
		return !(e instanceof ShulkerBulletEntity);
	}
	
	public static FilterShulkerBulletSetting genericCombat(boolean checked)
	{
		return new FilterShulkerBulletSetting("Won't attack shulker bullets.",
			checked);
	}
}
