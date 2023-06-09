/*

 *



 */
package gg.evlieye.hud;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.other_features.EvlieyeLogoOtf;

public final class EvlieyeLogo
{
	private static final Identifier texture =
		new Identifier("evlieye", "evlieye_128.png");
	
	public void render(MatrixStack matrixStack)
	{
		EvlieyeLogoOtf otf = EvlieyeClient.INSTANCE.getOtfs().evlieyeLogoOtf;
		if(!otf.isVisible())
			return;
		
		String version = getVersionString();
		TextRenderer tr = EvlieyeClient.MC.textRenderer;
		
		// draw version background
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		float[] color;
		if(EvlieyeClient.INSTANCE.getHax().rainbowUiHack.isEnabled())
			color = EvlieyeClient.INSTANCE.getGui().getAcColor();
		else
			color = otf.getBackgroundColor();
		
		drawQuads(matrixStack, 0, 6, tr.getWidth(version) + 76, 17, color[0],
			color[1], color[2], 0.5F);
		
		// draw version string
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		tr.draw(matrixStack, version, 74, 8, otf.getTextColor());
		
		// draw Evlieye logo
		RenderSystem.setShaderColor(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_BLEND);
		RenderSystem.setShaderTexture(0, texture);
		DrawableHelper.drawTexture(matrixStack, 0, 3, 0, 0, 72, 18, 72, 18);
	}
	
	private String getVersionString()
	{
		String version = "v" + EvlieyeClient.VERSION;
		version += " MC" + EvlieyeClient.MC_VERSION;
		
		if(EvlieyeClient.INSTANCE.getUpdater().isOutdated())
			version += " (outdated)";
		
		return version;
	}
	
	private void drawQuads(MatrixStack matrices, int x1, int y1, int x2, int y2,
		float r, float g, float b, float a)
	{
		Matrix4f matrix = matrices.peek().getPositionMatrix();
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);
		
		Tessellator tessellator = RenderSystem.renderThreadTesselator();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
			VertexFormats.POSITION_COLOR);
		bufferBuilder.vertex(matrix, x1, y2, 0.0F).color(r, g, b, a).next();
		bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(r, g, b, a).next();
		bufferBuilder.vertex(matrix, x2, y1, 0.0F).color(r, g, b, a).next();
		bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(r, g, b, a).next();
		tessellator.draw();
	}
}
