/*

 *



 */
package gg.evlieye.settings.filterlists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.entity.Entity;
import gg.evlieye.settings.Setting;
import gg.evlieye.settings.filters.*;

public class EntityFilterList
{
	private final List<EntityFilter> entityFilters;
	
	public EntityFilterList(EntityFilter... filters)
	{
		this(Arrays.asList(filters));
	}
	
	public EntityFilterList(List<EntityFilter> filters)
	{
		entityFilters = Collections.unmodifiableList(filters);
	}
	
	public final void forEach(Consumer<? super Setting> action)
	{
		entityFilters.stream().map(EntityFilter::getSetting).forEach(action);
	}
	
	public final <T extends Entity> Stream<T> applyTo(Stream<T> stream)
	{
		for(EntityFilter filter : entityFilters)
		{
			if(!filter.isFilterEnabled())
				continue;
			
			stream = stream.filter(filter);
		}
		
		return stream;
	}
	
	public static EntityFilterList genericCombat()
	{
		return new EntityFilterList(FilterPlayersSetting.genericCombat(false),
			FilterSleepingSetting.genericCombat(false),
			FilterFlyingSetting.genericCombat(0),
			FilterMonstersSetting.genericCombat(false),
			FilterPigmenSetting.genericCombat(false),
			FilterEndermenSetting.genericCombat(false),
			FilterAnimalsSetting.genericCombat(false),
			FilterBabiesSetting.genericCombat(false),
			FilterPetsSetting.genericCombat(false),
			FilterTradersSetting.genericCombat(false),
			FilterGolemsSetting.genericCombat(false),
			FilterAllaysSetting.genericCombat(false),
			FilterInvisibleSetting.genericCombat(false),
			FilterNamedSetting.genericCombat(false),
			FilterShulkerBulletSetting.genericCombat(false),
			FilterArmorStandsSetting.genericCombat(false),
			FilterCrystalsSetting.genericCombat(false));
	}
	
	public static interface EntityFilter extends Predicate<Entity>
	{
		public boolean isFilterEnabled();
		
		public Setting getSetting();
	}
}
