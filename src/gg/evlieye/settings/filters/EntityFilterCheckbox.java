/*

 *



 */
package gg.evlieye.settings.filters;

import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.settings.Setting;
import gg.evlieye.settings.filterlists.EntityFilterList.EntityFilter;

public abstract class EntityFilterCheckbox extends CheckboxSetting
	implements EntityFilter
{
	public EntityFilterCheckbox(String name, String description,
		boolean checked)
	{
		super(name, description, checked);
	}
	
	@Override
	public final boolean isFilterEnabled()
	{
		return isChecked();
	}
	
	@Override
	public final Setting getSetting()
	{
		return this;
	}
}
