/*

 *



 */
package gg.evlieye.hud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.other_features.HackListOtf;
import gg.evlieye.other_features.HackListOtf.Mode;
import gg.evlieye.other_features.HackListOtf.Position;

public final class HackListHUD implements UpdateListener
{
	private final ArrayList<HackListEntry> activeHax = new ArrayList<>();
	private final HackListOtf otf = EvlieyeClient.INSTANCE.getOtfs().hackListOtf;
	private int posY;
	private int textColor;
	
	public HackListHUD()
	{
		EvlieyeClient.INSTANCE.getEventManager().add(UpdateListener.class, this);
	}
	
	public void render(MatrixStack matrixStack, float partialTicks)
	{
		if(otf.getMode() == Mode.HIDDEN)
			return;
		
		if(otf.getPosition() == Position.LEFT
			&& EvlieyeClient.INSTANCE.getOtfs().wurstLogoOtf.isVisible())
			posY = 22;
		else
			posY = 2;
		
		// color
		if(EvlieyeClient.INSTANCE.getHax().rainbowUiHack.isEnabled())
		{
			float[] acColor = EvlieyeClient.INSTANCE.getGui().getAcColor();
			textColor = 0x04 << 24 | (int)(acColor[0] * 256) << 16
				| (int)(acColor[1] * 256) << 8 | (int)(acColor[2] * 256);
			
		}else
			textColor = 0x04000000 | otf.getColor();
		
		int height = posY + activeHax.size() * 9;
		Window sr = EvlieyeClient.MC.getWindow();
		
		if(otf.getMode() == Mode.COUNT || height > sr.getScaledHeight())
			drawCounter(matrixStack);
		else
			drawHackList(matrixStack, partialTicks);
	}
	
	private void drawCounter(MatrixStack matrixStack)
	{
		long size = activeHax.stream().filter(e -> e.hack.isEnabled()).count();
		String s = size + " hack" + (size != 1 ? "s" : "") + " active";
		drawString(matrixStack, s);
	}
	
	private void drawHackList(MatrixStack matrixStack, float partialTicks)
	{
		if(otf.isAnimations())
			for(HackListEntry e : activeHax)
				drawWithOffset(matrixStack, e, partialTicks);
		else
			for(HackListEntry e : activeHax)
				drawString(matrixStack, e.hack.getRenderName());
	}
	
	public void updateState(Hack hack)
	{
		int offset = otf.isAnimations() ? 4 : 0;
		HackListEntry entry = new HackListEntry(hack, offset);
		
		if(hack.isEnabled())
		{
			if(activeHax.contains(entry))
				return;
			
			activeHax.add(entry);
			sort();
			
		}else if(!otf.isAnimations())
			activeHax.remove(entry);
	}
	
	private void sort()
	{
		Comparator<HackListEntry> comparator =
			Comparator.comparing(hle -> hle.hack, otf.getComparator());
		Collections.sort(activeHax, comparator);
	}
	
	@Override
	public void onUpdate()
	{
		if(otf.shouldSort())
			sort();
		
		if(!otf.isAnimations())
			return;
		
		for(Iterator<HackListEntry> itr = activeHax.iterator(); itr.hasNext();)
		{
			HackListEntry e = itr.next();
			boolean enabled = e.hack.isEnabled();
			e.prevOffset = e.offset;
			
			if(enabled && e.offset > 0)
				e.offset--;
			else if(!enabled && e.offset < 4)
				e.offset++;
			else if(!enabled && e.offset >= 4)
				itr.remove();
		}
	}
	
	private void drawString(MatrixStack matrixStack, String s)
	{
		TextRenderer tr = EvlieyeClient.MC.textRenderer;
		int posX;
		
		if(otf.getPosition() == Position.LEFT)
			posX = 2;
		else
		{
			int screenWidth = EvlieyeClient.MC.getWindow().getScaledWidth();
			int stringWidth = tr.getWidth(s);
			
			posX = screenWidth - stringWidth - 2;
		}
		
		tr.draw(matrixStack, s, posX + 1, posY + 1, 0xff000000);
		tr.draw(matrixStack, s, posX, posY, textColor | 0xff000000);
		
		posY += 9;
	}
	
	private void drawWithOffset(MatrixStack matrixStack, HackListEntry e,
		float partialTicks)
	{
		TextRenderer tr = EvlieyeClient.MC.textRenderer;
		String s = e.hack.getRenderName();
		
		float offset =
			e.offset * partialTicks + e.prevOffset * (1 - partialTicks);
		
		float posX;
		if(otf.getPosition() == Position.LEFT)
			posX = 2 - 5 * offset;
		else
		{
			int screenWidth = EvlieyeClient.MC.getWindow().getScaledWidth();
			int stringWidth = tr.getWidth(s);
			
			posX = screenWidth - stringWidth - 2 + 5 * offset;
		}
		
		int alpha = (int)(255 * (1 - offset / 4)) << 24;
		tr.draw(matrixStack, s, posX + 1, posY + 1, 0x04000000 | alpha);
		tr.draw(matrixStack, s, posX, posY, textColor | alpha);
		
		posY += 9;
	}
	
	private static final class HackListEntry
	{
		private final Hack hack;
		private int offset;
		private int prevOffset;
		
		public HackListEntry(Hack mod, int offset)
		{
			hack = mod;
			this.offset = offset;
			prevOffset = offset;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			// do not use Java 16 syntax here,
			// it breaks Eclipse's Clean Up feature
			if(!(obj instanceof HackListEntry))
				return false;
			
			HackListEntry other = (HackListEntry)obj;
			return hack == other.hack;
		}
		
		@Override
		public int hashCode()
		{
			return hack.hashCode();
		}
	}
}
