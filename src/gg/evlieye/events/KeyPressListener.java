/*

 *



 */
package gg.evlieye.events;

import java.util.ArrayList;

import gg.evlieye.event.Event;
import gg.evlieye.event.Listener;

public interface KeyPressListener extends Listener
{
	public void onKeyPress(KeyPressEvent event);
	
	public static class KeyPressEvent extends Event<KeyPressListener>
	{
		private final int keyCode;
		private final int scanCode;
		private final int action;
		private final int modifiers;
		
		public KeyPressEvent(int keyCode, int scanCode, int action,
			int modifiers)
		{
			this.keyCode = keyCode;
			this.scanCode = scanCode;
			this.action = action;
			this.modifiers = modifiers;
		}
		
		@Override
		public void fire(ArrayList<KeyPressListener> listeners)
		{
			for(KeyPressListener listener : listeners)
				listener.onKeyPress(this);
		}
		
		@Override
		public Class<KeyPressListener> getListenerType()
		{
			return KeyPressListener.class;
		}
		
		public int getKeyCode()
		{
			return keyCode;
		}
		
		public int getScanCode()
		{
			return scanCode;
		}
		
		public int getAction()
		{
			return action;
		}
		
		public int getModifiers()
		{
			return modifiers;
		}
	}
}
