/*

 *



 */
package gg.evlieye.ai;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.util.BlockUtils;
import gg.evlieye.util.RotationUtils;

public class WalkPathProcessor extends PathProcessor
{
	public WalkPathProcessor(ArrayList<PathPos> path)
	{
		super(path);
	}
	
	@Override
	public void process()
	{
		// get positions
		BlockPos pos;
		if(EvlieyeClient.MC.player.isOnGround())
			pos = BlockPos.ofFloored(EvlieyeClient.MC.player.getX(),
				EvlieyeClient.MC.player.getY() + 0.5,
				EvlieyeClient.MC.player.getZ());
		else
			pos = BlockPos.ofFloored(EvlieyeClient.MC.player.getPos());
		PathPos nextPos = path.get(index);
		int posIndex = path.indexOf(pos);
		
		if(posIndex == -1)
			ticksOffPath++;
		else
			ticksOffPath = 0;
		
		// update index
		if(pos.equals(nextPos))
		{
			index++;
			
			// disable when done
			if(index >= path.size())
				done = true;
			return;
		}
		if(posIndex > index)
		{
			index = posIndex + 1;
			
			// disable when done
			if(index >= path.size())
				done = true;
			return;
		}
		
		lockControls();
		EvlieyeClient.MC.player.getAbilities().flying = false;
		
		// face next position
		facePosition(nextPos);
		if(MathHelper.wrapDegrees(Math.abs(RotationUtils
			.getHorizontalAngleToLookVec(Vec3d.ofCenter(nextPos)))) > 90)
			return;
		
		if(WURST.getHax().jesusHack.isEnabled())
		{
			// wait for Jesus to swim up
			if(EvlieyeClient.MC.player.getY() < nextPos.getY()
				&& (EvlieyeClient.MC.player.isTouchingWater()
					|| EvlieyeClient.MC.player.isInLava()))
				return;
			
			// manually swim down if using Jesus
			if(EvlieyeClient.MC.player.getY() - nextPos.getY() > 0.5
				&& (EvlieyeClient.MC.player.isTouchingWater()
					|| EvlieyeClient.MC.player.isInLava()
					|| WURST.getHax().jesusHack.isOverLiquid()))
				MC.options.sneakKey.setPressed(true);
		}
		
		// horizontal movement
		if(pos.getX() != nextPos.getX() || pos.getZ() != nextPos.getZ())
		{
			MC.options.forwardKey.setPressed(true);
			
			if(index > 0 && path.get(index - 1).isJumping()
				|| pos.getY() < nextPos.getY())
				MC.options.jumpKey.setPressed(true);
			
			// vertical movement
		}else if(pos.getY() != nextPos.getY())
			// go up
			if(pos.getY() < nextPos.getY())
			{
				// climb up
				// TODO: Spider
				Block block = BlockUtils.getBlock(pos);
				if(block instanceof LadderBlock || block instanceof VineBlock)
				{
					WURST.getRotationFaker().faceVectorClientIgnorePitch(
						BlockUtils.getBoundingBox(pos).getCenter());
					
					MC.options.forwardKey.setPressed(true);
					
				}else
				{
					// directional jump
					if(index < path.size() - 1
						&& !nextPos.up().equals(path.get(index + 1)))
						index++;
					
					// jump up
					MC.options.jumpKey.setPressed(true);
				}
				
				// go down
			}else
			{
				// skip mid-air nodes and go straight to the bottom
				while(index < path.size() - 1
					&& path.get(index).down().equals(path.get(index + 1)))
					index++;
				
				// walk off the edge
				if(EvlieyeClient.MC.player.isOnGround())
					MC.options.forwardKey.setPressed(true);
			}
	}
}
