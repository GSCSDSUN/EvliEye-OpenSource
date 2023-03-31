/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;

public interface RightClickListener extends Listener
{
	public void onRightClick(RightClickEvent event);
	
	public static class RightClickEvent
		extends CancellableEvent<RightClickListener>
	{
		@Override
		public void fire(ArrayList<RightClickListener> listeners)
		{
			for(RightClickListener listener : listeners)
			{
				listener.onRightClick(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<RightClickListener> getListenerType()
		{
			return RightClickListener.class;
		}
	}
}
