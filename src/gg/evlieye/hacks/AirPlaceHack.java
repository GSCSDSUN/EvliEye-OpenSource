/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.RightClickListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@SearchTags({"air place"})
public final class AirPlaceHack extends Hack implements RightClickListener
{
	private final SliderSetting range =
		new SliderSetting("Range", 5, 1, 6, 0.05, ValueDisplay.DECIMAL);
	
	public AirPlaceHack()
	{
		super("AirPlace");
		setCategory(Category.BLOCKS);
		addSetting(range);
	}
	
	@Override
	public void onEnable()
	{
		evlieye.getHax().autoFishHack.setEnabled(false);
		
		EVENTS.add(RightClickListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(RightClickListener.class, this);
	}
	
	@Override
	public void onRightClick(RightClickEvent event)
	{
		HitResult hitResult = MC.player.raycast(range.getValue(), 0, false);
		if(!(hitResult instanceof BlockHitResult blockHitResult))
			return;
		
		IMC.getInteractionManager().rightClickBlock(
			blockHitResult.getBlockPos(), blockHitResult.getSide(),
			blockHitResult.getPos());
	}
}
