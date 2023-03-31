/*

 *



 */
package gg.evlieye.hacks.chestesp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import net.minecraft.util.math.Box;
import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.settings.ColorSetting;
import gg.evlieye.settings.Setting;

public abstract class ChestEspGroup
{
	protected final ArrayList<Box> boxes = new ArrayList<>();
	private final ColorSetting color;
	private final CheckboxSetting enabled;
	
	public ChestEspGroup(ColorSetting color, CheckboxSetting enabled)
	{
		this.color = Objects.requireNonNull(color);
		this.enabled = enabled;
	}
	
	public void clear()
	{
		boxes.clear();
	}
	
	public boolean isEnabled()
	{
		return enabled == null || enabled.isChecked();
	}
	
	public Stream<Setting> getSettings()
	{
		return Stream.of(enabled, color).filter(Objects::nonNull);
	}
	
	public float[] getColorF()
	{
		return color.getColorF();
	}
	
	public List<Box> getBoxes()
	{
		return Collections.unmodifiableList(boxes);
	}
}
