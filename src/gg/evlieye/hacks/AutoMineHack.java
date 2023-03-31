/*

 *



 */
package gg.evlieye.hacks;

import java.util.Arrays;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.util.BlockBreaker;

@SearchTags({"auto mine", "AutoBreak", "auto break"})
public final class AutoMineHack extends Hack implements UpdateListener
{
	private BlockPos currentBlock;
	
	public AutoMineHack()
	{
		super("AutoMine");
		setCategory(Category.BLOCKS);
	}
	
	@Override
	public void onEnable()
	{
		WURST.getHax().excavatorHack.setEnabled(false);
		WURST.getHax().nukerHack.setEnabled(false);
		WURST.getHax().nukerLegitHack.setEnabled(false);
		WURST.getHax().speedNukerHack.setEnabled(false);
		WURST.getHax().tunnellerHack.setEnabled(false);
		
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
		stopMiningAndResetProgress();
	}
	
	@Override
	public void onUpdate()
	{
		setCurrentBlockFromHitResult();
		
		if(currentBlock != null)
			breakCurrentBlock();
	}
	
	private void setCurrentBlockFromHitResult()
	{
		if(MC.crosshairTarget == null || MC.crosshairTarget.getPos() == null
			|| MC.crosshairTarget.getType() != HitResult.Type.BLOCK
			|| !(MC.crosshairTarget instanceof BlockHitResult))
		{
			stopMiningAndResetProgress();
			return;
		}
		
		currentBlock = ((BlockHitResult)MC.crosshairTarget).getBlockPos();
	}
	
	private void breakCurrentBlock()
	{
		if(MC.player.getAbilities().creativeMode)
			BlockBreaker.breakBlocksWithPacketSpam(Arrays.asList(currentBlock));
		else
			BlockBreaker.breakOneBlock(currentBlock);
	}
	
	private void stopMiningAndResetProgress()
	{
		if(currentBlock == null)
			return;
		
		IMC.getInteractionManager().setBreakingBlock(true);
		MC.interactionManager.cancelBlockBreaking();
		currentBlock = null;
	}
}
