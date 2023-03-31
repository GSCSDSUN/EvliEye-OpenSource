/*

 *



 */
package gg.evlieye.settings;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import com.google.gson.JsonElement;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.clickgui.Component;
import gg.evlieye.keybinds.PossibleKeybind;

public abstract class Setting
{
	private final String name;
	private final String description;
	
	public Setting(String name, String description)
	{
		this.name = Objects.requireNonNull(name);
		this.description = Objects.requireNonNull(description);
	}
	
	public final String getName()
	{
		return name;
	}
	
	public final String getDescription()
	{
		return EvlieyeClient.INSTANCE.translate(description);
	}
	
	public final String getWrappedDescription(int width)
	{
		List<StringVisitable> lines = EvlieyeClient.MC.textRenderer
			.getTextHandler().wrapLines(getDescription(), width, Style.EMPTY);
		
		StringJoiner joiner = new StringJoiner("\n");
		lines.stream().map(StringVisitable::getString)
			.forEach(s -> joiner.add(s));
		
		return joiner.toString();
	}
	
	public abstract Component getComponent();
	
	public abstract void fromJson(JsonElement json);
	
	public abstract JsonElement toJson();
	
	public void update()
	{
		
	}
	
	public abstract Set<PossibleKeybind> getPossibleKeybinds(
		String featureName);
}
