/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.client.util.math.MatrixStack;
import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface GUIRenderListener extends Listener
{
	public void onRenderGUI(MatrixStack matrixStack, float partialTicks);
	
	public static class GUIRenderEvent extends Event<GUIRenderListener>
	{
		private final float partialTicks;
		private final MatrixStack matrixStack;
		
		public GUIRenderEvent(MatrixStack matrixStack, float partialTicks)
		{
			this.matrixStack = matrixStack;
			this.partialTicks = partialTicks;
		}
		
		@Override
		public void fire(ArrayList<GUIRenderListener> listeners)
		{
			for(GUIRenderListener listener : listeners)
				listener.onRenderGUI(matrixStack, partialTicks);
		}
		
		@Override
		public Class<GUIRenderListener> getListenerType()
		{
			return GUIRenderListener.class;
		}
	}
}
