/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import gg.evlieye.Category;
import gg.evlieye.events.RightClickListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

public final class ThrowHack extends Hack implements RightClickListener
{
	private final SliderSetting amount = new SliderSetting("Amount",
		"Amount of uses per click.", 16, 2, 1000000, 1, ValueDisplay.INTEGER);
	
	public ThrowHack()
	{
		super("Throw");
		
		setCategory(Category.OTHER);
		addSetting(amount);
	}
	
	@Override
	public String getRenderName()
	{
		return getName() + " [" + amount.getValueString() + "]";
	}
	
	@Override
	public void onEnable()
	{
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
		if(IMC.getItemUseCooldown() > 0)
			return;
		
		if(!MC.options.useKey.isPressed())
			return;
		
		for(int i = 0; i < amount.getValueI(); i++)
		{
			if(MC.crosshairTarget.getType() == HitResult.Type.BLOCK)
			{
				BlockHitResult hitResult = (BlockHitResult)MC.crosshairTarget;
				IMC.getInteractionManager().rightClickBlock(
					hitResult.getBlockPos(), hitResult.getSide(),
					hitResult.getPos());
			}
			
			IMC.getInteractionManager().rightClickItem();
		}
	}
}
