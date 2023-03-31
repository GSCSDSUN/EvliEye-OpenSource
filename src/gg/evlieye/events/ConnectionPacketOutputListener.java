/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.network.packet.Packet;
import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;
import gg.evlieye.events.PacketOutputListener.PacketOutputEvent;

/**
 * Similar to {@link PacketOutputListener}, but also captures packets that are
 * sent before the client has finished connecting to the server. Most hacks
 * should use {@link PacketOutputListener} instead.
 */
public interface ConnectionPacketOutputListener extends Listener
{
	public void onSentConnectionPacket(ConnectionPacketOutputEvent event);
	
	/**
	 * Similar to {@link PacketOutputEvent}, but also captures packets that are
	 * sent before the client has finished connecting to the server. Most hacks
	 * should use {@link PacketOutputEvent} instead.
	 */
	public static class ConnectionPacketOutputEvent
		extends CancellableEvent<ConnectionPacketOutputListener>
	{
		private Packet<?> packet;
		
		public ConnectionPacketOutputEvent(Packet<?> packet)
		{
			this.packet = packet;
		}
		
		public Packet<?> getPacket()
		{
			return packet;
		}
		
		public void setPacket(Packet<?> packet)
		{
			this.packet = packet;
		}
		
		@Override
		public void fire(ArrayList<ConnectionPacketOutputListener> listeners)
		{
			for(ConnectionPacketOutputListener listener : listeners)
			{
				listener.onSentConnectionPacket(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<ConnectionPacketOutputListener> getListenerType()
		{
			return ConnectionPacketOutputListener.class;
		}
	}
}
