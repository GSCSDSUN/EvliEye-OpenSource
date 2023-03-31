/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.block.BlockState;
import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;

public interface TesselateBlockListener extends Listener
{
	public void onTesselateBlock(TesselateBlockEvent event);
	
	public static class TesselateBlockEvent
		extends CancellableEvent<TesselateBlockListener>
	{
		private final BlockState state;
		
		public TesselateBlockEvent(BlockState state)
		{
			this.state = state;
		}
		
		public BlockState getState()
		{
			return state;
		}
		
		@Override
		public void fire(ArrayList<TesselateBlockListener> listeners)
		{
			for(TesselateBlockListener listener : listeners)
			{
				listener.onTesselateBlock(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<TesselateBlockListener> getListenerType()
		{
			return TesselateBlockListener.class;
		}
	}
}
