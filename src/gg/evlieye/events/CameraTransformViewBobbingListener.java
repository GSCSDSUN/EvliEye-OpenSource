/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;

public interface CameraTransformViewBobbingListener extends Listener
{
	public void onCameraTransformViewBobbing(
		CameraTransformViewBobbingEvent event);
	
	public static class CameraTransformViewBobbingEvent
		extends CancellableEvent<CameraTransformViewBobbingListener>
	{
		@Override
		public void fire(
			ArrayList<CameraTransformViewBobbingListener> listeners)
		{
			for(CameraTransformViewBobbingListener listener : listeners)
			{
				listener.onCameraTransformViewBobbing(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<CameraTransformViewBobbingListener> getListenerType()
		{
			return CameraTransformViewBobbingListener.class;
		}
	}
}
