/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.block.BlockState;
import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface GetAmbientOcclusionLightLevelListener extends Listener
{
	public void onGetAmbientOcclusionLightLevel(
		GetAmbientOcclusionLightLevelEvent event);
	
	public static class GetAmbientOcclusionLightLevelEvent
		extends Event<GetAmbientOcclusionLightLevelListener>
	{
		private final BlockState state;
		private float lightLevel;
		private final float defaultLightLevel;
		
		public GetAmbientOcclusionLightLevelEvent(BlockState state,
			float lightLevel)
		{
			this.state = state;
			this.lightLevel = lightLevel;
			defaultLightLevel = lightLevel;
		}
		
		public BlockState getState()
		{
			return state;
		}
		
		public float getLightLevel()
		{
			return lightLevel;
		}
		
		public void setLightLevel(float lightLevel)
		{
			this.lightLevel = lightLevel;
		}
		
		public float getDefaultLightLevel()
		{
			return defaultLightLevel;
		}
		
		@Override
		public void fire(
			ArrayList<GetAmbientOcclusionLightLevelListener> listeners)
		{
			for(GetAmbientOcclusionLightLevelListener listener : listeners)
				listener.onGetAmbientOcclusionLightLevel(this);
		}
		
		@Override
		public Class<GetAmbientOcclusionLightLevelListener> getListenerType()
		{
			return GetAmbientOcclusionLightLevelListener.class;
		}
	}
}
