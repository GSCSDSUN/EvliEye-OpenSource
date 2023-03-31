/*

 *



 */
package gg.evlieye.hacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.minecraft.loader.api.FabricLoader;
import net.minecraft.loader.api.ModContainer;
import net.minecraft.loader.api.metadata.ModMetadata;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.clickgui.screens.EditBlockListScreen;
import gg.evlieye.events.GetAmbientOcclusionLightLevelListener;
import gg.evlieye.events.RenderBlockEntityListener;
import gg.evlieye.events.SetOpaqueCubeListener;
import gg.evlieye.events.ShouldDrawSideListener;
import gg.evlieye.events.TesselateBlockListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.mixinterface.ISimpleOption;
import gg.evlieye.settings.BlockListSetting;
import gg.evlieye.util.BlockUtils;
import gg.evlieye.util.ChatUtils;

@SearchTags({"XRay", "x ray", "OreFinder", "ore finder"})
public final class XRayHack extends Hack implements UpdateListener,
	SetOpaqueCubeListener, GetAmbientOcclusionLightLevelListener,
	ShouldDrawSideListener, TesselateBlockListener, RenderBlockEntityListener
{
	private final BlockListSetting ores = new BlockListSetting("Ores", "",
		"minecraft:ancient_debris", "minecraft:anvil", "minecraft:beacon",
		"minecraft:bone_block", "minecraft:bookshelf",
		"minecraft:brewing_stand", "minecraft:chain_command_block",
		"minecraft:chest", "minecraft:clay", "minecraft:coal_block",
		"minecraft:coal_ore", "minecraft:command_block", "minecraft:copper_ore",
		"minecraft:crafting_table", "minecraft:deepslate_coal_ore",
		"minecraft:deepslate_copper_ore", "minecraft:deepslate_diamond_ore",
		"minecraft:deepslate_emerald_ore", "minecraft:deepslate_gold_ore",
		"minecraft:deepslate_iron_ore", "minecraft:deepslate_lapis_ore",
		"minecraft:deepslate_redstone_ore", "minecraft:diamond_block",
		"minecraft:diamond_ore", "minecraft:dispenser", "minecraft:dropper",
		"minecraft:emerald_block", "minecraft:emerald_ore",
		"minecraft:enchanting_table", "minecraft:end_portal",
		"minecraft:end_portal_frame", "minecraft:ender_chest",
		"minecraft:furnace", "minecraft:glowstone", "minecraft:gold_block",
		"minecraft:gold_ore", "minecraft:hopper", "minecraft:iron_block",
		"minecraft:iron_ore", "minecraft:ladder", "minecraft:lapis_block",
		"minecraft:lapis_ore", "minecraft:lava", "minecraft:lodestone",
		"minecraft:mossy_cobblestone", "minecraft:nether_gold_ore",
		"minecraft:nether_portal", "minecraft:nether_quartz_ore",
		"minecraft:raw_copper_block", "minecraft:raw_gold_block",
		"minecraft:raw_iron_block", "minecraft:redstone_block",
		"minecraft:redstone_ore", "minecraft:repeating_command_block",
		"minecraft:spawner", "minecraft:suspicious_sand", "minecraft:tnt",
		"minecraft:torch", "minecraft:trapped_chest", "minecraft:water");
	
	private ArrayList<String> oreNames;
	private final String warning;
	
	private final String renderName =
		Math.random() < 0.01 ? "X-Evlieye" : getName();
	
	public XRayHack()
	{
		super("X-Ray");
		setCategory(Category.RENDER);
		addSetting(ores);
		
		List<String> mods = FabricLoader.getInstance().getAllMods().stream()
			.map(ModContainer::getMetadata).map(ModMetadata::getId)
			.collect(Collectors.toList());
		
		Pattern optifine = Pattern.compile("opti(?:fine|fabric).*");
		
		if(mods.stream().anyMatch(optifine.asPredicate()))
			warning = "OptiFine is installed. X-Ray will not work properly!";
		else
			warning = null;
	}
	
	@Override
	public String getRenderName()
	{
		return renderName;
	}
	
	@Override
	public void onEnable()
	{
		oreNames = new ArrayList<>(ores.getBlockNames());
		
		EVENTS.add(UpdateListener.class, this);
		EVENTS.add(SetOpaqueCubeListener.class, this);
		EVENTS.add(GetAmbientOcclusionLightLevelListener.class, this);
		EVENTS.add(ShouldDrawSideListener.class, this);
		EVENTS.add(TesselateBlockListener.class, this);
		EVENTS.add(RenderBlockEntityListener.class, this);
		MC.worldRenderer.reload();
		
		if(warning != null)
			ChatUtils.warning(warning);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
		EVENTS.remove(SetOpaqueCubeListener.class, this);
		EVENTS.remove(GetAmbientOcclusionLightLevelListener.class, this);
		EVENTS.remove(ShouldDrawSideListener.class, this);
		EVENTS.remove(TesselateBlockListener.class, this);
		EVENTS.remove(RenderBlockEntityListener.class, this);
		MC.worldRenderer.reload();
		
		@SuppressWarnings("unchecked")
		ISimpleOption<Double> gammaOption =
			(ISimpleOption<Double>)(Object)MC.options.getGamma();
		
		// TODO: Why does this use 0.5 instead of
		// FullBright's defaultGamma setting?
		if(!evlieye.getHax().fullbrightHack.isEnabled())
			gammaOption.forceSetValue(0.5);
	}
	
	@Override
	public void onUpdate()
	{
		@SuppressWarnings("unchecked")
		ISimpleOption<Double> gammaOption =
			(ISimpleOption<Double>)(Object)MC.options.getGamma();
		
		gammaOption.forceSetValue(16.0);
	}
	
	@Override
	public void onSetOpaqueCube(SetOpaqueCubeEvent event)
	{
		event.cancel();
	}
	
	@Override
	public void onGetAmbientOcclusionLightLevel(
		GetAmbientOcclusionLightLevelEvent event)
	{
		event.setLightLevel(1);
	}
	
	@Override
	public void onShouldDrawSide(ShouldDrawSideEvent event)
	{
		event.setRendered(isVisible(event.getState().getBlock()));
	}
	
	@Override
	public void onTesselateBlock(TesselateBlockEvent event)
	{
		if(!isVisible(event.getState().getBlock()))
			event.cancel();
	}
	
	@Override
	public void onRenderBlockEntity(RenderBlockEntityEvent event)
	{
		if(!isVisible(BlockUtils.getBlock(event.getBlockEntity().getPos())))
			event.cancel();
	}
	
	public void openBlockListEditor(Screen prevScreen)
	{
		MC.setScreen(new EditBlockListScreen(prevScreen, ores));
	}
	
	private boolean isVisible(Block block)
	{
		String name = BlockUtils.getName(block);
		int index = Collections.binarySearch(oreNames, name);
		return index >= 0;
	}
}
