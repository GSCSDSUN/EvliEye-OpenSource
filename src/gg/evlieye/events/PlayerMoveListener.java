/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;
import gg.evlieye.mixinterface.IClientPlayerEntity;

public interface PlayerMoveListener extends Listener
{
	public void onPlayerMove(IClientPlayerEntity player);
	
	public static class PlayerMoveEvent extends Event<PlayerMoveListener>
	{
		private final IClientPlayerEntity player;
		
		public PlayerMoveEvent(IClientPlayerEntity player)
		{
			this.player = player;
		}
		
		@Override
		public void fire(ArrayList<PlayerMoveListener> listeners)
		{
			for(PlayerMoveListener listener : listeners)
				listener.onPlayerMove(player);
		}
		
		@Override
		public Class<PlayerMoveListener> getListenerType()
		{
			return PlayerMoveListener.class;
		}
	}
}
