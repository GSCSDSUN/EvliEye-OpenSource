/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import net.minecraft.network.packet.Packet;
import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;

public interface PacketInputListener extends Listener
{
	public void onReceivedPacket(PacketInputEvent event);
	
	public static class PacketInputEvent
		extends CancellableEvent<PacketInputListener>
	{
		private final Packet<?> packet;
		
		public PacketInputEvent(Packet<?> packet)
		{
			this.packet = packet;
		}
		
		public Packet<?> getPacket()
		{
			return packet;
		}
		
		@Override
		public void fire(ArrayList<PacketInputListener> listeners)
		{
			for(PacketInputListener listener : listeners)
			{
				listener.onReceivedPacket(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<PacketInputListener> getListenerType()
		{
			return PacketInputListener.class;
		}
	}
}
