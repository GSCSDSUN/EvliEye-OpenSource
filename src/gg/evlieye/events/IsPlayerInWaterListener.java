/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface IsPlayerInWaterListener extends Listener
{
	public void onIsPlayerInWater(IsPlayerInWaterEvent event);
	
	public static class IsPlayerInWaterEvent
		extends Event<IsPlayerInWaterListener>
	{
		private boolean inWater;
		private final boolean normallyInWater;
		
		public IsPlayerInWaterEvent(boolean inWater)
		{
			this.inWater = inWater;
			normallyInWater = inWater;
		}
		
		public boolean isInWater()
		{
			return inWater;
		}
		
		public void setInWater(boolean inWater)
		{
			this.inWater = inWater;
		}
		
		public boolean isNormallyInWater()
		{
			return normallyInWater;
		}
		
		@Override
		public void fire(ArrayList<IsPlayerInWaterListener> listeners)
		{
			for(IsPlayerInWaterListener listener : listeners)
				listener.onIsPlayerInWater(this);
		}
		
		@Override
		public Class<IsPlayerInWaterListener> getListenerType()
		{
			return IsPlayerInWaterListener.class;
		}
	}
}
