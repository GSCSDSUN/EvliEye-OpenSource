/*

 *



 */
package gg.evlieye.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;

public enum ChatUtils
{
	;
	
	private static final MinecraftClient MC = EvlieyeClient.MC;
	
	public static final String evlieye_PREFIX =
		"\u00a7c[\u00a76Evlieye\u00a7c]\u00a7r ";
	private static final String WARNING_PREFIX =
		"\u00a7c[\u00a76\u00a7lWARNING\u00a7c]\u00a7r ";
	private static final String ERROR_PREFIX =
		"\u00a7c[\u00a74\u00a7lERROR\u00a7c]\u00a7r ";
	private static final String SYNTAX_ERROR_PREFIX =
		"\u00a74Syntax error:\u00a7r ";
	
	private static boolean enabled = true;
	
	public static void setEnabled(boolean enabled)
	{
		ChatUtils.enabled = enabled;
	}
	
	public static void component(Text component)
	{
		if(!enabled)
			return;
		
		ChatHud chatHud = MC.inGameHud.getChatHud();
		MutableText prefix = Text.literal(evlieye_PREFIX);
		chatHud.addMessage(prefix.append(component));
	}
	
	public static void message(String message)
	{
		component(Text.literal(message));
	}
	
	public static void warning(String message)
	{
		message(WARNING_PREFIX + message);
	}
	
	public static void error(String message)
	{
		message(ERROR_PREFIX + message);
	}
	
	public static void syntaxError(String message)
	{
		message(SYNTAX_ERROR_PREFIX + message);
	}
	
	public static String getAsString(ChatHudLine.Visible visible)
	{
		return getAsString(visible.content());
	}
	
	public static String getAsString(OrderedText text)
	{
		JustGiveMeTheStringVisitor visitor = new JustGiveMeTheStringVisitor();
		text.accept(visitor);
		return visitor.toString();
	}
}
