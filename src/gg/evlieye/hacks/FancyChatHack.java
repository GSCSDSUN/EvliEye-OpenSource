/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.ChatOutputListener;
import gg.evlieye.hack.Hack;

@SearchTags({"fancy chat"})
public final class FancyChatHack extends Hack implements ChatOutputListener
{
	private final String blacklist = "(){}[]|";
	
	public FancyChatHack()
	{
		super("FancyChat");
		setCategory(Category.CHAT);
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(ChatOutputListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(ChatOutputListener.class, this);
	}
	
	@Override
	public void onSentMessage(ChatOutputEvent event)
	{
		String message = event.getOriginalMessage();
		if(message.startsWith("/") || message.startsWith("."))
			return;
		
		String newMessage = convertString(message);
		event.setMessage(newMessage);
	}
	
	private String convertString(String input)
	{
		String output = "";
		for(char c : input.toCharArray())
			output += convertChar(c);
		
		return output;
	}
	
	private String convertChar(char c)
	{
		if(c < 0x21 || c > 0x80)
			return "" + c;
		
		if(blacklist.contains(Character.toString(c)))
			return "" + c;
		
		return new String(Character.toChars(c + 0xfee0));
	}
}
