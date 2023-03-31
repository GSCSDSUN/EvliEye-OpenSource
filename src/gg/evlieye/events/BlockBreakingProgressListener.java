/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface BlockBreakingProgressListener extends Listener
{
	public void onBlockBreakingProgress(BlockBreakingProgressEvent event);
	
	public static class BlockBreakingProgressEvent
		extends Event<BlockBreakingProgressListener>
	{
		private final BlockPos blockPos;
		private final Direction direction;
		
		public BlockBreakingProgressEvent(BlockPos blockPos,
			Direction direction)
		{
			this.blockPos = blockPos;
			this.direction = direction;
		}
		
		@Override
		public void fire(ArrayList<BlockBreakingProgressListener> listeners)
		{
			for(BlockBreakingProgressListener listener : listeners)
				listener.onBlockBreakingProgress(this);
		}
		
		@Override
		public Class<BlockBreakingProgressListener> getListenerType()
		{
			return BlockBreakingProgressListener.class;
		}
		
		public BlockPos getBlockPos()
		{
			return blockPos;
		}
		
		public Direction getDirection()
		{
			return direction;
		}
	}
}
