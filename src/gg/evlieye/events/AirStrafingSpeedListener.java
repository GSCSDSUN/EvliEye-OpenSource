/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface AirStrafingSpeedListener extends Listener
{
	public void onGetAirStrafingSpeed(AirStrafingSpeedEvent event);
	
	public static class AirStrafingSpeedEvent
		extends Event<AirStrafingSpeedListener>
	{
		private float airStrafingSpeed;
		private final float defaultSpeed;
		
		public AirStrafingSpeedEvent(float airStrafingSpeed)
		{
			this.airStrafingSpeed = airStrafingSpeed;
			defaultSpeed = airStrafingSpeed;
		}
		
		public float getSpeed()
		{
			return airStrafingSpeed;
		}
		
		public void setSpeed(float airStrafingSpeed)
		{
			this.airStrafingSpeed = airStrafingSpeed;
		}
		
		public float getDefaultSpeed()
		{
			return defaultSpeed;
		}
		
		@Override
		public void fire(ArrayList<AirStrafingSpeedListener> listeners)
		{
			for(AirStrafingSpeedListener listener : listeners)
				listener.onGetAirStrafingSpeed(this);
		}
		
		@Override
		public Class<AirStrafingSpeedListener> getListenerType()
		{
			return AirStrafingSpeedListener.class;
		}
	}
}
