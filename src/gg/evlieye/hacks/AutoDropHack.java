/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.ItemListSetting;

@SearchTags({"auto drop", "AutoEject", "auto-eject", "auto eject",
	"InventoryCleaner", "inventory cleaner", "InvCleaner", "inv cleaner"})
public final class AutoDropHack extends Hack implements UpdateListener
{
	private ItemListSetting items = new ItemListSetting("Items",
		"Unwanted items that will be dropped.", "minecraft:allium",
		"minecraft:azure_bluet", "minecraft:blue_orchid",
		"minecraft:cornflower", "minecraft:dandelion", "minecraft:lilac",
		"minecraft:lily_of_the_valley", "minecraft:orange_tulip",
		"minecraft:oxeye_daisy", "minecraft:peony", "minecraft:pink_tulip",
		"minecraft:poisonous_potato", "minecraft:poppy", "minecraft:red_tulip",
		"minecraft:rose_bush", "minecraft:rotten_flesh", "minecraft:sunflower",
		"minecraft:wheat_seeds", "minecraft:white_tulip");
	
	private final String renderName =
		Math.random() < 0.01 ? "AutoLinus" : getName();
	
	public AutoDropHack()
	{
		super("AutoDrop");
		setCategory(Category.ITEMS);
		addSetting(items);
	}
	
	@Override
	public String getRenderName()
	{
		return renderName;
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		// check screen
		if(MC.currentScreen instanceof HandledScreen
			&& !(MC.currentScreen instanceof InventoryScreen))
			return;
		
		for(int slot = 9; slot < 45; slot++)
		{
			int adjustedSlot = slot;
			if(adjustedSlot >= 36)
				adjustedSlot -= 36;
			ItemStack stack = MC.player.getInventory().getStack(adjustedSlot);
			
			if(stack.isEmpty())
				continue;
			
			Item item = stack.getItem();
			String itemName = Registries.ITEM.getId(item).toString();
			
			if(!items.getItemNames().contains(itemName))
				continue;
			
			IMC.getInteractionManager().windowClick_THROW(slot);
		}
	}
}
