/*

 *



 */
package gg.evlieye.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.clickgui.Component;
import gg.evlieye.clickgui.components.ItemListEditButton;
import gg.evlieye.keybinds.PossibleKeybind;
import gg.evlieye.util.json.JsonException;
import gg.evlieye.util.json.JsonUtils;
import gg.evlieye.util.json.WsonArray;

public final class ItemListSetting extends Setting
{
	private final ArrayList<String> itemNames = new ArrayList<>();
	private final String[] defaultNames;
	
	public ItemListSetting(String name, String description, String... items)
	{
		super(name, description);
		
		Arrays.stream(items).parallel()
			.map(s -> Registries.ITEM.get(new Identifier(s)))
			.filter(Objects::nonNull)
			.map(i -> Registries.ITEM.getId(i).toString()).distinct().sorted()
			.forEachOrdered(s -> itemNames.add(s));
		defaultNames = itemNames.toArray(new String[0]);
	}
	
	public List<String> getItemNames()
	{
		return Collections.unmodifiableList(itemNames);
	}
	
	public void add(Item item)
	{
		String name = Registries.ITEM.getId(item).toString();
		if(Collections.binarySearch(itemNames, name) >= 0)
			return;
		
		itemNames.add(name);
		Collections.sort(itemNames);
		EvlieyeClient.INSTANCE.saveSettings();
	}
	
	public void remove(int index)
	{
		if(index < 0 || index >= itemNames.size())
			return;
		
		itemNames.remove(index);
		EvlieyeClient.INSTANCE.saveSettings();
	}
	
	public void resetToDefaults()
	{
		itemNames.clear();
		itemNames.addAll(Arrays.asList(defaultNames));
		EvlieyeClient.INSTANCE.saveSettings();
	}
	
	@Override
	public Component getComponent()
	{
		return new ItemListEditButton(this);
	}
	
	@Override
	public void fromJson(JsonElement json)
	{
		try
		{
			WsonArray wson = JsonUtils.getAsArray(json);
			itemNames.clear();
			
			wson.getAllStrings().parallelStream()
				.map(s -> Registries.ITEM.get(new Identifier(s)))
				.filter(Objects::nonNull)
				.map(i -> Registries.ITEM.getId(i).toString()).distinct()
				.sorted().forEachOrdered(s -> itemNames.add(s));
			
		}catch(JsonException e)
		{
			e.printStackTrace();
			resetToDefaults();
		}
	}
	
	@Override
	public JsonElement toJson()
	{
		JsonArray json = new JsonArray();
		itemNames.forEach(s -> json.add(s));
		return json;
	}
	
	@Override
	public Set<PossibleKeybind> getPossibleKeybinds(String featureName)
	{
		String fullName = featureName + " " + getName();
		
		String command = ".itemlist " + featureName.toLowerCase() + " ";
		command += getName().toLowerCase().replace(" ", "_") + " ";
		
		LinkedHashSet<PossibleKeybind> pkb = new LinkedHashSet<>();
		// Can't just list all the items here. Would need to change UI to allow
		// user to choose an item after selecting this option.
		// pkb.add(new PossibleKeybind(command + "add dirt",
		// "Add dirt to " + fullName));
		// pkb.add(new PossibleKeybind(command + "remove dirt",
		// "Remove dirt from " + fullName));
		pkb.add(new PossibleKeybind(command + "reset", "Reset " + fullName));
		
		return pkb;
	}
}
