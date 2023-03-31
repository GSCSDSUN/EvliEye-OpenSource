/*

 *



 */
package gg.evlieye.util;

import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;

/**
 * A {@link CharacterVisitor} to completely bypass Mojang's visitor
 * system and just get the damn {@link String} out of a
 * {@link ChatHudLine.Visible}.
 *
 * <p>
 * Is this seriously the replacement for <code>getString()</code>?
 * What were they thinking?!
 */
public class JustGiveMeTheStringVisitor implements CharacterVisitor
{
	private final StringBuilder sb = new StringBuilder();
	
	@Override
	public boolean accept(int index, Style style, int codePoint)
	{
		sb.appendCodePoint(codePoint);
		return true;
	}
	
	@Override
	public String toString()
	{
		return sb.toString();
	}
}
