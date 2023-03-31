/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.block.entity.BlockEntity;
import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;

public interface RenderBlockEntityListener extends Listener
{
	public void onRenderBlockEntity(RenderBlockEntityEvent event);
	
	public static class RenderBlockEntityEvent
		extends CancellableEvent<RenderBlockEntityListener>
	{
		private final BlockEntity blockEntity;
		
		public RenderBlockEntityEvent(BlockEntity blockEntity)
		{
			this.blockEntity = blockEntity;
		}
		
		public BlockEntity getBlockEntity()
		{
			return blockEntity;
		}
		
		@Override
		public void fire(ArrayList<RenderBlockEntityListener> listeners)
		{
			for(RenderBlockEntityListener listener : listeners)
			{
				listener.onRenderBlockEntity(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<RenderBlockEntityListener> getListenerType()
		{
			return RenderBlockEntityListener.class;
		}
	}
}
