/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import gg.evlieye.event.CancellableEvent;
import gg.evlieye.event.Listener;

public interface ChatInputListener extends Listener
{
	public void onReceivedMessage(ChatInputEvent event);
	
	public static class ChatInputEvent
		extends CancellableEvent<ChatInputListener>
	{
		private Text component;
		private List<ChatHudLine.Visible> chatLines;
		
		public ChatInputEvent(Text component,
			List<ChatHudLine.Visible> visibleMessages)
		{
			this.component = component;
			chatLines = visibleMessages;
		}
		
		public Text getComponent()
		{
			return component;
		}
		
		public void setComponent(Text component)
		{
			this.component = component;
		}
		
		public List<ChatHudLine.Visible> getChatLines()
		{
			return chatLines;
		}
		
		@Override
		public void fire(ArrayList<ChatInputListener> listeners)
		{
			for(ChatInputListener listener : listeners)
			{
				listener.onReceivedMessage(this);
				
				if(isCancelled())
					break;
			}
		}
		
		@Override
		public Class<ChatInputListener> getListenerType()
		{
			return ChatInputListener.class;
		}
	}
}
