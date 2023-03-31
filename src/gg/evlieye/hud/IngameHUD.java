/*

 *



 */
package gg.evlieye.hud;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.util.math.MatrixStack;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.clickgui.ClickGui;
import gg.evlieye.clickgui.screens.ClickGuiScreen;
import gg.evlieye.events.GUIRenderListener;

public final class IngameHUD implements GUIRenderListener
{
	private final EvlieyeLogo evlieyeLogo = new EvlieyeLogo();
	private final HackListHUD hackList = new HackListHUD();
	private TabGui tabGui;
	
	@Override
	public void onRenderGUI(MatrixStack matrixStack, float partialTicks)
	{
		if(!EvlieyeClient.INSTANCE.isEnabled())
			return;
		
		if(tabGui == null)
			tabGui = new TabGui();
		
		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		ClickGui clickGui = EvlieyeClient.INSTANCE.getGui();
		
		// GL settings
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		clickGui.updateColors();
		
		evlieyeLogo.render(matrixStack);
		hackList.render(matrixStack, partialTicks);
		tabGui.render(matrixStack, partialTicks);
		
		// pinned windows
		if(!(EvlieyeClient.MC.currentScreen instanceof ClickGuiScreen))
			clickGui.renderPinnedWindows(matrixStack, partialTicks);
		
		// GL resets
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		
		if(blend)
			GL11.glEnable(GL11.GL_BLEND);
		else
			GL11.glDisable(GL11.GL_BLEND);
	}
	
	public HackListHUD getHackList()
	{
		return hackList;
	}
}
