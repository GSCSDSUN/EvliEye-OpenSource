/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface HitResultRayTraceListener extends Listener
{
	public void onHitResultRayTrace(float float_1);
	
	public static class HitResultRayTraceEvent
		extends Event<HitResultRayTraceListener>
	{
		private float float_1;
		
		public HitResultRayTraceEvent(float float_1)
		{
			this.float_1 = float_1;
		}
		
		@Override
		public void fire(ArrayList<HitResultRayTraceListener> listeners)
		{
			for(HitResultRayTraceListener listener : listeners)
				listener.onHitResultRayTrace(float_1);
		}
		
		@Override
		public Class<HitResultRayTraceListener> getListenerType()
		{
			return HitResultRayTraceListener.class;
		}
	}
}
