/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.client.util.math.MatrixStack;
import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface RenderListener extends Listener
{
	public void onRender(MatrixStack matrixStack, float partialTicks);
	
	public static class RenderEvent extends Event<RenderListener>
	{
		private final MatrixStack matrixStack;
		private final float partialTicks;
		
		public RenderEvent(MatrixStack matrixStack, float partialTicks)
		{
			this.matrixStack = matrixStack;
			this.partialTicks = partialTicks;
		}
		
		@Override
		public void fire(ArrayList<RenderListener> listeners)
		{
			for(RenderListener listener : listeners)
				listener.onRender(matrixStack, partialTicks);
		}
		
		@Override
		public Class<RenderListener> getListenerType()
		{
			return RenderListener.class;
		}
	}
}
