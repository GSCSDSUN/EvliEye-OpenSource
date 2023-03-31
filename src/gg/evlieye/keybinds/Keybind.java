/*

 *



 */
package gg.evlieye.keybinds;

import java.util.Objects;

public class Keybind implements Comparable<Keybind>
{
	private final String key;
	private final String commands;
	
	public Keybind(String key, String commands)
	{
		this.key = Objects.requireNonNull(key);
		this.commands = Objects.requireNonNull(commands);
	}
	
	public String getKey()
	{
		return key;
	}
	
	public String getCommands()
	{
		return commands;
	}
	
	@Override
	public int compareTo(Keybind o)
	{
		return key.compareToIgnoreCase(o.key);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof Keybind))
			return false;
		
		Keybind otherKeybind = (Keybind)obj;
		
		return key.equalsIgnoreCase(otherKeybind.key);
	}
	
	@Override
	public int hashCode()
	{
		return key.hashCode();
	}
	
	@Override
	public String toString()
	{
		return key.replace("key.keyboard.", "") + " -> " + commands;
	}
}
