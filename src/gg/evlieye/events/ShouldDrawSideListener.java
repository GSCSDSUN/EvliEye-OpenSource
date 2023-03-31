/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.block.BlockState;
import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface ShouldDrawSideListener extends Listener
{
	public void onShouldDrawSide(ShouldDrawSideEvent event);
	
	public static class ShouldDrawSideEvent
		extends Event<ShouldDrawSideListener>
	{
		private final BlockState state;
		private Boolean rendered;
		
		public ShouldDrawSideEvent(BlockState state)
		{
			this.state = state;
		}
		
		public BlockState getState()
		{
			return state;
		}
		
		public Boolean isRendered()
		{
			return rendered;
		}
		
		public void setRendered(boolean rendered)
		{
			this.rendered = rendered;
		}
		
		@Override
		public void fire(ArrayList<ShouldDrawSideListener> listeners)
		{
			for(ShouldDrawSideListener listener : listeners)
				listener.onShouldDrawSide(this);
		}
		
		@Override
		public Class<ShouldDrawSideListener> getListenerType()
		{
			return ShouldDrawSideListener.class;
		}
	}
}
