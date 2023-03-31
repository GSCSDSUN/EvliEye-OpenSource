/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;

public interface VelocityFromFluidListener extends Listener
{
	public void onVelocityFromFluid(VelocityFromFluidEvent event);
	
	public static class VelocityFromFluidEvent
		extends CancellableEvent<VelocityFromFluidListener>
	{
		private final Entity entity;
		
		public VelocityFromFluidEvent(Entity entity)
		{
			this.entity = entity;
		}
		
		public Entity getEntity()
		{
			return entity;
		}
		
		@Override
		public void fire(ArrayList<VelocityFromFluidListener> listeners)
		{
			for(VelocityFromFluidListener listener : listeners)
			{
				listener.onVelocityFromFluid(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<VelocityFromFluidListener> getListenerType()
		{
			return VelocityFromFluidListener.class;
		}
	}
}
