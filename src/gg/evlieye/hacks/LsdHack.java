/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import gg.evlieye.Category;
import gg.evlieye.hack.DontSaveState;
import gg.evlieye.hack.Hack;
import gg.evlieye.mixinterface.IGameRenderer;

@DontSaveState
public final class LsdHack extends Hack
{
	public LsdHack()
	{
		super("LSD");
		setCategory(Category.FUN);
	}
	
	@Override
	public void onEnable()
	{
		if(!(MC.getCameraEntity() instanceof PlayerEntity))
		{
			setEnabled(false);
			return;
		}
		
		if(MC.gameRenderer.getPostProcessor() != null)
			MC.gameRenderer.disablePostProcessor();
		
		((IGameRenderer)MC.gameRenderer)
			.loadEvlieyeShader(new Identifier("shaders/post/wobble.json"));
	}
	
	@Override
	public void onDisable()
	{
		if(MC.gameRenderer.getPostProcessor() != null)
			MC.gameRenderer.disablePostProcessor();
	}
}
